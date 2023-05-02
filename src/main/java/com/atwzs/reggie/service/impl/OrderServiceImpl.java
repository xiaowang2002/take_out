package com.atwzs.reggie.service.impl;

import com.atwzs.reggie.common.BaseContext;
import com.atwzs.reggie.domain.*;
import com.atwzs.reggie.exception.CustomException;
import com.atwzs.reggie.mapper.OrderMapper;
import com.atwzs.reggie.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Resource
    private ShoppingCartService shoppingCartService;

    @Resource
    private UserService userService;

    @Resource
    private AddressBookService addressBookService;

    @Resource
    private OrderDetailService orderDetailService;

    /**
     * @description: 提交订单，填充Order表和orderDetail表
     **/
    @Override
    @Transactional
    public void submit(Orders orders) {
        Long currentId = BaseContext.getCurrentId();  //获取当前用户Id

        //查询当前购物车数据
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(shoppingCartLambdaQueryWrapper);

        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new CustomException("购物车是空的，不能下单");
        }

        //查询用户数据
        User user = userService.getById(currentId);

        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null) {
            throw new CustomException("地址有误，不能下单");
        }

        long id = IdWorker.getId();   //订单号,手动获取，而不是插入到数据库的时候获取

        //具有原子性的integer
        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> list = shoppingCartList.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(id);   //手动添加，因为orderdetail的id是自动生成的，但是只能有一个是自动生成的，所以这个手动获取
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            //addandget等于+=
            return orderDetail;
        }).collect(Collectors.toList());

        //向订单表中插入一条数据  订单号就等于id号
        orders.setNumber(String.valueOf(id));
        orders.setId(id);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//计算总金额
        orders.setUserId(currentId);
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        this.save(orders);

        //向订单明细表中插入多条数据
        orderDetailService.saveBatch(list);
        //清空购物车数据
        shoppingCartService.remove(shoppingCartLambdaQueryWrapper);
    }
}
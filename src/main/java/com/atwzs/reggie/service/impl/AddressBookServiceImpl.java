package com.atwzs.reggie.service.impl;

import com.atwzs.reggie.domain.AddressBook;
import com.atwzs.reggie.mapper.AddressBookMapper;
import com.atwzs.reggie.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @ClassName AddressBookServiceImpl
 * @Description
 * @Author WangZhisheng
 * @Date 10:26 2023/5/2
 * @Version 11.0.15
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}

# take_out
技术栈：Spring+SpringBoot+Spring MVC+MyBatisPlus+MySQL+Redis+Nginx

项目介绍：
本项目是专门为外卖商家定制的一款软件产品，包括系统管理后台和移动端应用两部分。其中系统管理后台主要提供给企业内
部员工使用，可以对店铺的外卖单品，套餐，订单等进行管理维护移动端应用丰要提供给消费者使用，可以在线浏览商品，添
加购物车，下单等。移动端登录还提供了阿里云的短信验证码服务，便于用户登录。

技术描述：
1、访拦截:拦截器配合ThreadLocal使用，用户信息在整个线程内共享和统一异常处理
2、在MySQL中使用主从复制机制实现和套餐信息的缓存，减轻数据库压力，提高运行速度
3、用户登录注册:调用第三方平台发送验证码配合Redis实现验证码时效，
4、查询从redis中读取数据，减少了高并发情况下服务器的响应时长高并发秒杀项目;

package com.atwzs.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @ClassName ReggieApplication
 * @Description
 * @Author WangZhisheng
 * @Date 7:20 2023/5/1
 * @Version 11.0.15
 */
@SpringBootApplication
@Slf4j
@ServletComponentScan
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        log.info("项目启动成功");
    }
}

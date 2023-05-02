package com.atwzs.reggie.exception;

/**
 * @ClassName CustomException
 * @Description
 * @Author WangZhisheng
 * @Date 13:33 2023/5/1
 * @Version 11.0.15
 */
public class CustomException extends RuntimeException {

    public CustomException(String msg) {
        super(msg);
    }
}

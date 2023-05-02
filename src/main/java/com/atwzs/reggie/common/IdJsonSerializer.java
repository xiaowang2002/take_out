package com.atwzs.reggie.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.nio.file.LinkOption;

/**
 * @ClassName IdJsonSerializer
 * @Description
 * @Author WangZhisheng
 * @Date 12:06 2023/5/1
 * @Version 11.0.15
 */
public class IdJsonSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(aLong + "");
    }
}

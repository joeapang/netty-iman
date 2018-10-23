package com.joe.netty.serializer.impl;/**
 * @author joe
 * @date 2018/10/23/023
 */

import com.alibaba.fastjson.JSON;
import com.joe.netty.serializer.Serializer;
import com.joe.netty.serializer.SerializerAlgorithm;

/**
 * @author joe
 * @description:
 * @date 2018/10/23/023
 */
public class JsonSerializer<T> implements Serializer<T> {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serializer(T object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserializer(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}

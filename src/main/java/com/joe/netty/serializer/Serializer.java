package com.joe.netty.serializer;/**
 * @author joe
 * @date 2018/10/23/023
 */

import com.joe.netty.serializer.impl.JsonSerializer;

/**
 * @author joe
 * @description: 序列化接口
 * @date 2018/10/23/023
 */
public interface Serializer<T> {

    Serializer DEFAULT=new JsonSerializer();
    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转换成二进制
     * @param object
     * @return
     */
    byte[] serializer(T object);

    /**
     * 二进制转换成java对象
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserializer(Class<T> clazz,byte[] bytes);
}

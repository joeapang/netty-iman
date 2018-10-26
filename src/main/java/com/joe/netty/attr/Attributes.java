package com.joe.netty.attr;/**
 * @author joe
 * @date 2018/10/25/025
 */

import io.netty.util.AttributeKey;

/**
 * @author joe
 * @description:
 * @date 2018/10/25/025
 */
public interface Attributes {
    AttributeKey<Boolean> login = AttributeKey.newInstance("login");
}

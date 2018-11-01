package com.joe.netty.attr;/**
 * @author joe
 * @date 2018/10/25/025
 */

import com.joe.netty.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author joe
 * @description:
 * @date 2018/10/25/025
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("LOGIN");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}

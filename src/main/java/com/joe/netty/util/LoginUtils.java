package com.joe.netty.util;/**
 * @author joe
 * @date 2018/10/25/025
 */

import com.joe.netty.attr.Attributes;
import io.netty.channel.Channel;

/**
 * @author joe
 * @description:抽取出 LoginUtil 用于设置登录标志位以及判断是否有标志位，
 *  如果有标志位，不管标志位的值是什么，都表示已经成功登录过
 * @date 2018/10/25/025
 */
public class LoginUtils {

    public static void markLogin(Channel channel) {
        channel.attr(Attributes.login).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.login).get() != null;
    }
}

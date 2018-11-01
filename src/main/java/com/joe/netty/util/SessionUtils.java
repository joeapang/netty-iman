package com.joe.netty.util;/**
 * @author joe
 * @date 2018/10/31/031
 */

import com.joe.netty.attr.Attributes;
import com.joe.netty.session.Session;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author joe
 * @description:
 * @date 2018/10/31/031
 */
public class SessionUtils {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel));
        }
    }
    public static void getAllUser(){
        System.out.println(userIdChannelMap.toString());
    }
    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(Attributes.SESSION);
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }

}

package com.joe.netty.client.handler;/**
 * @author joe
 * @date 2018/10/25/025
 */

import com.joe.netty.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author joe
 * @description:
 * @date 2018/10/25/025
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        System.out.println(new Date() + ": 收到服务端的消息: " + msg.getMessage());

    }


}

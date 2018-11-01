package com.joe.netty.server.handler;/**
 * @author joe
 * @date 2018/10/25/025
 */

import com.joe.netty.packet.MessageRequestPacket;
import com.joe.netty.packet.MessageResponsePacket;
import com.joe.netty.session.Session;
import com.joe.netty.util.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author joe
 * @description: 1.服务端在收到客户端发来的消息之后，首先拿到当前用户，也就是消息发送方的会话信息。
 * 2.拿到消息发送方的会话信息之后，构造一个发送给客户端的消息对象 MessageResponsePacket，填上发送消息方的用户标识、昵称、消息内容。
 * 3.通过消息接收方的标识拿到对应的 channel。
 * 4如果消息接收方当前是登录状态，直接发送，如果不在线，控制台打印出一条警告消息。
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        SessionUtils.getAllUser();
        //1.拿到消息发送方的会话信息
        Session session = SessionUtils.getSession(ctx.channel());

        // 2.通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        System.out.println(new Date() + ": 收到客户端消息: " + msg.getMessage());
        messageResponsePacket.setMessage(msg.getMessage());
        // 3.拿到消息接收方的 channel
        Channel toUserChannel = SessionUtils.getChannel(msg.getToUserId());

        // 4.将消息发送给消息接收方
        if (toUserChannel != null && SessionUtils.hasLogin(toUserChannel)) {

            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + msg.getToUserId() + "] 不在线，发送失败!");
        }
    }
}

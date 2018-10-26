package com.joe.netty.client.handler;/**
 * @author joe
 * @date 2018/10/25/025
 */

import com.joe.netty.packet.LoginRequestPacket;
import com.joe.netty.packet.LoginResponsePacket;
import com.joe.netty.protocol.command.PacketCodeC;
import com.joe.netty.util.LoginUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * @author joe
 * @description:
 * @date 2018/10/25/025
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println(new Date() + ": 客户端登录成功");
            LoginUtils.markLogin(ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + msg.getReason());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "：客户端开始登陆");
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setPassword("wutong");
        packet.setUsername("joe");
        packet.setUserId(UUID.randomUUID().toString());
        //编码,第一个实参 ctx.alloc() 获取的就是与当前连接相关的 ByteBuf 分配器
        ctx.channel().writeAndFlush(packet);
    }
}

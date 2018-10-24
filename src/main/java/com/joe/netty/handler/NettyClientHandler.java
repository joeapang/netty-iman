package com.joe.netty.handler;/**
 * @author joe
 * @date 2018/10/24/024
 */

import com.joe.netty.packet.BasePacket;
import com.joe.netty.packet.LoginRequestPacket;
import com.joe.netty.packet.LoginResponsePacket;
import com.joe.netty.protocol.command.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * @author joe
 * @description:
 * @date 2018/10/24/024
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf response = (ByteBuf) msg;
        BasePacket packet = PacketCodeC.INSTANCE.decode(response);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket   loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + ": 客户端登录成功");

            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
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
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), packet);
        ctx.channel().writeAndFlush(buffer);
    }
}

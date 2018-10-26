package com.joe.netty.server.handler;/**
 * @author joe
 * @date 2018/10/25/025
 */

import com.joe.netty.packet.BasePacket;
import com.joe.netty.packet.LoginRequestPacket;
import com.joe.netty.packet.LoginResponsePacket;
import com.joe.netty.protocol.command.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author joe
 * @description:
 * @date 2018/10/25/025
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println("收到客户端请求！");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        //登陆校验
        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + ": 登录成功!");

        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);

        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

}

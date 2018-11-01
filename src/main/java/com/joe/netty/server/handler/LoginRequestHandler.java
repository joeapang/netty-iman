package com.joe.netty.server.handler;/**
 * @author joe
 * @date 2018/10/25/025
 */

import com.joe.netty.packet.LoginRequestPacket;
import com.joe.netty.packet.LoginResponsePacket;
import com.joe.netty.session.Session;
import com.joe.netty.util.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

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
        loginResponsePacket.setUserName(msg.getUsername());

        //登陆校验
        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            String userId = msg.getUsername();
            loginResponsePacket.setUserId(userId);
            SessionUtils.bindSession(new Session(userId,msg.getUsername()),ctx.channel());
            System.out.println(new Date() + "[" + msg.getUsername() + "]:" + " 登录成功!");

        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);

        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtils.unbindSession(ctx.channel());
    }
}

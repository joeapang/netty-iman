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

/**
 * @author joe
 * @description:
 * @date 2018/10/24/024
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到客户端请求！");
        ByteBuf buffer = (ByteBuf) msg;
        BasePacket packet = PacketCodeC.INSTANCE.decode(buffer);

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        //判断是否为登陆请求
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            loginResponsePacket.setVersion(packet.getVersion());

            //登陆校验
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ": 登录成功!");

            } else {
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);

            }
            ByteBuf response = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(response);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}

package com.joe.netty.util;/**
 * @author joe
 * @date 2018/10/25/025
 */

import com.joe.netty.packet.BasePacket;
import com.joe.netty.protocol.command.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author joe
 * @description:
 * @date 2018/10/25/025
 */
public class PacketEncoder extends MessageToByteEncoder<BasePacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, BasePacket msg, ByteBuf out) throws Exception {
        System.out.println("进行编码");
        PacketCodeC.INSTANCE.encode(out, msg);
    }
}

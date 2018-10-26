package com.joe.netty.util;/**
 * @author joe
 * @date 2018/10/25/025
 */

import com.joe.netty.protocol.command.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author joe
 * @description:
 * @date 2018/10/25/025
 */
public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("进行解码！");
        out.add(PacketCodeC.INSTANCE.decode(in));
    }
}

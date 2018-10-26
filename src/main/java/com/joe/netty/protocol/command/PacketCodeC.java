package com.joe.netty.protocol.command;/**
 * @author joe
 * @date 2018/10/23/023
 */

import com.joe.netty.packet.*;
import com.joe.netty.serializer.Serializer;
import com.joe.netty.serializer.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author joe
 * @description:
 * @date 2018/10/23/023
 */
public class PacketCodeC {
    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends BasePacket>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        serializerMap = new HashMap<>();
        Serializer serializer = new JsonSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    /**
     * 对数据进行编码
     *
     * @param packet    传递的java对象
     * @param byteBuf ByteBuf 分配器
     * @return
     */
    public ByteBuf encode(ByteBuf byteBuf, BasePacket packet) {
        //序列化对象
        byte[] bytes = Serializer.DEFAULT.serializer(packet);
        /**
         * 根据通信协议封装数据
         * 通信协议的结构为：魔数->版本号->序列化算法->指令->数据长度->数据
         */
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.command());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public BasePacket decode(ByteBuf byteBuf) {
        //跳过前四字节的魔数字
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);
        //获取序列化算法
        byte serializerAlgorithm = byteBuf.readByte();
        //获取指令
        byte commond = byteBuf.readByte();
        //获取数据长度
        int length = byteBuf.readInt();
        //读取数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends BasePacket> packetType =getRequestType(commond);
        Serializer<BasePacket> serializer =getSerializer(serializerAlgorithm);


        if (packetType != null && serializer != null) {
            return serializer.deserializer(packetType, bytes);
        }
        return null;

    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends BasePacket> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}

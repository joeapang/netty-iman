package com.joe.netty.packet;/**
 * @author joe
 * @date 2018/10/24/024
 */

import com.joe.netty.protocol.command.Command;
import lombok.Data;

/**
 * @author joe
 * @description: 服务端发送至客户端的消息对象定义为 MessageResponsePacket
 * @date 2018/10/24/024
 */
@Data
public class MessageResponsePacket extends BasePacket {
    private String message;

    @Override
    public Byte command() {

        return Command.MESSAGE_RESPONSE;
    }
}

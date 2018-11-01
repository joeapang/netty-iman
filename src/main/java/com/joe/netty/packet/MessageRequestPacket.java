package com.joe.netty.packet;/**
 * @author joe
 * @date 2018/10/24/024
 */

import com.joe.netty.protocol.command.Command;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author joe
 * @description: 把客户端发送至服务端的消息对象定义为 MessageRequestPacket
 * @date 2018/10/24/024
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends BasePacket {
    private String toUserId;

    private String message;

    public MessageRequestPacket(String message) {
        this.message = message;
    }

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte command() {
        return Command.MESSAGE_REQUEST;
    }
}

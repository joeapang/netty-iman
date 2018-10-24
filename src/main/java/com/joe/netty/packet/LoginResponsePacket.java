package com.joe.netty.packet;/**
 * @author joe
 * @date 2018/10/24/024
 */

import com.joe.netty.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author joe
 * @description: Server端发给客户端的信息
 * @date 2018/10/24/024
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResponsePacket extends  BasePacket{
    private boolean isSuccess;
    private String reason;
    @Override
    public Byte command() {
        return Command.LOGIN_RESPONSE;
    }
}

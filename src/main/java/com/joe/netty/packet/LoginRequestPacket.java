package com.joe.netty.packet;/**
 * @author joe
 * @date 2018/10/23/023
 */

import com.joe.netty.packet.BasePacket;
import com.joe.netty.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author joe
 * @description:
 * @date 2018/10/23/023
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginRequestPacket extends BasePacket {
    private String userId;
    private String username;
    private String password;

    @Override
    public Byte command() {
        return Command.LOGIN_REQUEST;
    }
}

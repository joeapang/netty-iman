package com.joe.netty.protocol.command;/**
 * @author joe
 * @date 2018/10/23/023
 */

import lombok.Data;

/**
 * @author joe
 * @description:
 * @date 2018/10/23/023
 */
@Data
public class LoginRequestPacket extends BasePacket {
    private Integer userId;
    private String username;
    private String password;

    @Override
    public Byte command() {
        return Command.LOGIN_REQUEST;
    }
}

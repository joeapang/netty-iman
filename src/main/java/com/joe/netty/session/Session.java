package com.joe.netty.session;/**
 * @author joe
 * @date 2018/10/31/031
 */

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author joe
 * @description:
 * @date 2018/10/31/031
 */
@Data
@NoArgsConstructor
public class Session {
    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}

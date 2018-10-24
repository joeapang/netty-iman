package com.joe.netty.main;/**
 * @author joe
 * @date 2018/10/24/024
 */

import com.joe.netty.client.NettyClient;

/**
 * @author joe
 * @description:启动客户端
 * @date 2018/10/24/024
 */
public class ClientStarter {

    private static final int port = 8080;
    private static final String host = "127.0.0.1";

    public static void main(String[] args) {
        NettyClient client = new NettyClient(host, port);
        client.start();
    }
}

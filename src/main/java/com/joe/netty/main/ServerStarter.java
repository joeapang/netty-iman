package com.joe.netty.main;/**
 * @author joe
 * @date 2018/10/24/024
 */

import com.joe.netty.server.NettyServer;

/**
 * @author joe
 * @description:
 * @date 2018/10/24/024
 */
public class ServerStarter {
    public static void main(String[] args){
        NettyServer server=new NettyServer(8080);
        server.start();
    }
}

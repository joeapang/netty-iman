package com.joe.netty.echo;/**
 * @author joe
 * @date 2018/10/19/019
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author joe
 * @description:
 * @date 2018/10/19/019
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        //使用NIO传输，利用NioEventLoopGroup来监听新的连接并处理已经接入的连接
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建ServerBootstrap实例，获取服务器端引擎
            ServerBootstrap b = new ServerBootstrap();
            //将channel设置为NioServerSocketChannel,并且将InetSocketAddress绑定到服务器，这样服务器才能接受新的连接
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline().addLast(new EchoServerHandler());
                                }
                            });
            ChannelFuture future = b.bind().sync();
            System.out.println(EchoServer.class.getName() + "started and listen on"
                    + future.channel().localAddress());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {

        int port = Integer.parseInt("8080");
        new EchoServer(port).start();
    }
}

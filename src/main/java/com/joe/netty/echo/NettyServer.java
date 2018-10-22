package com.joe.netty.echo;/**
 * @author joe
 * @date 2018/10/19/019
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author joe
 * @description:
 * @date 2018/10/19/019
 */
public class NettyServer {
    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        //bossGroup表示监听端口，accept 新连接的线程组，然后交给workerGroup处理，workerGroup表示处理每一条连接的数据读写的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //创建ServerBootstrap实例，获取服务器端引擎
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            /**
             * childOption()可以给每条连接设置一些TCP底层相关的属性
             *
             * ChannelOption.SO_KEEPALIVE表示是否开启TCP底层心跳机制，true为开启
             * ChannelOption.TCP_NODELAY表示是否开启Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。...
             *
             */
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline().addLast(new NettyServerHandler());
                                }
                            });
            //bind服务端，此时发生阻塞，直到bind调用完成，才会调用到sync()方法
           ChannelFuture future= bind(serverBootstrap, port).sync();
           //会一直等到服务端channel关闭的时候才会执行（因为我们在channel关闭的future上执行了sync()方法）
           future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully().sync();
            workGroup.shutdownGracefully().sync();
        }
    }

    private static ChannelFuture bind(final ServerBootstrap serverBootstrap, int port) {
            return serverBootstrap.bind(port).addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                }
            });
    }

    public static void main(String[] args) throws Exception {

        int port = Integer.parseInt("8080");
        new NettyServer(port).start();
    }
}

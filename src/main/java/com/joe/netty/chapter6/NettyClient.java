package com.joe.netty.chapter6;/**
 * @author joe
 * @date 2018/10/19/019
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author joe
 * @description:
 * @date 2018/10/19/019
 */
public class NettyClient {
    private final String HOST;
    private final int PORT;
    private int MAX_RETRY = 5;

    public NettyClient(String host, int port, int max) {
        HOST = host;
        PORT = port;
        MAX_RETRY = max;
    }

    public NettyClient(String host, int port) {
        HOST = host;
        PORT = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
            /**
             * option() 方法可以给连接设置一些 TCP 底层相关的属性
             *
             * ChannelOption.CONNECT_TIMEOUT_MILLIS 表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
             * ChannelOption.SO_KEEPALIVE 表示是否开启 TCP 底层心跳机制，true 为开启
             * ChannelOption.TCP_NODELAY 表示是否开始 Nagle 算法，true 表示关闭，false 表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就设置为 true 关闭，如果需要减少发送次数减少网络交互，就设置为 false 开启...
             *
             */
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
                    .option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            connect(bootstrap, HOST, PORT, MAX_RETRY);

    }


    /**
     * 防止网络原因连接不上，进行失败重连
     *
     * @param bootstrap
     * @param host
     * @param port
     * @param retry     重连接次数
     */
    private void connect(Bootstrap bootstrap, String host, int port, int retry) {
        try {


            bootstrap.connect(host, port).addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("连接成功！");
                } else if (retry == 0) {
                    System.out.println("连接次数已用完，连接超时！");
                } else {
                    //第几次连接
                    int order = (MAX_RETRY - retry) + 1;
                    //本次重连间隔
                    int delay = 1 << order;
                    System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                    /**
                     * bootstrap.config() 这个方法返回的是 BootstrapConfig，他是对 Bootstrap 配置参数的抽象，
                     * 然后 bootstrap.config().group() 返回的就是我们在一开始的时候配置的线程模型 group，
                     * 调 group 的 schedule 方法即可实现定时任务逻辑...
                     *
                     */
                    bootstrap.config().group()
                            .schedule(() -> connect(bootstrap, host, port, retry - 1),
                                    delay, TimeUnit.SECONDS);
                }
            }).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        final String host = "127.0.0.1";
        final int port = Integer.parseInt("8080");
        new NettyClient(host, port, 5).start();
    }
}

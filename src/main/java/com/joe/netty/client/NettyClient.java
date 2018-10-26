package com.joe.netty.client;/**
 * @author joe
 * @date 2018/10/24/024
 */

import com.joe.netty.client.handler.LoginResponseHandler;
import com.joe.netty.client.handler.MessageResponseHandler;
import com.joe.netty.packet.MessageRequestPacket;
import com.joe.netty.protocol.command.PacketCodeC;
import com.joe.netty.util.LoginUtils;
import com.joe.netty.util.PacketDecoder;
import com.joe.netty.util.PacketEncoder;
import com.joe.netty.util.Spliter;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author joe
 * @description:
 * @date 2018/10/24/024
 */
public class NettyClient {
    private String HOST;
    private int PORT;
    private int MAX_RETRY = 5;

    public NettyClient(String HOST, int PORT, int MAX_RETRY) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.MAX_RETRY = MAX_RETRY;
    }

    public NettyClient(String HOST, int PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
    }

    public void start() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        /**
         * option() 方法可以给连接设置一些 TCP 底层相关的属性
         *
         * ChannelOption.CONNECT_TIMEOUT_MILLIS 表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
         * ChannelOption.SO_KEEPALIVE 表示是否开启 TCP 底层心跳机制，true 为开启
         * ChannelOption.TCP_NODELAY 表示是否开始 Nagle 算法，true 表示关闭，false 表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就设置为 true 关闭，如果需要减少发送次数减少网络交互，就设置为 false 开启...
         *
         */
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new Spliter()).addLast(new PacketDecoder()).addLast(new LoginResponseHandler())
                                .addLast(new MessageResponseHandler()).addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    /**
     * @param bootstrap
     * @param host
     * @param port
     * @param retry
     */
    private void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + "：连接主机" + host + ":" + port + "成功！");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);

            } else if (retry == 0) {
                System.out.println("重试次数已用完，放弃连接!");
            } else {
                //第几次连接
                int order = (MAX_RETRY - retry) + 1;
                //本次重连时间
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1),
                        delay, TimeUnit.SECONDS);
            }
        });

    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtils.hasLogin(channel)) {
                    System.out.println("已登录,输入消息发送至服务端: ");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();
                    //封装请求消息包

                    channel.writeAndFlush(new MessageRequestPacket(line));
                }
            }
        }).start();
    }
}

package com.example.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.channels.SocketChannel;

/**
 * Created by xupengfey on 16/8/1.
 */
@Component
public class NettyServer implements ApplicationContextAware{
    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private int port = 6000;
    public NettyServer() {
        this.port = 6001;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.debug("setApplicationContext");
    }

    @PostConstruct
    public void start() throws InterruptedException {
        logger.debug("start");
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new EchoServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

//        p.addLast(new EchoServerHandler());

        serverBootstrap.bind(port).sync().channel();
    }

    @PreDestroy
    public void stop() {
        logger.debug("stop");
    }
}

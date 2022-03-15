package hao.lin.splitdate;

import hao.lin.mynetty.MyNettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyServer {
public static void main(String[] args) {
	
	EventLoopGroup bossGroup = new NioEventLoopGroup();
	EventLoopGroup workGroup = new NioEventLoopGroup();
	
	try {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workGroup);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				//添加自定义的编码器
				ch.pipeline().addLast(new MyMessageEncoder());
				//添加自定义的解码器
				ch.pipeline().addLast(new MyMessageDecoder());
				// TODO Auto-generated method stub
				ch.pipeline().addLast(new MyServerHandler());
			}
			
		});
		System.out.println("服务端已经启动好了");
		
		ChannelFuture sync = serverBootstrap.bind(6688).sync();
		sync.channel().closeFuture().sync();
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}finally {
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
	}
	
}
}

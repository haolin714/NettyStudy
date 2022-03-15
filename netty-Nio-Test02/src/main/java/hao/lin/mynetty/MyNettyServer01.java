package hao.lin.mynetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyNettyServer01 {
	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workGroup);
			serverBootstrap.channel(NioServerSocketChannel.class);
			serverBootstrap.childOption(ChannelOption.SO_BACKLOG, 128);
			serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new MyNettyServerHandler());
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

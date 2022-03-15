package hao.lin.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
	public static void main(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			//启动服务器
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			//加入boss组和工作组
			serverBootstrap.group(bossGroup, workGroup).
			channel(NioServerSocketChannel.class)
			.childHandler(new TestServerInitializer());//会在workGroup生效
//			.handler(null);//会在bossGroup生效
			
			ChannelFuture channelFuture = serverBootstrap.bind(6688).sync();
			channelFuture.channel().closeFuture().sync();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}

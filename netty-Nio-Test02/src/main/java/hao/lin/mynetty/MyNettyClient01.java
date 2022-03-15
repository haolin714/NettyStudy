package hao.lin.mynetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyNettyClient01 {
	public static void main(String[] args) {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new MyNettyClientHandler());
					
				}
			});
			System.out.println("客户端已准备成功");
			ChannelFuture connect = bootstrap.connect("localhost", 6688);
			connect.channel().closeFuture().sync();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			eventLoopGroup.shutdownGracefully();
		}
	}

}

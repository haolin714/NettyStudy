package hao.lin.splitdate;

import hao.lin.mynetty.MyNettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {

	public static void main(String[] args) {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//添加自定义的编码器
					ch.pipeline().addLast(new MyMessageEncoder());
					//添加自定义的解码器
					ch.pipeline().addLast(new MyMessageDecoder());
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new MyClientHandler());
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

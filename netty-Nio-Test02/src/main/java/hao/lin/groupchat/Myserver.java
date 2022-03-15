package hao.lin.groupchat;

import java.util.concurrent.TimeUnit;

import hao.lin.netty.TestServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class Myserver {
	public static void main(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			//启动服务器
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
//			.handler(new LoggingHandler(LogLevel.INFO ))
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ChannelPipeline pipeline = ch.pipeline();
					//加入一个netty提供的空闲状态处理器
					/**
					 * addLast方法参数IdleStateHandler对象的参数解释
					 * readerIdleTime表示多长时间没有读，就会发送心跳检测包检测是否连接
					 * writerIdleTime表示多长时间没有写，就会发送心跳检测包检测是否连接
					 * allIdleTime表示多长时间没有读写，就会发送心跳检测包检测是否连接
					 */
					pipeline.addLast(new IdleStateHandler(3,5,7,TimeUnit.SECONDS));
					//加入一个对空闲检测进一步处理的handler（自定义）
					pipeline.addLast(new MyServerHandler());
				}
			});
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

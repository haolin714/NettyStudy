package hao.lin.webnetty;

import java.util.concurrent.TimeUnit;

import hao.lin.groupchat.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class WebServer {
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
					//基于http协议，所以使用http的编解码器
					pipeline.addLast(new HttpServerCodec());
					//以块方式写入，需要添加ChunkedWriteHandler
					pipeline.addLast(new ChunkedWriteHandler());
					/**
					 * http数据传输过程中是分段的 httpobjectAggergator,就是可以将多
					 * 个段聚合，当浏览器发送大量数据时，就会发出多次http请求
					 */
					pipeline.addLast(new HttpObjectAggregator(8192));
					/**
					 * 对于websocket，他的数据以 帧 的形式传递
					 * 可以看到webSocketFrame下面有六个子类
					 * 浏览器请求时 ws://localhost:6688/hello  表示请求uri
					 * WebSocketServerProtocolHandler 核心功能将http协议升级为
					 * websocket协议，保持长连接
					 */
					pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
					//自定义handler 业务处理逻辑
					pipeline.addLast(new MyWebHandler());

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

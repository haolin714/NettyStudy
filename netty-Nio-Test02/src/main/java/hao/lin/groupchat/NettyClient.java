package hao.lin.groupchat;



import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {
	public static void main(String[] args) throws Exception {
		//客户端需要一个事件循环组
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {		//创建客户端启动对象
			//注意客户端使用的不是ServerbootStap  而是bootStrap
			Bootstrap bootstrap = new Bootstrap();
			
			//做一个参数设置
			bootstrap.group(eventLoopGroup) //设置线程组
			.channel(NioSocketChannel.class) //设置客户端通道的实现类（反射）
			.handler(new ChannelInitializer<SocketChannel>() {
	
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new NettyClientHandler())//加入自己的处理器
					.addLast("decoder",new StringDecoder())   //添加解码器
					.addLast("encoder",new StringEncoder());  //添加编码器
				}
			});
			
			System.out.println("客户端已准备好");
			//启动客户端连接服务器
			ChannelFuture channelFuture = bootstrap.connect("localhost",6688);
			Channel channel = channelFuture.channel();
			System.out.println("===="+ channel.localAddress() +"=======");
			
			//客户端输入
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNext()) {
				String msgString  = scanner.nextLine();
				channel.writeAndFlush(msgString+"\r\n");
			}
			
			//给关闭通道进行监听
			channel.closeFuture().sync();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			//关闭
			eventLoopGroup.shutdownGracefully();
		}
	}

}

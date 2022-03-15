package hao.lin.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		//向管道加入处理器
		
		//得到管道
		ChannelPipeline pipeline = ch.pipeline();
		
		//加入一个netty 提供的hettpServerCode  一个编解码器,由netty提供
		pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
		
		//增加一个自定义的handler
		pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());
	} 
}

package hao.lin.splitdate;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<Messageprotocl>{
	private int count = 0;
//	@Override
//	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//		// TODO Auto-generated method stub
//		//客户端读取事件
//		byte[] buffer = new byte[msg.readableBytes()];
//		msg.readBytes(buffer);
//		String message = new String(buffer,Charset.forName("utf-8"));
//		System.out.println("服务端返回信息为："+message);
//		System.out.println("服务器接收到的数据消息量"+(++this.count));
//	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Messageprotocl msg) throws Exception {
		// TODO Auto-generated method stub
		int len = msg.getLen();
		byte[] content = msg.getContent();
		System.out.println("服务端得到的数据："+
				new String(content,Charset.forName("utf-8"))+
				" 长度为： "+len);
		System.out.println("服务器接收到的数据第几包："+(++this.count));
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		//客户端发送是十条数据
		for (int i = 0; i < 10; i++) {
			String msgString = "今天老子不上班，爽翻！";
			byte[] bytes = msgString.getBytes(Charset.forName("utf-8"));
			int len = bytes.length;
			//包装对象
			Messageprotocl message = new Messageprotocl(len, bytes);
			ctx.writeAndFlush(message);	
		}
	}
	

}

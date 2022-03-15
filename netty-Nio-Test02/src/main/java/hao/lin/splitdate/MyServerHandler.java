package hao.lin.splitdate;

import java.nio.charset.Charset;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyServerHandler extends SimpleChannelInboundHandler<Messageprotocl>{
	private int count = 0;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Messageprotocl msg) throws Exception {
		// TODO Auto-generated method stub
//		byte[] buffer = new byte[msg.readableBytes()];
//		msg.readBytes(buffer);
//		String message = new String(buffer,Charset.forName("utf-8"));
//		
//		System.out.println("服务器接收到的数据"+message);
//		System.out.println("服务器接收到的数据消息量"+(++this.count));
//		
		int len = msg.getLen();
		byte[] content = msg.getContent();
		System.out.println("服务端得到的数据："+
				new String(content,Charset.forName("utf-8"))+
				" 长度为： "+len);
		System.out.println("服务器接收到的数据第几包："+(++this.count));
		//返回数据给客户端
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(UUID.randomUUID().toString()+"----",CharsetUtil.UTF_8);
		String uuid = new String(UUID.randomUUID().toString());
		byte[] bytes = uuid.getBytes();
		Messageprotocl messageprotocl = new Messageprotocl(bytes.length, bytes);
		ctx.writeAndFlush(messageprotocl);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(cause.getMessage());
		ctx.close();
	}

}

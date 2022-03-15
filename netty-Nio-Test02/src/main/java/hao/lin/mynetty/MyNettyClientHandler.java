package hao.lin.mynetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class MyNettyClientHandler extends ChannelInboundHandlerAdapter{
	//通道就绪触发该方法
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("client "+ ctx);
		
		ctx.writeAndFlush(Unpooled.copiedBuffer("hello server",CharsetUtil.UTF_8));
		
	}
	//通道有读取事件时会触发
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf buf = (ByteBuf)msg;
		System.out.println("服务端回复的消息："+ buf.toString(CharsetUtil.UTF_8));
		System.out.println("服务器地址：" + ctx.channel().remoteAddress());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
}
}

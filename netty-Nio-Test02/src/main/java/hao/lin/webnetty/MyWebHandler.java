package hao.lin.webnetty;

import java.time.LocalDateTime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class MyWebHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
	
	//回复消息
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("服务端接收到的消息"+msg.text());
		
		ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间"+
				LocalDateTime.now()+msg.text()));
		
	}
	
	//web客户端链接后，触发方法
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		// id表示唯一的值，longText是唯一的，shorttext不是唯一
		System.out.println("handlerAdded 被调用"+ctx.channel().id().asLongText());
		System.out.println("handlerAdded 被调用"+ctx.channel().id().asShortText());
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("handlerRemoved 被调用"+ctx.channel().id().asLongText());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("异常发生"+cause.getMessage());
		ctx.close();
	}
}

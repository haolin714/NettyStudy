package hao.lin.groupchat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class MyServerHandler extends ChannelInboundHandlerAdapter{
	
	/**
	 * ctx  为上下文
	 * evt  事件
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// TODO Auto-generated method stub
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent)evt;
			String eventType = null;
			switch (event.state()) {
			case READER_IDLE:
				eventType = "读空闲";
				break;
			case WRITER_IDLE:
				eventType = "写空闲";
				break;
			case ALL_IDLE:
				eventType = "读写空闲";
				break;
			default:
				break;
			}
			System.out.println(ctx.channel().remoteAddress()+"--超时时间"+eventType);
			System.out.println("服务器做相应处理..");
		}
	}
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf byteBuf = (ByteBuf)msg;
		System.out.println("客户端发来的消息是："+byteBuf.toString(CharsetUtil.UTF_8));
	}
}

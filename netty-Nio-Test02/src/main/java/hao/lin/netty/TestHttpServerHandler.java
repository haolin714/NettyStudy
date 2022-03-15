package hao.lin.netty;

import java.net.URI;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
/**
 * 
 * @author 59735
 * SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 * HttpObject是客户端和服务端的通讯数据封装成的对象
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject>{

	//读取事件就会触发  读取客户端数据
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		// TODO Auto-generated method stub
		//判断是否是http的request请求
		if (msg instanceof HttpRequest) {
			HttpRequest httpRequest = (HttpRequest)msg;
			URI uri = new URI(httpRequest.uri());
			//如果是请求网页图标题，则进行拦截
			if ("/favicon.ico".equals(uri.getPath())) {
				return;
			}
			System.out.println("msg类型为=="+msg.getClass());
			System.out.println("客户端地址为："+ctx.channel().remoteAddress());
			System.out.println("pipeline hashCode: "+ctx.pipeline().hashCode());
			System.out.println("handler hashCode: "+ ctx.channel().hashCode());
			System.out.println("pipeline获取的channel："+ctx.pipeline().channel());
			System.out.println("pipeline获取的handler："+ctx.handler());
			//回复给浏览器客户端信息
			ByteBuf content = Unpooled.copiedBuffer("Hello World!!",CharsetUtil.UTF_8);
			//构造一个http的响应，即httpResponse
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
			//
			
			//设置头信息
			response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
			response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
			ctx.writeAndFlush(response);	
		}
	}
}

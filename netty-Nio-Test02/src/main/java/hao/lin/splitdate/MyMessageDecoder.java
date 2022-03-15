package hao.lin.splitdate;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

//自定义解码器
public class MyMessageDecoder extends ReplayingDecoder<Messageprotocl>{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MyMessageDecoder 被调用");
		
		//需要将得到的二进制字节码 转为对象
		int len = in.readInt();
		byte[] content = new byte[len];
		in.readBytes(content);
		
		//封装为Messageprotocl对象，放入out，传入下一个handler
		Messageprotocl messageprotocl = new Messageprotocl();
		messageprotocl.setContent(content);
		messageprotocl.setLen(len);
		
		out.add(messageprotocl);
		
	}

}

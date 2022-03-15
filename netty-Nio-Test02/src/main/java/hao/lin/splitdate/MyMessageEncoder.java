package hao.lin.splitdate;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;



//自定义编码器
public class MyMessageEncoder extends MessageToByteEncoder<Messageprotocl>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Messageprotocl msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MyMessageEncoder 方法被调用");
		out.writeInt(msg.getLen());
		out.writeBytes(msg.getContent());
	}


}

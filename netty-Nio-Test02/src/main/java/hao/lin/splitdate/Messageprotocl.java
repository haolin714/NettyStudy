package hao.lin.splitdate;
//自定义协议包
public class Messageprotocl {
	private int len;
	private byte[] content;
	/**
	 * @param len
	 * @param content
	 */
	
	public Messageprotocl(int len, byte[] content) {
		super();
		this.len = len;
		this.content = content;
	}
	/**
	 * 
	 */
	public Messageprotocl() {
		super();
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
}

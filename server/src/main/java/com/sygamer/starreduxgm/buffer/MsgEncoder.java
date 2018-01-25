package com.sygamer.starreduxgm.buffer;

import java.io.IOException;
import org.apache.mina.core.buffer.IoBuffer;

public class MsgEncoder {
	public static byte[] encode(Msg<?> msg) throws IOException{
		byte[] datas = toBytes(msg);
		IoWritBuffer buffer = new IoWritBuffer();
		buffer.putBytes(datas);
		return buffer.toBytes();
	}
	
	private static byte[] toBytes(Msg<?> message) throws IOException{
		int cmd = message.getCmd();
		Object param = message.getParam();
		IoWritBuffer writer = new IoWritBuffer(1024, true);
		IoBuffer ioBuffer = writer.getIoBuffer();
		ioBuffer.putInt(cmd);
		writer.write(param);
		byte[] rs = writer.toBytes();
		ioBuffer.free();
		return rs;
	}
}
package com.sygamer.starreduxgm.buffer;

import java.io.IOException;

public class MsgDecoder {
	public static Msg<?> decode(byte[] data) throws IOException{
		IoReadBuffer buffer =  IoReadBuffer.wrap(data);
		Msg<Object>msg = new Msg<Object>();
		buffer.getInt();
		int cmd = buffer.getInt();
		Object param = buffer.read();
		msg.setCmd(cmd);
		msg.setParam(param);
		return msg;
	}
}

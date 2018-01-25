package com.sygamer.starreduxgm.buffer;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.buffer.IoBuffer;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;


public class IoReadBuffer {
	
	
	protected IoBuffer ioBuffer;
	
	private IoReadBuffer(){
		ioBuffer = IoBuffer.allocate(256).setAutoExpand(true);
		ioBuffer.order(ByteOrder.LITTLE_ENDIAN);
	}
	
	public IoReadBuffer(IoBuffer ioBuffer){
		this.ioBuffer = ioBuffer;
		this.ioBuffer.order(ByteOrder.LITTLE_ENDIAN);
	}
	
	public static IoReadBuffer wrap(byte[] src){
		IoReadBuffer rb = new IoReadBuffer();
		rb.ioBuffer = IoBuffer.wrap(src);
		rb.ioBuffer.order(ByteOrder.LITTLE_ENDIAN);
		return rb;
	}
	
	public Object read() throws IOException{
		byte tag = ioBuffer.get();
		switch (tag) {
		case BufferType.NIL: {
			return null;
		}case BufferType.BYTE: {
			byte v = ioBuffer.get();
			return v;
		}case BufferType.BOOLEAN:{
			byte v = ioBuffer.get();
			return v == 0?false:true;
		}case BufferType.SHORT:{
			short v = ioBuffer.getShort();
			return v;
		}case BufferType.INT: {
			int v = ioBuffer.getInt();
			return v;
		}case BufferType.FLOAT: {
			float v = ioBuffer.getFloat();
			return v;
		}case BufferType.LONG:{
			long v = ioBuffer.getLong();
			return v;
		}case BufferType.DATE:{
			long v = ioBuffer.getLong();
			return new Date(v);
		}case  BufferType.STRING: {
			String v = getString("utf-8");
			return v;
		}case  BufferType.BYTE_ARR:{
 			int len = ioBuffer.getInt();
			byte[] v = getBytes(len);
			return v;
		}case BufferType.ARR:{
			int len = ioBuffer.getInt();
			Object[]vs = new Object[len];
			for(int i=0;i<len;i++){
				Object v = read();
				vs[i] = v;
			}
			return vs;
		}case BufferType.LIST:{
			List<Object> vs = new ArrayList<Object>();
			int len = ioBuffer.getInt();
			for(int i=0;i<len;i++){
				Object v = read();
				vs.add(v);
			}
			return vs;
		}case  BufferType.MAP: {
			Map<Object,Object> vs = new HashMap<Object,Object>();
			int len = ioBuffer.getInt();
			for (int i = 0; i < len;i++) {
				Object key = read();
				Object var = read();
				vs.put(key, var);
			}
			return vs;
		}case BufferType.SET:{
			Set<Object> vs = new HashSet<Object>();
			int len = ioBuffer.getInt();
			for(int i=0;i<len;i++){
				Object v = read();
				vs.add(v);
			}
			return vs;
		}case BufferType.TABLE:{
			Table<Object, Object, Object> vs = HashBasedTable.create();
			int len = ioBuffer.getInt();
			for(int i=0;i<len;i++){
				Object rowkey = read();
				Object columnkey = read();
				Object value = read();
				vs.put(rowkey, columnkey, value);
			}
		}
		default:
			throw new IOException("read_object:type error" + tag);
		}
	}
	
	public byte getByte()throws IOException{
		if(!ioBuffer.hasRemaining())
			throw new IOException("no date read to byte");
		return ioBuffer.get();
	}
	
	public boolean getBoolean()throws IOException{
		if(!ioBuffer.hasRemaining())
			throw new IOException("no date read to boolean");
		return ioBuffer.get()==1?true:false;
	}
	
	public short getShort()throws IOException{
		if(ioBuffer.remaining()<2)
			throw new IOException("no date read to short");
		return ioBuffer.getShort();
	}
	
	public int getInt()throws IOException{
		if(ioBuffer.remaining()<4)
			throw new IOException("no date read to int");
		return ioBuffer.getInt();
	}
	
	public long getLong() throws IOException{
		if(ioBuffer.remaining()<8)
			throw new IOException("no date read to long");
		return ioBuffer.getLong();
	}
	
	public byte[] getBytes(int len) throws IOException {
		int remainLen = ioBuffer.remaining();
		if(len>remainLen)
			throw new IOException(IoReadBuffer.class + String.format("BufferUnderflow. expect length %s, remain length %s.", len, remainLen));
		byte[] data = new byte[len];
		int off = 0;
		ioBuffer.get(data, off, len);
		return data;
	}
	
	public String getString(String charsetName) throws IOException{
		int len = ioBuffer.getInt();
		byte[] data = getBytes(len);
		len = data.length;
		while(len > 0 && data[len-1] == 0)
			len --;
		return new String(data,0,len,charsetName);
	}

	public IoBuffer getIoBuffer() {
		return ioBuffer;
	}
	
	public void free(){
		ioBuffer.free();
	}
}

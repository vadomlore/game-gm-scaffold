package com.sygamer.starreduxgm.buffer;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.mina.core.buffer.IoBuffer;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

public class IoWritBuffer{
	protected IoBuffer out;
	
	public IoWritBuffer(){
		out = IoBuffer.allocate(256).setAutoExpand(true);
		out.order(ByteOrder.LITTLE_ENDIAN);
	}
	
	public static IoWritBuffer allocate(){
		IoWritBuffer wrb = new IoWritBuffer();
		return wrb;
	}
	
	public IoWritBuffer(int capacity,boolean isAutoExpand){
		this.out = IoBuffer.allocate(capacity).setAutoExpand(isAutoExpand);
		this.out.order(ByteOrder.LITTLE_ENDIAN);
	}

	@SuppressWarnings("rawtypes")
	public void write(Object o) throws IOException{
		if (o == null){
			out.put(BufferType.NIL);//put(Type.TYPE_NIL);
		}else if(o instanceof Byte){
			out.put(BufferType.BYTE);
			out.put((Byte)o);
		}else if(o instanceof Boolean){
			out.put(BufferType.BOOLEAN);
			out.put((byte)((Boolean)o?1:0));
		}else if(o instanceof Short){
			out.put(BufferType.SHORT);
			out.putShort((Short)o);
		}else if (o instanceof Integer){
			out.put(BufferType.INT);
			out.putInt((Integer)o);
		}else if(o instanceof Float){
			out.put(BufferType.FLOAT);
			out.putFloat((Float)o);
		}else if(o instanceof Long){
			out.put(BufferType.LONG);
			out.putLong((Long)o);
		}else if(o instanceof Date){
			out.put(BufferType.DATE);
			out.putLong(((Date)o).getTime());
		}else if(o instanceof String){
			out.put(BufferType.STRING);
			putBytes(((String)o).getBytes("utf-8"));
		}else if(o instanceof byte[]){
			out.put(BufferType.BYTE_ARR);
			putBytes((byte[])o);
		}else if(o instanceof Object[]){
			out.put(BufferType.ARR);
			int len = ((Object[]) o).length;
			out.putInt(len);
			for(int i=0;i<((Object[])o).length;i++)
				write(((Object[])o)[i]);
		}else if(o instanceof String[]){
			out.put(BufferType.ARR);
			int len = ((String[]) o).length;
			out.putInt(len);
			for(int i=0;i<((String[])o).length;i++)
				write(((String[])o)[i]);
		}else if(o instanceof boolean[]){
			out.put(BufferType.ARR);
			int len = ((boolean[]) o).length;
			out.putInt(len);
			for(int i=0;i<((boolean[])o).length;i++)
				write(((boolean[])o)[i]);
		}else if(o instanceof Boolean[]){
			out.put(BufferType.ARR);
			int len = ((Boolean[]) o).length;
			out.putInt(len);
			for(int i=0;i<((Boolean[])o).length;i++)
				write(((Boolean[])o)[i]);
		}else if(o instanceof short[] ){
			out.put(BufferType.ARR);
			int len = ((short[]) o).length;
			out.putInt(len);
			for(int i=0;i<((short[])o).length;i++)
				write(((short[])o)[i]);
		}else if(o instanceof Short[]){
			out.put(BufferType.ARR);
			int len = ((Short[]) o).length;
			out.putInt(len);
			for(int i=0;i<((Short[])o).length;i++)
				write(((Short[])o)[i]);
		}else if(o instanceof int[]){
			out.put(BufferType.ARR);
			int len = ((int[]) o).length;
			out.putInt(len);
			for(int i=0;i<((int[])o).length;i++)
				write(((int[])o)[i]);
		}else if(o instanceof Integer[]){
			out.put(BufferType.ARR);
			int len = ((Integer[]) o).length;
			out.putInt(len);
			for(int i=0;i<((Integer[])o).length;i++)
				write(((Integer[])o)[i]);
		}else if(o instanceof float[]){
			out.put(BufferType.ARR);
			int len = ((float[]) o).length;
			out.putInt(len);
			for(int i=0;i<((float[])o).length;i++)
				write(((float[])o)[i]);
		}else if(o instanceof Float[]){
			out.put(BufferType.ARR);
			int len = ((Float[]) o).length;
			out.putInt(len);
			for(int i=0;i<((Float[])o).length;i++)
				write(((Float[])o)[i]);
		}else if(o instanceof long[]){
			out.put(BufferType.ARR);
			int len = ((long[]) o).length;
			out.putInt(len);
			for(int i=0;i<((long[])o).length;i++)
				write(((long[])o)[i]);
		}else if(o instanceof Long[]){
			out.put(BufferType.ARR);
			int len = ((Long[]) o).length;
			out.putInt(len);
			for(int i=0;i<((Long[])o).length;i++)
				write(((Long[])o)[i]);
		}else if(o instanceof Date[]){
			out.put(BufferType.ARR);
			int len = ((Date[]) o).length;
			out.putInt(len);
			for(int i=0;i<((Date[])o).length;i++)
				write(((Date[])o)[i]);
		}else if(o instanceof List[]){
			out.put(BufferType.ARR);
			int len = ((List[]) o).length;
			out.putInt(len);
			for(int i=0;i<((List[])o).length;i++)
				write(((List[])o)[i]);
		}else if(o instanceof Map[]){
			out.put(BufferType.ARR);
			int len = ((Map[]) o).length;
			out.putInt(len);
			for(int i=0;i<((Map[])o).length;i++)
				write(((Map[])o)[i]);
		}else if(o instanceof List){
			out.put(BufferType.LIST);
			List os = (List)o;
			int len = os.size();
			out.putInt(len);
			for(int i=0;i<os.size();i++)
				write((os.get(i)));
		}else if (o instanceof Map){
			Map mo = (Map) o;
			int len = mo.size();
			out.put(BufferType.MAP); // type
			out.putInt(len); // num
			Iterator it = mo.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				Object var = mo.get(key);
				write(key);
				write(var);
			}
		}else if(o instanceof Set){
			out.put(BufferType.SET);
			Set os = (Set)o;
			int len = os.size();
			out.putInt(len);
			for(Object v:os)
				write(v);
		}else if(o instanceof Table){
			Table<?, ?, ?> mo = (Table<?,?,?>)o;
			int len = mo.size();
			out.put(BufferType.TABLE);// type
			out.putInt(len); // num
			Set<?> cells = mo.cellSet();
	        for(Object r : cells){
	        	Cell<?,?,?> cell = (Cell<?,?,?>)r;
	        	Object rowkey = cell.getRowKey();
	        	Object columnkey = cell.getColumnKey();
	        	Object value = cell.getValue();
	        	write(rowkey);
	        	write(columnkey);
	        	write(value);
	        }
		}else{
			throw new IOException("unsupported object:"+o + " type:" + o.getClass().getName());
		}
	}
	
	public void putByte(byte in){
		out.put(in);
	}
	
	public void putBytes(byte[] in){
		out.putInt(in.length);
		out.put(in);
	}
	
	public void putBoolean(boolean in){
		out.put((byte)(in?1:0));
	}
	
	public void putInt(int in){
		out.putInt(in);
	}
	
	public byte[] toBytes(Object o) throws IOException{
		write(o);
		return toBytes();
	}
	
	public byte[] toBytes(){
		int pos = out.position();
		byte[] ret = new byte[pos];
		System.arraycopy(out.array(), 0, ret,0,pos);
		return ret;
	}
	
	public IoBuffer getIoBuffer() {
		return out;
	}
}

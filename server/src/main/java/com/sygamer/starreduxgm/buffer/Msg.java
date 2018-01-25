package com.sygamer.starreduxgm.buffer;

/**
 * 消息
 * @author chenxin
 */
public class Msg<T> {
	
	private int cmd;
	
	private T param;
	
	private Object extParam;
	
	public Msg(){
	}
	
	public Msg(int cmd){
		this.cmd = cmd;
	}
	
	public Msg(int cmd,T param){
		this.cmd = cmd;
		this.param = param;
	}
	
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public T getParam() {
		return param;
	}
	public void setParam(T param) {
		this.param = param;
	}

	public Object getExtParam() {
		return extParam;
	}

	public void setExtParam(Object extParam) {
		this.extParam = extParam;
	}
}

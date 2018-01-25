package com.sygamer.starreduxgm.business.model;

import java.io.Serializable;

/**
 * Created by LF on 2017/12/4 0004.
 */
public class GmResult implements Serializable {
    private int status; //状态
    private String msg;//消息
    private Object data;//数据

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

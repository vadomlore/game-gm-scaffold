package com.sygamer.starreduxgm.business.model;

import java.sql.Timestamp;

/**
 * Created by LF on 2017/12/11 0011.
 */
public class RechargeLog {
    private String pid;
    private Integer productid;
    private Integer productnum;
    private String extaward;
    private Timestamp crttime;
    private Double price;
    private Integer state;
    private String appid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Integer getProductnum() {
        return productnum;
    }

    public void setProductnum(Integer productnum) {
        this.productnum = productnum;
    }

    public String getExtaward() {
        return extaward;
    }

    public void setExtaward(String extaward) {
        this.extaward = extaward;
    }

    public Timestamp getCrttime() {
        return crttime;
    }

    public void setCrttime(Timestamp crttime) {
        this.crttime = crttime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}

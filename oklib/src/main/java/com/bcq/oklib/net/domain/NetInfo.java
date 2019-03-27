package com.bcq.oklib.net.domain;

import java.io.Serializable;

/**
 * @author: BaiCQ
 * @ClassName: NetInfo
 * @date: 2018/6/27
 * @Description: NetInfo Json解析实体
 */
public class NetInfo implements Serializable{
    //code
    private int status;
    //net info
    private String sysMsg;
    //数据集
    private String body;
    //页码索引
    private int pageIndex;
    //总页
    private int pageTotal;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSysMsg() {
        return sysMsg;
    }

    public void setSysMsg(String sysMsg) {
        this.sysMsg = sysMsg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }
}

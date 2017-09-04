package com.coral.rxjava.error;

/**
 * Created by ccc on 2017/8/29.
 */
public class Number {

    private Integer status;
    private Integer data;

    public Number(Integer status, Integer data) {
        this.status = status;
        this.data = data;
    }

    public Number(Integer data) {
        this.data = data;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

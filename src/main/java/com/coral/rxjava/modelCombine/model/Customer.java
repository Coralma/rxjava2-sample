package com.coral.rxjava.modelCombine.model;

/**
 * Created by ccc on 2017/8/8.
 */
public class Customer {

    private String name;
    private String mobile;

    public Customer(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

package com.coral.rxjava.modelCombine.model;

import java.util.List;

/**
 * Created by ccc on 2017/8/8.
 */
public class Product {

    private String name;
    private Customer customers;
    private Insured insureds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomers() {
        return customers;
    }

    public void setCustomers(Customer customers) {
        this.customers = customers;
    }

    public Insured getInsureds() {
        return insureds;
    }

    public void setInsureds(Insured insureds) {
        this.insureds = insureds;
    }
}

package com.coral.rxjava.modelCombine.model;

import java.util.List;

/**
 * Created by ccc on 2017/8/8.
 */
public class Product {

    private String name;
    private List<Customer> customers;
    private List<Insured> insureds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Insured> getInsureds() {
        return insureds;
    }

    public void setInsureds(List<Insured> insureds) {
        this.insureds = insureds;
    }
}

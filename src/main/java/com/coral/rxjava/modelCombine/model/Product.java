package com.coral.rxjava.modelCombine.model;

import java.util.List;

/**
 * Created by ccc on 2017/8/8.
 */
public class Product {

    private Long id;
    private Customer customers;
    private Insured insureds;

    public Product(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

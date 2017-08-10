package com.coral.rxjava.modelCombine.callable;

import com.coral.rxjava.modelCombine.DaoCallable;
import com.coral.rxjava.modelCombine.model.Customer;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by ccc on 2017/8/8.
 */
public class CustomerCallable implements DaoCallable<Customer> {

    public Customer call(Long productId) {
        Customer customer = new Customer("Coral-" + productId, "13817225094");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return customer;
    }
}

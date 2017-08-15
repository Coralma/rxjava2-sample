package com.coral.rxjava.modelCombine.callable;

import com.coral.rxjava.modelCombine.DaoCallable;
import com.coral.rxjava.modelCombine.model.Address;
import com.coral.rxjava.modelCombine.model.Insured;

/**
 * Created by ccc on 2017/8/14.
 */
public class AddressCallable implements DaoCallable<Address> {
    @Override
    public Address call(Long productId) {
        return new Address();
    }
}

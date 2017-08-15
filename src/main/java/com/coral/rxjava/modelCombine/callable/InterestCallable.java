package com.coral.rxjava.modelCombine.callable;

import com.coral.rxjava.modelCombine.DaoCallable;
import com.coral.rxjava.modelCombine.model.Interest;

/**
 * Created by ccc on 2017/8/14.
 */
public class InterestCallable implements DaoCallable<Interest> {
    @Override
    public Interest call(Long productId) {
        return new Interest();
    }
}

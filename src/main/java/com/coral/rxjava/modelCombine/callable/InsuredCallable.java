package com.coral.rxjava.modelCombine.callable;

import com.coral.rxjava.modelCombine.DaoCallable;
import com.coral.rxjava.modelCombine.model.Insured;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by ccc on 2017/8/8.
 */
public class InsuredCallable implements DaoCallable<Insured> {

    public Insured call(Long productId) {
        Insured insured = new Insured("Insured-" + productId);
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return insured;
    }
}

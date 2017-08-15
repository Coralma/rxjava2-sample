package com.coral.rxjava.modelCombine.callable;

import com.coral.rxjava.modelCombine.DaoCallable;
import com.coral.rxjava.modelCombine.model.Address;
import com.coral.rxjava.modelCombine.model.Company;

/**
 * Created by ccc on 2017/8/14.
 */
public class CompanyCallable implements DaoCallable<Company> {
    @Override
    public Company call(Long productId) {
        return new Company();
    }
}

package com.coral.rxjava.modelCombine;

import java.util.List;

/**
 * Created by ccc on 2017/8/9.
 */
public interface DaoCallable<R> {

    R call(Long productId);
}

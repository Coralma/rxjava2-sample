package com.coral.rxjava.basic;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by ccc on 2017/8/7.
 */
public class HelloRxjava {

    public static void main(String[] args) {
        Flowable.just("Hello world")
                .subscribe(new Consumer<String>() {
                    @Override public void accept(String s) {
                        System.out.println(s);
                    }
                });
    }
}
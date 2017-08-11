package com.coral.rxjava.basic;

import com.coral.rxjava.modelCombine.model.Product;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by ccc on 2017/8/9.
 */
public class FlowSample {

    public static void main(String[] args) {
        final Product product = new Product(1l);
        /*Observable.create(new ObservableOnSubscribe<Product>() {
            @Override
            public void subscribe(ObservableEmitter<Product> observableEmitter) throws Exception {
                *//*observableEmitter.onNext(product);
                observableEmitter.onComplete();*//*
            }
        }).flatMap(new Function<Product, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Product product) throws Exception {
                return null;
            }
        });*/

    }
}

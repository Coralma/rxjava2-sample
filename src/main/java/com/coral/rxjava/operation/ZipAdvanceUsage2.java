package com.coral.rxjava.operation;

import com.coral.rxjava.modelCombine.DaoCallable;
import com.coral.rxjava.modelCombine.callable.CustomerCallable;
import com.coral.rxjava.modelCombine.callable.InsuredCallable;
import com.coral.rxjava.modelCombine.model.Customer;
import com.coral.rxjava.modelCombine.model.Insured;
import com.coral.rxjava.modelCombine.model.Product;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ccc on 2017/8/10.
 */
public class ZipAdvanceUsage2 {

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        Long productId = 6000l;

        ZipAdvanceUsage2 zipAdvanceUsage = new ZipAdvanceUsage2();
        Observable customerObservable = zipAdvanceUsage.createCallableObservable(new CustomerCallable(),productId);
        Observable insuredObservable = zipAdvanceUsage.createCallableObservable(new InsuredCallable(),productId);
        List<Observable<Object>> daoCallables = Lists.newArrayList();
        daoCallables.add(customerObservable);
        daoCallables.add(insuredObservable);
        Iterable<Observable<Object>> ite = Iterables.concat(daoCallables);
        Observable.zip(ite,new Function<Object[], Product>() {
            @Override
            public Product apply(Object[] daoCallables) throws Exception {
                Product p = new Product(123l);
                return p;
            }
        }).subscribe(new Consumer<Product>() {
            @Override
            public void accept(Product product) throws Exception {
                System.out.println(product);
            }
        });

        Thread.sleep(Long.MAX_VALUE);
    }

    protected <R> Observable<R> createCallableObservable(final DaoCallable<R> callable, final Long productId) {
        return Observable.create(new ObservableOnSubscribe<R>() {
            @Override
            public void subscribe(ObservableEmitter<R> e) throws Exception {
                System.out.println(callable.getClass().getSimpleName() + " , thread is " + Thread.currentThread().getName());
                R result = callable.call(productId);
                e.onNext(result);
            }
        }).subscribeOn(Schedulers.from(fixedThreadPool));
    }
}

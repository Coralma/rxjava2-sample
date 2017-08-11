package com.coral.rxjava.operation;

import com.coral.rxjava.modelCombine.DaoCallable;
import com.coral.rxjava.modelCombine.callable.CustomerCallable;
import com.coral.rxjava.modelCombine.callable.InsuredCallable;
import com.coral.rxjava.modelCombine.model.Customer;
import com.coral.rxjava.modelCombine.model.Insured;
import com.coral.rxjava.modelCombine.model.Product;
import com.google.gson.Gson;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ccc on 2017/8/10.
 */
public class ZipAdvanceUsage {

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        Long productId = 6000l;

        ZipAdvanceUsage zipAdvanceUsage = new ZipAdvanceUsage();
        Observable<Customer> customerObservable = zipAdvanceUsage.createCallableObservable(new CustomerCallable(),productId);
        Observable<Insured> insuredObservable = zipAdvanceUsage.createCallableObservable(new InsuredCallable(),productId);

        Observable.zip(customerObservable, insuredObservable, new BiFunction<Customer, Insured, Product>() {
            @Override
            public Product apply(Customer customer, Insured insured) throws Exception {
                Product p = new Product(1l);
                p.setInsureds(insured);
                p.setCustomers(customer);
                return p;
            }
        }).subscribe(new Consumer<Product>() {
            @Override
            public void accept(Product product) throws Exception {
                Gson gson = new Gson();
                System.out.println(gson.toJson(product));
            }
        });
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

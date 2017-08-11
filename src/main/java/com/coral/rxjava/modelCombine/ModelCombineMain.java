package com.coral.rxjava.modelCombine;

import com.coral.rxjava.modelCombine.callable.CustomerCallable;
import com.coral.rxjava.modelCombine.callable.InsuredCallable;
import com.coral.rxjava.modelCombine.model.Customer;
import com.coral.rxjava.modelCombine.model.Insured;
import com.coral.rxjava.modelCombine.model.Product;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ccc on 2017/8/9.
 */
public class ModelCombineMain {

    private static Gson gson = new Gson();
    private Long productId = 1l;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        /*for(long i=0;i<100;i++) {
            ModelCombineMain main = new ModelCombineMain();
            Product product = main.execute(i).blockingFirst();
            System.out.println(gson.toJson(product));
        }*/
        ModelCombineMain main = new ModelCombineMain();
        Object product = main.iterableExecute(123l).blockingFirst();
        System.out.println(gson.toJson(product));
    }

    public Observable iterableExecute(Long productId) {
        /*List<DaoCallable> callables = Lists.newArrayList();
        callables.add(new InsuredCallable());
        callables.add(new CustomerCallable());*/
        List<Observable<DaoCallable>> callables = Lists.newArrayList();
        callables.add(Observable.just((DaoCallable)new InsuredCallable()));
        callables.add(Observable.just((DaoCallable)new CustomerCallable()));

        /*DaoCallable[] callableArray = {new InsuredCallable(), new CustomerCallable()};
        Iterable<DaoCallable> iterable = Arrays.asList(callableArray);*/
        return Observable.zip(callables, new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] o) throws Exception {
                return null;
            }
        });
    }

    public Observable<Product> execute(Long productId) {
        this.productId = productId;
        return Observable.zip(createCallableObservable(new CustomerCallable()), createCallableObservable(new InsuredCallable()), new BiFunction<Customer, Insured, Product>() {
            @Override
            public Product apply(Customer customer, Insured insured) throws Exception {
                System.out.println("Observable.zip, thread is " + Thread.currentThread().getName());
                Product product = new Product(1l);
                product.setCustomers(customer);
                product.setInsureds(insured);
                return product;
            }
        }).subscribeOn(Schedulers.io());
    }

    public <R> Iterable<R> callableIterable() {
        /*Observable<Customer> customerObservable = createCallableObservable(new CustomerCallable());
        Observable<Insured> insuredObservable = createCallableObservable(new InsuredCallable());*/
        /*Iterable<Observable<R>> iterableObservable = Iterables.concat(
                createCallableObservable(new CustomerCallable()),
                createCallableObservable(new InsuredCallable())
        );*/
        return null;
    }

    protected <R> Observable<R> createCallableObservable(final DaoCallable<R> callable) {
        return Observable.create(new ObservableOnSubscribe<R>() {
            @Override
            public void subscribe(ObservableEmitter<R> e) throws Exception {
                System.out.println(callable.getClass().getSimpleName() + " , thread is " + Thread.currentThread().getName());
                R result = callable.call(productId);
                e.onNext(result);
            }
        });
    }


    public Observable<Customer> createCustomerObservable(final Long productId) {
        return Observable.create(new ObservableOnSubscribe<Customer>() {
            @Override
            public void subscribe(ObservableEmitter<Customer> e) throws Exception {
                /*Stopwatch sw = Stopwatch.createStarted();
                customerCallable.call(productId);
                System.out.println(sw.stop() + ", Thread Id of createCustomerObservable is: " + Thread.currentThread().getName() + " of " + Thread.currentThread().getId());
                subscriber.onNext(); //发射customer*/
            }
        });
    }

    public Observable<Insured> createInsuredObservable(final Long productId) {
        return Observable.create(new ObservableOnSubscribe<Insured>() {
            @Override
            public void subscribe(ObservableEmitter<Insured> e) throws Exception {
                /*Stopwatch sw = Stopwatch.createStarted();
                subscriber.onNext(insuredLoader.loadInsured(productId)); //发射insured
                System.out.println(sw.stop() + ", Thread Id of createInsuredObservable is: " + Thread.currentThread().getName() + " of " + Thread.currentThread().getId());*/
            }
        });
    }
}

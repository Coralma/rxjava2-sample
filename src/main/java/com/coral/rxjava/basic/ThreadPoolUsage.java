package com.coral.rxjava.basic;

import com.google.common.collect.Lists;
import io.reactivex.*;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by ccc on 2017/8/11.
 */
public class ThreadPoolUsage {

    public static void main(String[] args) throws InterruptedException {
        /*schedulerDemo1();
        schedulerDemo2();
        schedulerDemo3();*/

        List<String> letters = Lists.newArrayList("a","b","c","d","e","f","g");
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        Flowable<String> flowable = Flowable.fromIterable(letters);
        flowable.parallel()
                .runOn(Schedulers.from(fixedThreadPool))
                .sequential()
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        s = s + "-" + s;
                        System.out.println(s + " forEach, thread is " + Thread.currentThread().getName());
                    }
                });
        Iterable<String> iterable = flowable.blockingIterable();
        for(String s : iterable) {
            System.out.println("blocking result: " + s);
        }
        //Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * schedulerDemo1()表明，Observable在外执行，Consumer在另一个线程中执行
     */
    public static void schedulerDemo1() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i = 0; i < 5; i++) {
                            System.out.println("发射线程:"  + Thread.currentThread().getName()+ "---->" + "发射:" + i);
                            Thread.sleep(1000);
                            e.onNext(i);
                        }
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())   //设置可观察对象在Schedulers.io()的线程中发射数据
                .observeOn(Schedulers.single())//设置观察者在AndroidSchedulers.mainThread()的线程中处理数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer i) throws Exception {
                        System.out.println("接收线程:"  + Thread.currentThread().getName()+ "---->" + "接收:" + i);
                    }
                });
    }


    /**
     * 我们发现发射和处理数据均是在RxCachedThreadScheduler线程中，第二次通过subscribeOn指定的线程不起作用。
     */
    public static void schedulerDemo2() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i = 0; i < 5; i++) {
                            System.out.println("发射线程:" + Thread.currentThread().getName() + "---->" + "发射:" + i);
                            Thread.sleep(1000);
                            e.onNext(i);
                        }
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())//设置可观察对象在Schedulers.io()的线程中发射数据
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer i) throws Exception {
                        System.out.println("处理线程:" + Thread.currentThread().getName() + "---->" + "处理:" + i);
                        return i;
                    }
                })
                .subscribeOn(Schedulers.newThread())//指定map操作符在Schedulers.newThread()的线程中处理数据
                .observeOn(Schedulers.single())//设置观察者在AndroidSchedulers.mainThread()的线程中处理数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer i) throws Exception {
                        System.out.println("接收线程:" + Thread.currentThread().getName() + "---->" + "接收:" + i);
                    }
                });
    }

    public static void schedulerDemo3() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i = 0; i < 2; i++) {
                            System.out.println("发射线程:" + Thread.currentThread().getName() + "---->" + "发射:" + i);
                            Thread.sleep(1000);
                            e.onNext(i);
                        }
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())//设置可观察对象在Schedulers.io()的线程中发射数据
                .observeOn(Schedulers.newThread())//指定map操作符在Schedulers.newThread()的线程中处理数据
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer i) throws Exception {
                        System.out.println("Map处理线程:" + Thread.currentThread().getName() + "---->" + "处理:" + i);
                        return i;
                    }
                })
                .observeOn(Schedulers.computation())//设置观察者在AndroidSchedulers.mainThread()的线程中处理数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer i) throws Exception {
                        System.out.println("subscribe接收线程:" + Thread.currentThread().getName() + "---->" + "接收:" + i);
                    }
                });
    }

    public static void internalThread() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Observable.interval(100, TimeUnit.MILLISECONDS, Schedulers.from(executor))
                .forEach(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println(Thread.currentThread().getName());
                    }
                });
    }
}

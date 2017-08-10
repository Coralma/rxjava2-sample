package com.coral.rxjava.operation;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

/**
 * Created by ccc on 2017/8/10.
 */
public class WindowUsage {

    public static void main(String[] args) throws InterruptedException {
        Observable.interval(1,TimeUnit.SECONDS)
                .take(10)
                .window(3, TimeUnit.SECONDS).subscribe(new Consumer<Observable<Long>>() {
            @Override
            public void accept(Observable<Long> longObservable) throws Exception {
                longObservable.subscribe(innerConsumer());
            }
        });
        Thread.sleep(Long.MAX_VALUE);
    }

    public static Consumer<Long> innerConsumer() {
        return new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println(aLong);
            }
        };
    }
}

package com.coral.rxjava.basic;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.reactivex.*;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by ccc on 2017/8/14.
 */
public class FlowableUsage {

    List<String> letters = Lists.newArrayList("a","b","c","d","e","f","g");
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
    Gson gson = new Gson();

    public static void main(String[] args) {
        FlowableUsage fu = new FlowableUsage();
        //fu.createFlowable();
        fu.createAdvanceFlowable();

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createFlowable() {
        Flowable.fromIterable(letters)
                .parallel(letters.size())
                .runOn(Schedulers.from(fixedThreadPool)).map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        System.out.println(s + " map, thread is " + Thread.currentThread().getName());
                        return s + "-" + s;
                    }
                })
                .sequential()
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s + " forEach, thread is " + Thread.currentThread().getName());
                    }
                });
    }

    public void createAdvanceFlowable() {
        Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> e) throws Exception {
                while(true) {
                    e.onNext(System.currentTimeMillis());
                    Thread.sleep(50);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .buffer(100, TimeUnit.MILLISECONDS, 100)
                .map(new Function<List<Object>, Object>() {
                    @Override
                    public Object apply(List<Object> objects) throws Exception {
                        System.out.println("map: " + gson.toJson(objects));
                        return Observable.fromIterable(objects);
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("Consumer: "+gson.toJson(o));
                    }
                });
    }
}

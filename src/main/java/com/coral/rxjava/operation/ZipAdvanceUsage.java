package com.coral.rxjava.operation;

import com.coral.rxjava.operation.model.Student;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccc on 2017/8/10.
 */
public class ZipUsage {

    public static void main(String[] args) throws InterruptedException {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
            }
        });

        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("这是");
                e.onNext("这个是");
                e.onNext("这个则是");
            }
        });

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("个");
                e.onNext("只");
                e.onNext("条");
                e.onNext("张");
                e.onNext("本");
                e.onNext("副");
            }
        });

        Observable.zip(observable, observable1, observable2, new Function3<Integer, String, String, String>() {
            @Override
            public String apply(Integer integer, String s, String s2) throws Exception {
                return s + integer + s2;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }
}

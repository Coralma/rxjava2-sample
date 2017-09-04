package com.coral.rxjava.basic;

import com.google.common.collect.Lists;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import java.util.List;

/**
 * Created by ccc on 2017/8/18.
 */
public class ErrorHandlingUsage {

    public static void main(String[] args) throws InterruptedException {
        //simpleObservableError();
        simpleConsumerError();
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void simpleObservableError() {
        final List<String> chat = Lists.newArrayList("a","b","c","d","e");
        Iterable<String> iterable = Observable.fromIterable(chat).map(new Function<String, String>() {
            @Override
            public String apply(String strings) throws Exception {
                if(strings.equals("d")) {
                    return chat.get(100);
                }
                return strings + "_" + strings ;
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
                return Observable.just("NOE Error");
            }
        }).blockingIterable();

        for(String ite : iterable) {
            System.out.println(ite);
        }
    }

    public static Iterable<Observable<String>> delayError() {
        return Lists.newArrayList(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("a");
                e.onNext("b");
                List<String> data = Lists.newArrayList();
                e.onNext(data.get(100));
                e.onNext("d");
                e.onNext("e");
            }
        }));
    }

    public static void simpleConsumerError() {
        Observable.concatDelayError(delayError())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println(throwable.getMessage());
            }
        });
    }
}

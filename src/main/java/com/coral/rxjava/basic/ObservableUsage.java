package com.coral.rxjava.basic;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 官方文档： http://reactivex.io/documentation/operators.html#creating
 */
public class ObservableUsage {

    public static void main(String[] args) {
        createNormalObservable().subscribe(createConsumer());
        createNormalObservable().subscribe(createConsumer(), createErrorConsumer(), createComplete());
        createJust().subscribe(createConsumer());
        System.out.println("Just Result: " + createJust().blockingFirst());
        createDefer().subscribe(createConsumer());
        zipIterableOfObservables().subscribe(createConsumer());
        /*createNormalObservable().join(createJust(), )*/
    }

    public static Observable<String> createNormalObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext("Normal Observable");
                observableEmitter.onComplete();
            }
        });
    }

    public static Observable<String> createJust() {
        return Observable.just("Just Observable");
    }

    //这个方法很有用，defer操作符需要指定一个Obserable的工厂方法，然后它只会在有订阅者订阅它时才会创建Obserable，
    // 而且是为每个订阅者创建自己的Obserable；
    public static Observable<String> createDefer() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just("Defer");
            }
        });
    }

    public static Observable zipIterableOfObservables() {
        List<Observable<Integer>> observables =
                new ArrayList<Observable<Integer>>();
        observables.add(Observable.just(1, 2, 3));
        observables.add(Observable.just(1, 2, 3));

        return Observable.zip(observables, new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] o) throws Exception {
                int sum = 0;
                for (Object i : o) {
                    sum += (Integer) i;
                }
                return String.valueOf(sum);
            }
        });
    }

    public static Observable joinObservable() {
        return null;
    }

    public static Consumer<String> createConsumer() {
        return new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("Consumer: " + s);
            }
        };
    }

    public static Consumer<Throwable> createErrorConsumer() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable s) throws Exception {
                System.out.println("OnError: " + s);
            }
        };
    }

    public static Action createComplete() {
        return new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("Complete");
            }
        };
    }



}

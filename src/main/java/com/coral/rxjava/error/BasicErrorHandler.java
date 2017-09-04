package com.coral.rxjava.error;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ccc on 2017/8/29.
 */
public class BasicErrorHandler {

    private static Gson gson = new Gson();

    public static void main(String[] args) throws Exception {
        int index = 10;
        List<Number> list = Lists.newArrayList();
        for(int i=1;i <= index; i++) {
            list.add(new Number(1, i));
        }
        Observable.fromArray(list).map(new Function<List<Number>, List<Number>>() {
            @Override
            public List<Number> apply(List<Number> numbers) throws Exception {
                return mapConvert(numbers);
            }
        }).subscribe(new Consumer<List<Number>>() {
            @Override
            public void accept(List<Number> numbers) throws Exception {
                System.out.println(gson.toJson(numbers));
            }
        });
    }

    public static List<Number> mapConvert(final List<Number> numbers) {
        List<Observable<Boolean>> numberObservables = Lists.newArrayList();
        for(Number number : numbers) {
            numberObservables.add(createObservable(number));
        }
        /*Observable zipObservable = */Observable.zip(numberObservables, new Function<Object[], Boolean>() {
            @Override
            public Boolean apply(Object[] objects) throws Exception {
                return true;
            }
        }).onErrorReturn(new Function<Throwable, Boolean>() {
            @Override
            public Boolean apply(Throwable throwable) throws Exception {
                for(Number number : numbers) {
                    number.setStatus(-1);
                }
                return false;
            }
        }).timeout(500, TimeUnit.MILLISECONDS).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Boolean>>() {
            @Override
            public ObservableSource<? extends Boolean> apply(Throwable throwable) throws Exception {
                for(Number number : numbers) {
                    number.setStatus(0);
                }
                return Observable.just(false);
            }
        }).blockingFirst();
        return numbers;
    }

    public static Observable<Boolean> createObservable(final Number num) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                if(num.getData() == 5) {
                    //Thread.currentThread().sleep(800);
                    throw new RuntimeException("No point Exception.");
                }
                num.setData(num.getData() * 2);
                e.onNext(true);
                e.onComplete();
            }
        })/*.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Boolean>>() {
            @Override
            public ObservableSource<? extends Boolean> apply(Throwable throwable) throws Exception {
                System.out.println("onErrorResumeNext of createObservable");
                return Observable.error(throwable);
            }
        })*/;
    }
}

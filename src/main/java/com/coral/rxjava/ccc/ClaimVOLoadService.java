package com.coral.rxjava.ccc;

import com.coral.rxjava.ccc.loader.*;
import com.coral.rxjava.ccc.model.VehicleClaimParamVO;
import com.coral.rxjava.modelCombine.model.Insured;
import com.coral.rxjava.modelCombine.model.Product;
import com.google.common.collect.Lists;
import com.sun.org.apache.bcel.internal.generic.ILOAD;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ccc on 2017/8/14.
 */
public class ClaimVOLoadService {

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    public List<VehicleClaimParamVO> load(final List<VehicleClaimParamVO> vehicleClaimParamVOList) {
        /*Observable<List<VehicleClaimParamVO>> productObservable = Observable.create(new ObservableOnSubscribe<List<VehicleClaimParamVO>>() {
            @Override
            public void subscribe(ObservableEmitter<List<VehicleClaimParamVO>> e) throws Exception {
                e.onNext(vehicleClaimParamVOList);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.from(fixedThreadPool));
        productObservable.subscribe(consumer1());
        productObservable.subscribe(consumer2());
        productObservable.subscribe(consumer3());
        productObservable.subscribe(consumer4());
        productObservable.subscribe(consumer5());
        return productObservable.blockingFirst();*/

        List<List<Observable<Boolean>>> loaderObservableList = createLoaderObservableList(vehicleClaimParamVOList);
        for(List<Observable<Boolean>> loaderObservable : loaderObservableList) {
            Observable.zip(loaderObservable, new Function<Object[], Boolean>() {
                @Override
                public Boolean apply(Object[] objects) throws Exception {
                    return true;
                }
            }).subscribeOn(Schedulers.from(fixedThreadPool)).blockingFirst();
        }
        return vehicleClaimParamVOList;
    }

    private List<List<Observable<Boolean>>> createLoaderObservableList(List<VehicleClaimParamVO> vehicleClaimParamVOList) {
        LoadContext loadContext = new LoadContext();
        loadContext.setClaimVOList(vehicleClaimParamVOList);
        Observable<Boolean> loader1 = createObservable(new ClaimData1Loader(), loadContext);
        Observable<Boolean> loader2 = createObservable(new ClaimData2Loader(), loadContext);
        Observable<Boolean> loader3 = createObservable(new ClaimData3Loader(), loadContext);
        Observable<Boolean> loader4 = createObservable(new ClaimData4Loader(), loadContext);
        Observable<Boolean> loader5 = createObservable(new ClaimData5Loader(), loadContext);
        List<Observable<Boolean>> sep1 = Lists.newArrayList(loader1,loader2);
        List<Observable<Boolean>> sep2 = Lists.newArrayList(loader3,loader4);
        List<Observable<Boolean>> sep3 = Lists.newArrayList(loader5);
        List<List<Observable<Boolean>>> category = Lists.newArrayList(sep1,sep2,sep3);
        return category;
    }

    private Observable<Boolean> createObservable(final ILoader loader, final LoadContext loadContext) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                loader.load(loadContext);
                e.onNext(true);
                e.onComplete();
            }
        });
    }

    protected  Consumer<List<VehicleClaimParamVO>> consumer1() {
        return new Consumer<List<VehicleClaimParamVO>>() {
            @Override
            public void accept(List<VehicleClaimParamVO> vos) throws Exception {
                System.out.println("claim consumer1 thread is " + Thread.currentThread().getName());
                for(VehicleClaimParamVO p: vos) {
                    p.setClaimData("claimData-" + p.getClaimId());
                }
            }
        };
    }

    protected  Consumer<List<VehicleClaimParamVO>> consumer2() {
        return new Consumer<List<VehicleClaimParamVO>>() {
            @Override
            public void accept(List<VehicleClaimParamVO> vos) throws Exception {
                System.out.println("claim consumer2 thread is " + Thread.currentThread().getName());
                for(VehicleClaimParamVO p: vos) {
                    p.setClaimData2("claimData2-" + p.getClaimId());
                }
            }
        };
    }

    protected  Consumer<List<VehicleClaimParamVO>> consumer3() {
        return new Consumer<List<VehicleClaimParamVO>>() {
            @Override
            public void accept(List<VehicleClaimParamVO> vos) throws Exception {
                System.out.println("claim consumer3 thread is " + Thread.currentThread().getName());
                for(VehicleClaimParamVO p: vos) {
                    p.setClaimData3("claimData3-" + p.getClaimId());
                }
            }
        };
    }

    protected  Consumer<List<VehicleClaimParamVO>> consumer4() {
        return new Consumer<List<VehicleClaimParamVO>>() {
            @Override
            public void accept(List<VehicleClaimParamVO> vos) throws Exception {
                System.out.println("claim consumer4 thread is " + Thread.currentThread().getName());
                for(VehicleClaimParamVO p: vos) {
                    p.setClaimData4("claimData4-" + p.getClaimId());
                }
            }
        };
    }

    protected  Consumer<List<VehicleClaimParamVO>> consumer5() {
        return new Consumer<List<VehicleClaimParamVO>>() {
            @Override
            public void accept(List<VehicleClaimParamVO> vos) throws Exception {
                System.out.println("claim consumer5 thread is " + Thread.currentThread().getName());
                for(VehicleClaimParamVO p: vos) {
                    p.setClaimData5("claimData5-" + p.getClaimId());
                }
            }
        };
    }

}

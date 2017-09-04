package com.coral.rxjava.ccc;

import com.coral.rxjava.ccc.loader.*;
import com.coral.rxjava.ccc.model.VehicleClaimParamVO;
import com.coral.rxjava.modelCombine.model.Insured;
import com.coral.rxjava.modelCombine.model.Product;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ccc on 2017/8/14.
 */
public class ClaimVOLoadService {
    List<List<Observable<Boolean>>> a;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);

    public Map<Long, VehicleClaimParamVO> load(final List<VehicleClaimParamVO> vehicleClaimParamVOList) {
        LoadContext loadContext = new LoadContext();
        Map<Long, VehicleClaimParamVO> vehicleClaimParamVOMap = Maps.newHashMap();
        for(VehicleClaimParamVO vehicleClaimParamVO : vehicleClaimParamVOList) {
            vehicleClaimParamVOMap.put(vehicleClaimParamVO.getClaimId(), vehicleClaimParamVO);
        }
        loadContext.setClaimParamVOMap(vehicleClaimParamVOMap);

        List<List<Observable<Boolean>>> loaderObservableList = createLoaderObservableList(loadContext);
        for(List<Observable<Boolean>> loaderObservable : loaderObservableList) {
            Observable.zip(loaderObservable, new Function<Object[], Boolean>() {
                @Override
                public Boolean apply(Object[] objects) throws Exception {
                    return true;
                }
            }).blockingFirst();
        }
        return loadContext.getClaimParamVOMap();
    }

    private List<List<Observable<Boolean>>> createLoaderObservableList(LoadContext loadContext) {
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
        }).subscribeOn(Schedulers.from(fixedThreadPool));
    }
}

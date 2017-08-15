package com.coral.rxjava.ccc;

import com.coral.rxjava.ccc.model.AuditReportVO;
import com.coral.rxjava.ccc.model.VehicleClaimParamVO;
import com.google.common.collect.Lists;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ccc on 2017/8/14.
 */
public class AuditVOLoadService {

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public List<AuditReportVO> load(final List<VehicleClaimParamVO> vehicleClaimParamVOList) {
        Observable<List<AuditReportVO>> productObservable = Observable.create(new ObservableOnSubscribe<List<VehicleClaimParamVO>>() {
            @Override
            public void subscribe(ObservableEmitter<List<VehicleClaimParamVO>> e) throws Exception {
                e.onNext(vehicleClaimParamVOList);
                e.onComplete();
            }
        }).map(new Function<List<VehicleClaimParamVO>, List<AuditReportVO>>() {
            @Override
            public List<AuditReportVO> apply(List<VehicleClaimParamVO> vehicleClaimParamVO) throws Exception {
                List<AuditReportVO> auditReportVOs = Lists.newArrayList();
                for(VehicleClaimParamVO claimParamVO : vehicleClaimParamVO) {
                    AuditReportVO auditReportVO = new AuditReportVO(claimParamVO.getClaimId());
                    auditReportVO.setClaimId(claimParamVO.getClaimId());
                    auditReportVO.setClaimData(claimParamVO.getClaimData());
                    auditReportVO.setClaimData2(claimParamVO.getClaimData2());
                    auditReportVO.setClaimData3(claimParamVO.getClaimData3());
                    auditReportVO.setClaimData4(claimParamVO.getClaimData4());
                    auditReportVO.setClaimData5(claimParamVO.getClaimData5());
                    auditReportVOs.add(auditReportVO);
                }
                return auditReportVOs;
            }
        }).subscribeOn(Schedulers.from(fixedThreadPool));
        productObservable.subscribe(consumer1());
        productObservable.subscribe(consumer2());
        return productObservable.blockingFirst();
    }


    protected Consumer<List<AuditReportVO>> consumer1() {
        return new Consumer<List<AuditReportVO>>() {
            @Override
            public void accept(List<AuditReportVO> claimParamVOs) throws Exception {
                System.out.println("Audit consumer1 thread is " + Thread.currentThread().getName());
                for(AuditReportVO claimParamVO : claimParamVOs) {
                    claimParamVO.setAuditData("AuditData-" + claimParamVO.getClaimId());
                }
            }
        };
    }

    protected  Consumer<List<AuditReportVO>> consumer2() {
        return new Consumer<List<AuditReportVO>>() {
            @Override
            public void accept(List<AuditReportVO> auditReportVOs) throws Exception {
                System.out.println("Audit consumer2 thread is " + Thread.currentThread().getName());
                for (AuditReportVO claimParamVO : auditReportVOs) {
                    claimParamVO.setAuditData2("AuditData-" + claimParamVO.getClaimId());
                }
            }
        };
    }
}

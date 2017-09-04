package com.coral.rxjava.ccc;

import com.coral.rxjava.ccc.model.AuditReportVO;
import com.coral.rxjava.ccc.model.VehicleClaimParamVO;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.reactivex.*;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by ccc on 2017/8/14.
 */
public class AuditRuleAssistService {


    private Map<Long, BlockingQueue<AuditReportVO>> exchangerMap = new ConcurrentHashMap<>();
    private BlockingQueue<AuditReportVO> resultQueue = new LinkedBlockingQueue<>(1024);
    private BlockingQueue<VehicleClaimParamVO> claimVOQueue = new LinkedBlockingQueue<>(1024);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
    private Gson gson = new Gson();
    private ClaimVOLoadService claimVOLoadService = new ClaimVOLoadService();
    private AuditVOLoadService auditVOLoadService = new AuditVOLoadService();

    public AuditRuleAssistService() {
        initObservable();
    }

    public AuditReportVO processClaimPreauditRuleCheck(VehicleClaimParamVO claimVO) throws InterruptedException {
        Long claimId = claimVO.getClaimId();
        exchangerMap.put(claimId, new ArrayBlockingQueue<AuditReportVO>(1));
        claimVOQueue.put(claimVO);
        return exchangerMap.get(claimId).take();
    }

    private void initObservable() {
        Flowable.create(new FlowableOnSubscribe<VehicleClaimParamVO>() {
            @Override
            public void subscribe(FlowableEmitter<VehicleClaimParamVO> e) throws Exception {
                while(true) {
                    VehicleClaimParamVO id = claimVOQueue.take();
                    e.onNext(id);
                }
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.newThread())
                .buffer(300, TimeUnit.MILLISECONDS, 100)
                .filter(new Predicate<List<VehicleClaimParamVO>>() {
                    @Override
                    public boolean test(List<VehicleClaimParamVO> claimParamVOS) throws Exception {
                        return claimParamVOS.size() > 0;
                    }
                })
                .map(new Function<List<VehicleClaimParamVO>, Map<Long, VehicleClaimParamVO>>() {
                    @Override
                    public Map<Long, VehicleClaimParamVO> apply(List<VehicleClaimParamVO> objects) throws Exception {
                        System.out.println("Run in VehicleClaimParamVO map and the size is: " + objects.size());
                        return claimVOLoadService.load(objects);
                    }
                }).flatMap(new Function<Map<Long, VehicleClaimParamVO>, Flowable<AuditReportVO>>() {
                    @Override
                    public Flowable<AuditReportVO> apply(Map<Long, VehicleClaimParamVO> claimParamVOMap) throws Exception {
                        return Flowable.fromIterable(auditVOLoadService.load(claimParamVOMap));
                    }
                }).parallel()
                .runOn(Schedulers.from(fixedThreadPool))
                .sequential()
                .forEach(new Consumer<AuditReportVO>() {
                    @Override
                    public void accept(AuditReportVO auditReportVO) throws Exception {
                        System.out.println("执行规则:" + Thread.currentThread().getName());
                        exchangerMap.get(auditReportVO.getClaimId()).put(auditReportVO);
                    }
                });
    }
}

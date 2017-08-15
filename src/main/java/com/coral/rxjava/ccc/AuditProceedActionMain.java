package com.coral.rxjava.ccc;

import com.coral.rxjava.ccc.model.AuditReportVO;
import com.coral.rxjava.ccc.model.VehicleClaimParamVO;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ccc on 2017/8/14.
 */
public class AuditProceedActionMain {

    private AuditRuleAssistService service = new AuditRuleAssistService();
    private Gson gson = new Gson();

    public AuditReportVO audit(VehicleClaimParamVO claimParamVO) throws Exception {
        AuditReportVO auditReportVO = service.processClaimPreauditRuleCheck(claimParamVO);
        System.out.println("Final Result of claim " +  claimParamVO.getClaimId() + " is: " + gson.toJson(auditReportVO));
        return auditReportVO;
    }

    public static void main(String[] args) throws Exception {
        final AuditProceedActionMain main = new AuditProceedActionMain();
        ExecutorService executor = Executors.newScheduledThreadPool(10);
        final int threadNum = 5;
        List<Callable<AuditReportVO>> tasks = Lists.newArrayList();
        for(int i=0; i<threadNum; ++i) {
            tasks.add( new Callable<AuditReportVO>() {
                @Override
                public AuditReportVO call() throws Exception {
                    VehicleClaimParamVO claimParamVO = new VehicleClaimParamVO(randomNumber(1, 9999999));
                    return main.audit(claimParamVO);
                }
            });
        }
        List<Future<AuditReportVO>> futures = executor.invokeAll(tasks);
        for(int i=0; i<threadNum; ++i) {
            futures.get(i).get();
        }
    }

    public static long randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}

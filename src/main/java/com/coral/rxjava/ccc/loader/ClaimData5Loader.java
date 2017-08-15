package com.coral.rxjava.ccc.loader;

import com.coral.rxjava.ccc.model.AuditReportVO;
import com.coral.rxjava.ccc.model.VehicleClaimParamVO;

import java.util.List;

/**
 * Created by ccc on 2017/8/14.
 */
public class ClaimData1Loader extends AbstractLoader implements ILoader {

    @Override
    public void load(LoadContext loadContext) {
        String name = this.getClass().getSimpleName();
        System.out.println(this.getClass().getSimpleName() + " thread is " + Thread.currentThread().getName());
        for(VehicleClaimParamVO p: loadContext.getClaimVOList()) {
            p.setClaimData(name + "-" + p.getClaimId());
        }
    }
}

package com.coral.rxjava.ccc.loader;

import com.coral.rxjava.ccc.model.VehicleClaimParamVO;

/**
 * Created by ccc on 2017/8/14.
 */
public class ClaimData4Loader extends AbstractLoader implements ILoader {

    @Override
    public void load(LoadContext loadContext) {
        String name = this.getClass().getSimpleName();
        System.out.println(name + " thread is " + Thread.currentThread().getName());
        for(VehicleClaimParamVO p: loadContext.getClaimParamVOMap().values()) {
            p.setClaimData4(name + "-" + p.getClaimId());
        }
        randomSleep();
    }
}

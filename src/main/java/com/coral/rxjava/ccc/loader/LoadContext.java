package com.coral.rxjava.ccc.loader;

import com.coral.rxjava.ccc.model.AuditReportVO;
import com.coral.rxjava.ccc.model.VehicleClaimParamVO;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by ccc on 2017/8/14.
 */
public class LoadingContext {

    VehicleClaimParamVO claimVO;
    AuditReportVO reportVO;
    Map<Object, Object> contextMap = Maps.newHashMap();

    public VehicleClaimParamVO getClaimVO() {
        return claimVO;
    }

    public void setClaimVO(VehicleClaimParamVO claimVO) {
        this.claimVO = claimVO;
    }

    public AuditReportVO getReportVO() {
        return reportVO;
    }

    public void setReportVO(AuditReportVO reportVO) {
        this.reportVO = reportVO;
    }

    public void put(Object key, Object value) {
        contextMap.put(key,value);
    }

    public void get(Object key) {
        contextMap.get(key);
    }
}

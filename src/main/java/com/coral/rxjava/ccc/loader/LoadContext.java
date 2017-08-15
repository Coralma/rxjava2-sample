package com.coral.rxjava.ccc.loader;

import com.coral.rxjava.ccc.model.AuditReportVO;
import com.coral.rxjava.ccc.model.VehicleClaimParamVO;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by ccc on 2017/8/14.
 */
public class LoadContext {

    List<VehicleClaimParamVO> claimVOList;
    List<AuditReportVO> reportVOList;
    Map<Object, Object> contextMap = Maps.newHashMap();

    public List<VehicleClaimParamVO> getClaimVOList() {
        return claimVOList;
    }

    public void setClaimVOList(List<VehicleClaimParamVO> claimVOList) {
        this.claimVOList = claimVOList;
    }

    public List<AuditReportVO> getReportVOList() {
        return reportVOList;
    }

    public void setReportVOList(List<AuditReportVO> reportVOList) {
        this.reportVOList = reportVOList;
    }

    public Map<Object, Object> getContextMap() {
        return contextMap;
    }

    public void setContextMap(Map<Object, Object> contextMap) {
        this.contextMap = contextMap;
    }

    public void put(Object key, Object value) {
        contextMap.put(key,value);
    }

    public void get(Object key) {
        contextMap.get(key);
    }
}

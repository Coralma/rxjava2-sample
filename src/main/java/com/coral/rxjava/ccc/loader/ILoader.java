package com.coral.rxjava.ccc.loader;

import com.coral.rxjava.ccc.model.AuditReportVO;
import com.coral.rxjava.ccc.model.VehicleClaimParamVO;

import java.util.List;

/**
 * Created by ccc on 2017/8/14.
 */
public interface ILoader {

    void load(LoadContext loadContext);

    void setChildren(List<ILoader> children);

    void addChild(ILoader children);

    List<ILoader> getChildren();


}

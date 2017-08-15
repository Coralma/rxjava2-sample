package com.coral.rxjava.ccc.model;

/**
 * Created by ccc on 2017/8/14.
 */
public class AuditReportVO {


    private Long claimId;
    private String claimData;
    private String claimData2;
    private String claimData3;
    private String claimData4;
    private String claimData5;

    private String auditData;
    private String auditData2;

    public AuditReportVO(Long claimId) {
        this.claimId = claimId;
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public String getAuditData() {
        return auditData;
    }

    public void setAuditData(String auditData) {
        this.auditData = auditData;
    }

    public String getClaimData() {
        return claimData;
    }

    public void setClaimData(String claimData) {
        this.claimData = claimData;
    }

    public String getAuditData2() {
        return auditData2;
    }

    public void setAuditData2(String auditData2) {
        this.auditData2 = auditData2;
    }

    public String getClaimData2() {
        return claimData2;
    }

    public void setClaimData2(String claimData2) {
        this.claimData2 = claimData2;
    }

    public String getClaimData3() {
        return claimData3;
    }

    public void setClaimData3(String claimData3) {
        this.claimData3 = claimData3;
    }

    public String getClaimData4() {
        return claimData4;
    }

    public void setClaimData4(String claimData4) {
        this.claimData4 = claimData4;
    }

    public String getClaimData5() {
        return claimData5;
    }

    public void setClaimData5(String claimData5) {
        this.claimData5 = claimData5;
    }
}

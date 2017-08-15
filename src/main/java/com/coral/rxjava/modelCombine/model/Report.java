package com.coral.rxjava.modelCombine.model;

/**
 * Created by ccc on 2017/8/11.
 */
public class Report {

    private Long id;
    private Customer customers;
    private Insured insureds;
    private String reportData;
    private String extData;
    private String auditData;

    public Report(Product product) {
        this.id = product.getId();
        this.customers = product.getCustomers();
        this.insureds = product.getInsureds();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomers() {
        return customers;
    }

    public void setCustomers(Customer customers) {
        this.customers = customers;
    }

    public Insured getInsureds() {
        return insureds;
    }

    public void setInsureds(Insured insureds) {
        this.insureds = insureds;
    }

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }

    public String getExtData() {
        return extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getAuditData() {
        return auditData;
    }

    public void setAuditData(String auditData) {
        this.auditData = auditData;
    }
}

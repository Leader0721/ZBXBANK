package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/15.
 */
public class CustomListEntity implements Serializable {


    /**
     * CompanyName : 深蓝
     * ContacTel : 15969710771
     * ApplyDate : 2017-04-13 14:59:52
     * LoanAmount : 123
     * StautsName : 已通过
     * LoanApplyId : f8b2088c-f1f5-45b0-8c93-3fb7be296161
     * VersionId : 4ae01a81-2dc3-4614-8ac4-f074b93e371c
     */

    @Expose
    private String CompanyName;
    @Expose
    private String ContacTel;
    @Expose
    private String ApplyDate;
    @Expose
    private int LoanAmount;
    @Expose
    private int StatusCode;
    @Expose
    private String StautsName;

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    @Expose
    private String LoanApplyId;
    @Expose
    private String VersionId;

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getContacTel() {
        return ContacTel;
    }

    public void setContacTel(String ContacTel) {
        this.ContacTel = ContacTel;
    }

    public String getApplyDate() {
        return ApplyDate;
    }

    public void setApplyDate(String ApplyDate) {
        this.ApplyDate = ApplyDate;
    }

    public int getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(int LoanAmount) {
        this.LoanAmount = LoanAmount;
    }

    public String getStautsName() {
        return StautsName;
    }

    public void setStautsName(String StautsName) {
        this.StautsName = StautsName;
    }

    public String getLoanApplyId() {
        return LoanApplyId;
    }

    public void setLoanApplyId(String LoanApplyId) {
        this.LoanApplyId = LoanApplyId;
    }

    public String getVersionId() {
        return VersionId;
    }

    public void setVersionId(String VersionId) {
        this.VersionId = VersionId;
    }
}

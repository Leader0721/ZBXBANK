package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

/**
 * @author: ysj
 * @date: 2017-03-22 09:49
 */
public class BankSelectEntity {

    /**
     * LoanApplyId : fc4d3de0-c9d5-4f16-a8df-b261df89f93e
     * VersionId : 896f1d54-75c8-4fcf-b235-2d75cc0c547a
     */
    @Expose
    private String LoanApplyId;
    @Expose
    private String VersionId;

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

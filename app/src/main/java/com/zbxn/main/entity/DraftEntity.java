package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

/**
 * @author: ysj
 * @date: 2017-03-23 18:30
 */
public class DraftEntity {

    /**
     * BankName : TEST2
     * CompanyName : QQQAAA
     * ApplyDate : 2017-04-07 09:04:37
     * LoanAmount : 110
     * StautsName : 已通过
     * Step : 5
     * RejectMsg :
     * LoanApplyId : 7887c423-e726-4b48-bfc7-9e3a7652e561
     * VersionId : 1f411e8f-dd8d-48ab-8162-19e5b02ea46f
     */

    @Expose
    private String BankName;
    @Expose
    private String CompanyName;
    @Expose
    private String ApplyDate;
    @Expose
    private int LoanAmount;
    @Expose
    private String StautsName;
    @Expose
    private int Step;
    @Expose
    private int StautsId;

    public int getStautsId() {
        return StautsId;
    }

    public void setStautsId(int stautsId) {
        StautsId = stautsId;
    }

    @Expose
    private String RejectMsg;
    @Expose
    private String LoanApplyId;
    @Expose
    private String VersionId;

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
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

    public int getStep() {
        return Step;
    }

    public void setStep(int Step) {
        this.Step = Step;
    }

    public String getRejectMsg() {
        return RejectMsg;
    }

    public void setRejectMsg(String RejectMsg) {
        this.RejectMsg = RejectMsg;
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

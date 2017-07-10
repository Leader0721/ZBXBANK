package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * @author: ysj
 * @date: 2017-04-07 14:05
 */
public class AttachmentBindingEntity implements Serializable {


    /**
     * BusinessLicense : e14e33ce-d764-4883-8533-7203341c6f35
     * AccountOpeningPermit : e9029aaa-de13-4394-86bc-27ff333a2d98
     * TaxCertificate : 845bc423-772c-4b60-b8a4-62d4070390a9
     * CorporationIDCard : 82ad4cd6-6896-46ac-80db-0d2e11624455
     * FinanceIDCard : c810e1ed-104e-4634-bd62-863bf1d56e49
     * PartnerIDCard : c3cbf26b-1f1a-439a-853b-ec4c4231619e
     * FinanceReport : a866c151-8813-45d6-b9f1-b698bc0a5cda
     * EnterpriseArticles : 5ac52271-f643-4f18-8ea6-4693cebd6f1d
     * CapitalVerification : 14380584-00c4-4bf3-a8cc-f97abbcff66b
     * HouseProperty : f97c62af-4bca-4291-b855-01dcf61a3b1e
     * EnterpriseHistory : 42fd6110-d343-4f91-a872-b5ce25eae23e
     * FeasibilityStudy : 46acd6c0-5e6b-41b9-9521-d161d40475e2
     * OtherApproval : d9b84a2e-bc16-4a24-bca9-bdd62ae8b888
     */
    @Expose
    private String BusinessLicense;
    @Expose
    private String AccountOpeningPermit;
    @Expose
    private String TaxCertificate;
    @Expose
    private String CorporationIDCard;
    @Expose
    private String FinanceIDCard;
    @Expose
    private String PartnerIDCard;
    @Expose
    private String FinanceReport;
    @Expose
    private String EnterpriseArticles;
    @Expose
    private String CapitalVerification;
    @Expose
    private String HouseProperty;
    @Expose
    private String EnterpriseHistory;
    @Expose
    private String FeasibilityStudy;
    @Expose
    private String OtherApproval;
    @Expose
    private String OrganizationCode;
    @Expose
    private String CreditCode;

    public String getBusinessLicense() {
        return BusinessLicense;
    }

    public void setBusinessLicense(String BusinessLicense) {
        this.BusinessLicense = BusinessLicense;
    }

    public String getAccountOpeningPermit() {
        return AccountOpeningPermit;
    }

    public void setAccountOpeningPermit(String AccountOpeningPermit) {
        this.AccountOpeningPermit = AccountOpeningPermit;
    }

    public String getTaxCertificate() {
        return TaxCertificate;
    }

    public void setTaxCertificate(String TaxCertificate) {
        this.TaxCertificate = TaxCertificate;
    }

    public String getCorporationIDCard() {
        return CorporationIDCard;
    }

    public void setCorporationIDCard(String CorporationIDCard) {
        this.CorporationIDCard = CorporationIDCard;
    }

    public String getFinanceIDCard() {
        return FinanceIDCard;
    }

    public void setFinanceIDCard(String FinanceIDCard) {
        this.FinanceIDCard = FinanceIDCard;
    }

    public String getPartnerIDCard() {
        return PartnerIDCard;
    }

    public void setPartnerIDCard(String PartnerIDCard) {
        this.PartnerIDCard = PartnerIDCard;
    }

    public String getFinanceReport() {
        return FinanceReport;
    }

    public void setFinanceReport(String FinanceReport) {
        this.FinanceReport = FinanceReport;
    }

    public String getEnterpriseArticles() {
        return EnterpriseArticles;
    }

    public void setEnterpriseArticles(String EnterpriseArticles) {
        this.EnterpriseArticles = EnterpriseArticles;
    }

    public String getCapitalVerification() {
        return CapitalVerification;
    }

    public void setCapitalVerification(String CapitalVerification) {
        this.CapitalVerification = CapitalVerification;
    }

    public String getHouseProperty() {
        return HouseProperty;
    }

    public void setHouseProperty(String HouseProperty) {
        this.HouseProperty = HouseProperty;
    }

    public String getEnterpriseHistory() {
        return EnterpriseHistory;
    }

    public void setEnterpriseHistory(String EnterpriseHistory) {
        this.EnterpriseHistory = EnterpriseHistory;
    }

    public String getFeasibilityStudy() {
        return FeasibilityStudy;
    }

    public void setFeasibilityStudy(String FeasibilityStudy) {
        this.FeasibilityStudy = FeasibilityStudy;
    }

    public String getOtherApproval() {
        return OtherApproval;
    }

    public void setOtherApproval(String OtherApproval) {
        this.OtherApproval = OtherApproval;
    }

    public String getOrganizationCode() {
        return OrganizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        OrganizationCode = organizationCode;
    }

    public String getCreditCode() {
        return CreditCode;
    }

    public void setCreditCode(String creditCode) {
        CreditCode = creditCode;
    }
}

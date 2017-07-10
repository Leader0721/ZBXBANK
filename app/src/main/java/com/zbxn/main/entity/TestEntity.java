package com.zbxn.main.entity;

import java.util.List;

/**
 * @author: ysj
 * @date: 2017-03-09 17:12
 */
public class TestEntity {

    /**
     * investData : 2016
     * name : admin
     * regAddress : 历城区
     * comNature : 责任制
     * comHistory : 不多说
     * office : [{"comName":"淄博智博星济南研发中心","AmountMoney":"100万","Nature":"全资-分公司"},{"comName":"淄博智博星济南研发中心","AmountMoney":"100万","Nature":"全资-分公司"},{"comName":"淄博智博星济南研发中心","AmountMoney":"100万","Nature":"全资-分公司"},{"comName":"淄博智博星济南研发中心","AmountMoney":"100万","Nature":"全资-分公司"},{"comName":"淄博智博星济南研发中心","AmountMoney":"100万","Nature":"全资-分公司"}]
     */

    private String investData;
    private String name;
    private String regAddress;
    private String comNature;
    private String comHistory;
    private List<OfficeBean> office;

    public String getInvestData() {
        return investData;
    }

    public void setInvestData(String investData) {
        this.investData = investData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getComNature() {
        return comNature;
    }

    public void setComNature(String comNature) {
        this.comNature = comNature;
    }

    public String getComHistory() {
        return comHistory;
    }

    public void setComHistory(String comHistory) {
        this.comHistory = comHistory;
    }

    public List<OfficeBean> getOffice() {
        return office;
    }

    public void setOffice(List<OfficeBean> office) {
        this.office = office;
    }

    public static class OfficeBean {
        /**
         * comName : 淄博智博星济南研发中心
         * AmountMoney : 100万
         * Nature : 全资-分公司
         */

        private String comName;
        private String amountMoney;
        private String nature;

        public String getComName() {
            return comName;
        }

        public void setComName(String comName) {
            this.comName = comName;
        }

        public String getAmountMoney() {
            return amountMoney;
        }

        public void setAmountMoney(String AmountMoney) {
            this.amountMoney = AmountMoney;
        }

        public String getNature() {
            return nature;
        }

        public void setNature(String Nature) {
            this.nature = Nature;
        }
    }
}

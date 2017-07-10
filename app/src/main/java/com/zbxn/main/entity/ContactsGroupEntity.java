package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by U on 2017/3/24.
 */

public class ContactsGroupEntity {

    /**
     * ID : ae5c6f45-f2d9-4906-86b6-6f6dd37ccbfb
     * CorprationNO : CS43483
     * CompanyName : 测试公司4
     * ShortName : null
     * CompanyType : 其他
     * Region : null
     * ComopanyDesc : null
     * LogoUrl : null
     * UserID : null
     * UserCount : 2
     * DepartmentCount : 0
     * PositionCount : 0
     * UserPermission : 管理员
     */
    @Expose
    private String ID;//公司ID
    @Expose
    private String CorprationNO;//web端使用
    @Expose
    private String CompanyName;//公司名
    @Expose
    private String ShortName;//公司简称
    @Expose
    private String CompanyType;//公司类型
    @Expose
    private Object Region;
    @Expose
    private String ComopanyDesc;//公司描述
    @Expose
    private String LogoUrl;//公司logo
    @Expose
    private String UserID;
    @Expose
    private int UserCount;//员工数量
    @Expose
    private int DepartmentCount;//部门数量
    @Expose
    private int PositionCount;
    @Expose
    private String UserPermission;//职位权限 管理员or成员

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCorprationNO() {
        return CorprationNO;
    }

    public void setCorprationNO(String CorprationNO) {
        this.CorprationNO = CorprationNO;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public Object getShortName() {
        return ShortName;
    }

    public void setShortName(String ShortName) {
        this.ShortName = ShortName;
    }

    public String getCompanyType() {
        return CompanyType;
    }

    public void setCompanyType(String CompanyType) {
        this.CompanyType = CompanyType;
    }

    public Object getRegion() {
        return Region;
    }

    public void setRegion(Object Region) {
        this.Region = Region;
    }

    public Object getComopanyDesc() {
        return ComopanyDesc;
    }

    public void setComopanyDesc(String ComopanyDesc) {
        this.ComopanyDesc = ComopanyDesc;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String LogoUrl) {
        this.LogoUrl = LogoUrl;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public int getUserCount() {
        return UserCount;
    }

    public void setUserCount(int UserCount) {
        this.UserCount = UserCount;
    }

    public int getDepartmentCount() {
        return DepartmentCount;
    }

    public void setDepartmentCount(int DepartmentCount) {
        this.DepartmentCount = DepartmentCount;
    }

    public int getPositionCount() {
        return PositionCount;
    }

    public void setPositionCount(int PositionCount) {
        this.PositionCount = PositionCount;
    }

    public String getUserPermission() {
        return UserPermission;
    }

    public void setUserPermission(String UserPermission) {
        this.UserPermission = UserPermission;
    }
}

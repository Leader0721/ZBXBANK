package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Id;

import java.io.Serializable;


/**
 * @author GISirFive
 * @time 2016/8/8
 */
@Table(name = "Contacts")
public class ContactsEntity implements Serializable {
    /**
     * DepartmentName :
     * PositionName : 部门经理2
     * JoinDay : 9
     * CompanyName : 测试公司4
     * DepartmentId : 00000000-0000-0000-0000-000000000000
     * PositionId : 753ce4a6-58b6-4125-8a45-c414c3ee9a90
     * IsActive : false
     * CompanyId : 45bd4f2e-fa26-432a-9c8e-125d033399b5
     * UserId : d9932d57-8ef7-4355-b761-1d1c094c70dd
     * UserName : 朱聪3
     * HeadImgUrl : null
     * CaptialChar : null
     */

    @Id
    @Expose
    @Column
    private int Id;// Id   定义此字段为了解决默认id要自增且要唯一的问题  解决方案 给此字段加个@Id  顺序@Id id _id @NoAutoIncrement  不自增
    @Expose
    @Column
    private String DepartmentName;
    @Expose
    @Column
    private String PositionName;
    @Expose
    @Column
    private int JoinDay;
    @Expose
    @Column
    private String CompanyName;
    @Expose
    @Column
    private String DepartmentId;
    @Expose
    @Column
    private String PositionId;
    @Expose
    @Column
    private boolean IsActive;
    @Expose
    @Column
    private String CompanyId;
    @Expose
    @Column
    private String UserId;
    @Expose
    @Column
    private String UserName;
    @Expose
    @Column
    private String HeadImgUrl;
    @Expose
    @Column
    private String CaptialChar;

    private boolean isSelected;//自定义 是否选中

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String PositionName) {
        this.PositionName = PositionName;
    }

    public int getJoinDay() {
        return JoinDay;
    }

    public void setJoinDay(int JoinDay) {
        this.JoinDay = JoinDay;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String DepartmentId) {
        this.DepartmentId = DepartmentId;
    }

    public String getPositionId() {
        return PositionId;
    }

    public void setPositionId(String PositionId) {
        this.PositionId = PositionId;
    }

    public boolean isIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean IsActive) {
        this.IsActive = IsActive;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String CompanyId) {
        this.CompanyId = CompanyId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getHeadImgUrl() {
        return HeadImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        HeadImgUrl = headImgUrl;
    }

    public String getCaptialChar() {
        return CaptialChar;
    }

    public void setCaptialChar(String captialChar) {
        CaptialChar = captialChar;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

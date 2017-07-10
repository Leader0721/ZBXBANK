package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

/**
 * @author: ysj
 * @date: 2017-04-16 15:25
 */
public class BankMessageEntity {


    /**
     * UserId : null
     * UserName : null
     * HeadImgUrl : null
     * Titile : null
     * Body : null
     * Id : 0
     * PId : 0
     * CreationDate : 2017-06-13 15:24:34
     * CreationDateStr : 2017-06-13 15:24:34
     * IsRead : true
     * Processed : true
     * CompanyID : null
     * CompanyName : Google
     * MessageType : 7
     * MessageTypeStr : null
     * InviteId : 0
     * BankName : 临淄农村商业银行
     * ContacTel : 15562499177
     * LoanApplyId : 5d334848-6883-4ae3-bbc9-5707e4d97ab8
     * VersionId : e838c7e2-16cd-48ec-85ec-09de1f71668e
     * UploadDate : null
     * StatusId : 3
     * StatusName : 已拒绝
     * MissionId : a705afe9-97f5-403e-ad0a-15fa0cf5d7b9
     */
    @Expose
    private Object UserId;
    @Expose
    private Object UserName;
    @Expose
    private Object HeadImgUrl;
    @Expose
    private Object Titile;
    @Expose
    private Object Body;
    @Expose
    private int Id;
    @Expose
    private int PId;
    @Expose
    private String CreationDate;
    @Expose
    private String CreationDateStr;
    @Expose
    private boolean IsRead;
    @Expose
    private boolean Processed;
    @Expose
    private Object CompanyID;
    @Expose
    private String CompanyName;
    @Expose
    private int MessageType;
    @Expose
    private String MessageTypeStr;
    @Expose
    private int InviteId;
    @Expose
    private String BankName;
    @Expose
    private String ContacTel;
    @Expose
    private String LoanApplyId;
    @Expose
    private String VersionId;
    @Expose
    private String UploadDate;
    @Expose
    private int StatusId;
    @Expose
    private String StatusName;
    @Expose
    private String MissionId;

    public Object getUserId() {
        return UserId;
    }

    public void setUserId(Object UserId) {
        this.UserId = UserId;
    }

    public Object getUserName() {
        return UserName;
    }

    public void setUserName(Object UserName) {
        this.UserName = UserName;
    }

    public Object getHeadImgUrl() {
        return HeadImgUrl;
    }

    public void setHeadImgUrl(Object HeadImgUrl) {
        this.HeadImgUrl = HeadImgUrl;
    }

    public Object getTitile() {
        return Titile;
    }

    public void setTitile(Object Titile) {
        this.Titile = Titile;
    }

    public Object getBody() {
        return Body;
    }

    public void setBody(Object Body) {
        this.Body = Body;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getPId() {
        return PId;
    }

    public void setPId(int PId) {
        this.PId = PId;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String CreationDate) {
        this.CreationDate = CreationDate;
    }

    public String getCreationDateStr() {
        return CreationDateStr;
    }

    public void setCreationDateStr(String CreationDateStr) {
        this.CreationDateStr = CreationDateStr;
    }

    public boolean isIsRead() {
        return IsRead;
    }

    public void setIsRead(boolean IsRead) {
        this.IsRead = IsRead;
    }

    public boolean isProcessed() {
        return Processed;
    }

    public void setProcessed(boolean Processed) {
        this.Processed = Processed;
    }

    public Object getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(Object CompanyID) {
        this.CompanyID = CompanyID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public int getMessageType() {
        return MessageType;
    }

    public void setMessageType(int MessageType) {
        this.MessageType = MessageType;
    }

    public String getMessageTypeStr() {
        return MessageTypeStr;
    }

    public void setMessageTypeStr(String MessageTypeStr) {
        this.MessageTypeStr = MessageTypeStr;
    }

    public int getInviteId() {
        return InviteId;
    }

    public void setInviteId(int InviteId) {
        this.InviteId = InviteId;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getContacTel() {
        return ContacTel;
    }

    public void setContacTel(String ContacTel) {
        this.ContacTel = ContacTel;
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

    public String getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(String UploadDate) {
        this.UploadDate = UploadDate;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int StatusId) {
        this.StatusId = StatusId;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String StatusName) {
        this.StatusName = StatusName;
    }

    public String getMissionId() {
        return MissionId;
    }

    public void setMissionId(String MissionId) {
        this.MissionId = MissionId;
    }
}

package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;


public class ContactsDetailEntity implements Serializable {

    /**
     * UserId : b74598d5-9b8b-4937-9cc6-707b23503de6
     * TokenId : 00000000-0000-0000-0000-000000000000
     * RealName : 1786591Z
     * HeadImgUrl :
     * Address :
     * NickName :
     * BirthDay :
     * Email :
     * Phone : 17865915820
     * BirthDayStr :
     * CaptialChar : ["1"]
     */

    @Expose
    private String UserId;
    @Expose
    private String TokenId;
    @Expose
    private String RealName;
    @Expose
    private String HeadImgUrl;
    @Expose
    private String Address;
    @Expose
    private String NickName;
    @Expose
    private String BirthDay;
    @Expose
    private String Email;
    @Expose
    private String Phone;
    @Expose
    private String BirthDayStr;
    @Expose
    private List<String> CaptialChar;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getTokenId() {
        return TokenId;
    }

    public void setTokenId(String TokenId) {
        this.TokenId = TokenId;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getHeadImgUrl() {
        return HeadImgUrl;
    }

    public void setHeadImgUrl(String HeadImgUrl) {
        this.HeadImgUrl = HeadImgUrl;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String BirthDay) {
        this.BirthDay = BirthDay;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getBirthDayStr() {
        return BirthDayStr;
    }

    public void setBirthDayStr(String BirthDayStr) {
        this.BirthDayStr = BirthDayStr;
    }

    public List<String> getCaptialChar() {
        return CaptialChar;
    }

    public void setCaptialChar(List<String> CaptialChar) {
        this.CaptialChar = CaptialChar;
    }
}

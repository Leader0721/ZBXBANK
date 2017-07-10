package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by U on 2017/3/26.
 */

public class MemberCenterEntity {

    /**
     * UserId : 7fc81f9b-2fb3-4e80-a29e-1dca98377796
     * TokenId :
     * RealName : RealName
     * HeadImgUrl : HeadImgUrl
     * Address :
     * NickName :
     * BirthDay : /Date(-1834819200000)/
     * Email : null
     * Phone : null
     * BirthDayStr : 1911-11-11 00:00:00
     * CaptialChar : []
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
    private List<?> CaptialChar;

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

    public List<?> getCaptialChar() {
        return CaptialChar;
    }

    public void setCaptialChar(List<?> CaptialChar) {
        this.CaptialChar = CaptialChar;
    }
}

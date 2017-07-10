package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * @author GISirFive
 * @time 2016/8/8
 */
@Table(name = "Contacts")
public class ContactsFriendEntity implements Serializable {

    /**
     * UserId : c2c1e556-8f25-4970-ac9b-1e1a575d82ee
     * TokenId : null
     * RealName : RealName
     * HeadImgUrl : HeadImgUrl
     * Address : null
     * NickName : null
     * BirthDay : /Date(-1834732800000)/
     * Email : null
     * Phone : null
     * BirthDayStr : 1911-11-12 00:00:00
     * CaptialCharStr : ["B","M"]
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
    private String CaptialCharStr = "";

    private boolean isSelected;//自定义 是否选中

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTokenId() {
        return TokenId;
    }

    public void setTokenId(String tokenId) {
        TokenId = tokenId;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getHeadImgUrl() {
        return HeadImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        HeadImgUrl = headImgUrl;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getBirthDayStr() {
        return BirthDayStr;
    }

    public void setBirthDayStr(String birthDayStr) {
        BirthDayStr = birthDayStr;
    }

    public String getCaptialCharStr() {
        return CaptialCharStr;
    }

    public void setCaptialCharStr(String captialCharStr) {
        CaptialCharStr = captialCharStr;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

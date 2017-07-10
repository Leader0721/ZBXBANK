package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/26.
 */
@Table(name = "ContactsInviteEntity")
public class ContactsInviteEntity implements Serializable {
    @Expose
    @Column
    private int id;// ID
    @Expose
    @Column
    private String contactname;
    @Expose
    @Column
    private String contactnumber;
    @Expose
    @Column
    private String phonenumber;
    @Expose
    @Column
    private boolean inviteOrNot;
    @Expose
    @Column
    private String CaptialCharStr = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public boolean isInviteOrNot() {
        return inviteOrNot;
    }

    public void setInviteOrNot(boolean inviteOrNot) {
        this.inviteOrNot = inviteOrNot;
    }

    public String getCaptialCharStr() {
        return CaptialCharStr;
    }

    public void setCaptialCharStr(String captialCharStr) {
        CaptialCharStr = captialCharStr;
    }

    @Override
    public String toString() {
        return "ContactsInviteEntity{" +
                "id=" + id +
                ", contactname='" + contactname + '\'' +
                ", contactnumber='" + contactnumber + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", inviteOrNot=" + inviteOrNot +
                ", CaptialCharStr='" + CaptialCharStr + '\'' +
                '}';
    }
}


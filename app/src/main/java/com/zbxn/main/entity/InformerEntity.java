package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2017/3/25.
 */
public class InformerEntity {

    @Expose
    private String UserId;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    @Override
    public String toString() {
        return "InformerEntity{" +
                "UserId='" + UserId + '\'' +
                '}';
    }
}

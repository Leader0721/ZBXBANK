package com.zbxn.main.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by U on 2017/3/16.
 */

public class SmsCodeEntity {

    /**
     * Code :
     * MissionId : cb02f953-976b-4391-abb0-e23e29f3afd6
     */
    @Expose
    private String Code;
    @Expose
    private String MissionId;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getMissionId() {
        return MissionId;
    }

    public void setMissionId(String MissionId) {
        this.MissionId = MissionId;
    }
}

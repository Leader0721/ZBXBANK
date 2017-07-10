package com.zbxn.main.activity;


import android.content.Context;
import android.content.Intent;

import com.zbxn.main.activity.credit.CreditProgressActivity;
import com.zbxn.main.activity.dynamic.CompanyRequestsActivity;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.activity.steward.CustomInfoActivity;
import com.zbxn.main.activity.steward.FinaExcelActivity;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * @author ZBX
 * @time 2017/3/7
 */

public class JpushController {
    /**
     * 处理推送
     */
    public static void doJump(Context context, String extra, ICustomListener listener) {
        String ModuleId = "";
        String Type = "";
        String Body = "";
        try {
            JSONObject json = new JSONObject(extra);
            Iterator<String> it = json.keys();

            while (it.hasNext()) {
                String myKey = it.next().toString();
                if ("ModuleId".equals(myKey)) {
                    ModuleId = json.optString(myKey);
                } else if ("Type".equals(myKey)) {
                    Type = json.optString(myKey);
                } else if ("Body".equals(myKey)) {
                    Body = json.optString(myKey);
                }
            }
        } catch (Exception e) {
            return;
        }
        try {
            JSONObject jsonObject = null;
            Intent intent;
            switch (ModuleId) {
                case "1"://系统 对接：孔明杰
                    if ("1".equals(Type)) { //好友邀请 xx邀请您加为好友
                        /*intent = new Intent(context, MainActivity.class);
                        intent.putExtra("userID", jsonObject.optString("UserId"));
                        intent.putExtra("id", jsonObject.optString("MsgId"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);*/
                        listener.onCustomListener(0, 0, 0);
                    }
                    break;
                case "2"://企业 对接：朱聪
                    jsonObject = new JSONObject(Body);
                    if ("5".equals(Type)) { //企业邀请 xx邀请您加入xx公司
                        intent = new Intent(context, CompanyRequestsActivity.class);
                        intent.putExtra("id", jsonObject.optString("Id"));
                        intent.putExtra("body", jsonObject.optString("Body"));
                        intent.putExtra("InviteId", jsonObject.optString("InviteId"));
                        context.startActivity(intent);
                    }
                    break;
                case "3"://银行 对接：孟凡伟、卢鑫
                    jsonObject = new JSONObject(Body);
                    if ("5".equals(Type)) { //新的单款申请消息提示
                        intent = new Intent(context, CustomInfoActivity.class);
                        intent.putExtra("LoanApplyId", jsonObject.optString("LoanApplyId"));
                        intent.putExtra("VersionId", jsonObject.optString("VersionId"));
                        intent.putExtra("Phone", jsonObject.optString("ContacTel"));
                        context.startActivity(intent);
                    } else if ("6".equals(Type)) { //报表收取提醒
                        intent = new Intent(context, FinaExcelActivity.class);
                        intent.putExtra("bank", true);
                        intent.putExtra("LoanApplyId", jsonObject.optString("LoanApplyId"));
                        intent.putExtra("Phone", jsonObject.optString("ContacTel"));
                        context.startActivity(intent);
                    } else if ("7".equals(Type)) {//贷款申请批复
                        intent = new Intent(context, CreditProgressActivity.class);
                        intent.putExtra("From", "2");
                        intent.putExtra("LoanApplyId", jsonObject.optString("LoanApplyId"));
                        intent.putExtra("VersionId", jsonObject.optString("VersionId"));
                        intent.putExtra("StatusCode", jsonObject.optString("StatusId"));
                        context.startActivity(intent);
                    } else if ("8".equals(Type)) {//提交报表提醒
                        intent = new Intent(context, FinaExcelActivity.class);
                        intent.putExtra("bank", false);
                        intent.putExtra("LoanApplyId", jsonObject.optString("LoanApplyId"));
                        intent.putExtra("Phone", jsonObject.optString("ContacTel"));
                        context.startActivity(intent);
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("推送处理错误：" + e.toString());
        }
    }
}

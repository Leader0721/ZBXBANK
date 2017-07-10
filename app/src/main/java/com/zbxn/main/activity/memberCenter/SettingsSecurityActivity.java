package com.zbxn.main.activity.memberCenter;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.pub.dialog.InputInfoDialog;
import com.pub.dialog.InputInfoListener;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.zbxn.R;
import com.zbxn.main.activity.login.ResetPasswordVerifyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class SettingsSecurityActivity extends BaseActivity {

    @BindView(R.id.layout_mAlterPsw)
    LinearLayout layoutMAlterPsw;
    @BindView(R.id.mPhonenumber)
    TextView mPhonenumber;
    @BindView(R.id.layout_mAlterPhoneNumber)
    LinearLayout layoutMAlterPhoneNumber;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_Email)
    TextView tvEmail;
    @BindView(R.id.layout_mAlterEmail)
    LinearLayout layoutMAlterEmail;
    @BindView(R.id.activity_settings_security)
    LinearLayout activitySettingsSecurity;
    private InputInfoDialog mDialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_security);
        ButterKnife.bind(this);
    }

    @Override
    public void initRight() {
        super.initRight();
        setTitle("安全设置");
    }

    @OnClick({R.id.layout_mAlterPsw, R.id.layout_mAlterPhoneNumber, R.id.activity_settings_security})
    public void onClick(View view) {
        switch (view.getId()) {
            //登录密码
            case R.id.layout_mAlterPsw:
                startActivity(new Intent(this, SettingChangePSWActivity.class));
                break;
            //手机号码
            case R.id.layout_mAlterPhoneNumber:
                mDialog = new InputInfoDialog(this, new InputInfoListener() {
                    @Override
                    public void onDialogOk(Dialog dlg, Object param) {

                        String content = param.toString();
                        CheckUserPws(content, 1);
                    }

                    @Override
                    public void onDialogCancel(Dialog dlg, Object param) {
                        mDialog.dismiss();
                    }
                }, "请输入密码", "");
                mDialog.show();
                break;
            //邮箱
            case R.id.activity_settings_security:
                mDialog = new InputInfoDialog(this, new InputInfoListener() {
                    @Override
                    public void onDialogOk(Dialog dlg, Object param) {
                        String content = param.toString();
                        CheckUserPws(content, 2);
                    }

                    @Override
                    public void onDialogCancel(Dialog dlg, Object param) {
                        mDialog.dismiss();
                    }
                }, "请输入密码", "");
                mDialog.show();
                break;
        }
    }


    /**
     * 验证密码
     */
    public void CheckUserPws(String psw, final int type) {
        //请求网络
        Call call = HttpRequest.getIResource().CheckUserPws(psw);
        callRequest(call, new HttpCallBack(String.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//1成功

                    String data = (String) mResult.getData();
                    if (!"false".equals(data)) {
                        if (type == 1) {
                            intent = new Intent(SettingsSecurityActivity.this, ResetPasswordVerifyActivity.class);
                            intent.putExtra("title", "手机号更改");
                        } else if (type == 2) {
                            intent = new Intent(SettingsSecurityActivity.this, SettingAlterEmailActivity.class);
                        }
                        startActivity(intent);
                    } else {
                        MyToast.showToast("输入密码错误");
                    }
                    mDialog.dismiss();
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }
}

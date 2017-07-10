package com.zbxn.main.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.utils.ValidationUtil;
import com.zbxn.R;
import com.zbxn.main.entity.AlterPhoneNumEntity;
import com.zbxn.main.entity.SmsCodeEntity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ResetPasswordVerifyActivity extends BaseActivity {


    @BindView(R.id.myUserName)
    TextView myUserName;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etDeleteNum)
    ImageView etDeleteNum;
    @BindView(R.id.etVerificationCode)
    EditText etVerificationCode;
    @BindView(R.id.deletePassword)
    ImageView deletePassword;
    @BindView(R.id.btGainCode)
    TextView btGainCode;
    @BindView(R.id.btNext)
    TextView btNext;
    @BindView(R.id.mContentLayout)
    CoordinatorLayout mContentLayout;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.num_line)
    View numLine;
    @BindView(R.id.psw_line)
    View pswLine;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int mi = 60;
    private Timer switchTimer;
    boolean isPhonenumber;
    boolean isAlready;
    boolean isAlter = false;
    private String missionId = "";
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_reset_password_verify);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String title = getIntent().getStringExtra("title");
        if (!StringUtils.isEmpty(title) && "手机号更改".equals(title)) {
            isAlter = true;
            tvTitle.setText("手机号更改");
        } else {
            isAlter = false;
            tvTitle.setText("找回密码");
        }
        hideToolbar();
        btNext.setEnabled(false);
        //删除按钮的操作
        etVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                deletePassword.setVisibility(isEmpty ? View.INVISIBLE : View.VISIBLE);
                if (!StringUtils.isEmpty(etVerificationCode)) {
                    isAlready = true;
                } else {
                    isAlready = false;
                }
                deletePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etVerificationCode.setText("");
                    }
                });
                if (isAlready && isPhonenumber) {
                    btNext.setEnabled(true);
                } else {
                    btNext.setEnabled(false);
                }
            }
        });
        etVerificationCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pswLine.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    pswLine.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });
        etPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    numLine.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    numLine.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                etDeleteNum.setVisibility(isEmpty ? View.INVISIBLE : View.VISIBLE);
                etDeleteNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etPhoneNumber.setText("");
                    }
                });
                String mmobile = StringUtils.getEditText(etPhoneNumber);//手机号
                if (!StringUtils.isEmpty(mmobile)) {
                    isPhonenumber = true;
                } else {
                    isPhonenumber = false;
                }
                if (isAlready && isPhonenumber) {
                    btNext.setEnabled(true);
                } else {
                    btNext.setEnabled(false);
                }
            }
        });

//        isPassWordVis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isVisible1 == 1) {
//                    etVerificationCode.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    isPassWordVis.setImageResource(R.mipmap.temp143);
//                    isVisible1 = 2;
//                } else {
//                    etVerificationCode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    isPassWordVis.setImageResource(R.mipmap.temp142);
//                    isVisible1 = 1;
//                }
//            }
//        });
    }


    @OnClick({R.id.btGainCode, R.id.btNext, R.id.tv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            //获取验证码
            case R.id.btGainCode:
                if (validate()) {
                    send();
                    if (!isAlter){
                        RuleTimeDataList(StringUtils.getEditText(etPhoneNumber) + "", "China");
                    } else {
                        GetChangePhoneSmsCode(StringUtils.getEditText(etPhoneNumber) + "", "China");
                    }

                } else {
                    btGainCode.setEnabled(true);
                }
                break;
            //下一步
            case R.id.btNext:
                if (validate() && validate1()) {
                    if (!isAlter) {
                        //登出修改密码
                        Intent intent = new Intent(ResetPasswordVerifyActivity.this, ResetNewPswActivity.class);
                        intent.putExtra("missionId", missionId);
                        intent.putExtra("Code", StringUtils.getEditText(etVerificationCode));
                        startActivity(intent);
                        finish();
                    } else {
                        //修改手机号
                        ChangePhone();
                        finish();
                    }
                }
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    /**
     * 倒计时
     */
    private void send() {
        btGainCode.setEnabled(false);
        mi = 60;
        if (switchTimer != null) {
            switchTimer.cancel();
            switchTimer = null;
        }
        switchTimer = new Timer();
        switchTimer.schedule(new TimerTask() {
            public void run() {
                mi -= 1;
                imageSwitcherHandler.sendEmptyMessage(0);
            }
        }, 1000, 1000);
        btGainCode.setText("(" + mi + ")重新发送");
        btGainCode.setTextColor(getResources().getColor(R.color.cpb_blue));
        btGainCode.setClickable(false);
    }

    //验证是手机号码否为空
    private boolean validate() {
        if (StringUtils.isEmpty(etPhoneNumber)) {
            MyToast.showToast("请输入手机号码");
            return false;
        }
        String mmobile = StringUtils.getEditText(etPhoneNumber);//手机号
        if (!ValidationUtil.isMobile(mmobile)) {//验证手机号的格式
            isPhonenumber = false;
            MyToast.showToast("你输入的手机号格式不对");
            return false;
        } else {
            isPhonenumber = true;
        }
        return true;
    }

    //验证是验证码否为空
    private boolean validate1() {
        if (StringUtils.isEmpty(etVerificationCode)) {
            MyToast.showToast("验证码不能为空");
            return false;
        } else if (StringUtils.getEditText(etVerificationCode).length() != 6) {
            MyToast.showToast("请确认是否输入六位验证码");
            return false;
        }
        return true;
    }

    /**
     * 发送修改密码短信
     */
    public void RuleTimeDataList(String phoneNumber, String State) {
        Call call = HttpRequest.getIResource().GetSmsCode(phoneNumber, State, true);
        callRequest(call, new HttpCallBack(SmsCodeEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//1成功
                    SmsCodeEntity entity = (SmsCodeEntity) mResult.getData();
                    missionId = entity.getMissionId();
                    MyToast.showToast("验证码已发送到您的手机请注意查收！");
                    //                    TODO 发布的时候一定要去掉！！！！！！！！！！！！！
//                    Toast.makeText(ResetPasswordVerifyActivity.this,mResult.getMsg(),Toast.LENGTH_LONG).show();
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast("获取网络数据失败");
            }
        });
    }
    /**
     * 发送修改手机号短信
     */
    public void GetChangePhoneSmsCode(String phoneNumber, String State) {
        Call call = HttpRequest.getIResource().GetChangePhoneSmsCode(phoneNumber, State);
        callRequest(call, new HttpCallBack(SmsCodeEntity.class, this, true) {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//1成功
                    SmsCodeEntity entity = (SmsCodeEntity) mResult.getData();
                    missionId = entity.getMissionId();
                    MyToast.showToast("验证码已发送到您的手机请注意查收！");
                    //                    TODO 发布的时候一定要去掉！！！！！！！！！！！！！
//                    Toast.makeText(ResetPasswordVerifyActivity.this,mResult.getMsg(),Toast.LENGTH_LONG).show();
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast("获取网络数据失败");
            }
        });
    }

    /**
     * 确认修改手机号
     */
    public void ChangePhone() {
        Call call = HttpRequest.getIResource().ChangePhone(missionId,StringUtils.getEditText(etVerificationCode).trim());
        callRequest(call, new HttpCallBack(AlterPhoneNumEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//0成功
                    MyToast.showToast("更改成功");
                    finish();
                } else {
                    String message = mResult.getMsg();
                    MyToast.showToast(message);
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast("获取网络数据失败");
            }
        });
    }

    private Handler imageSwitcherHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == 0) {
                if (mi <= 0) {
                    btGainCode.setText("重新发送");
                    btGainCode.setTextColor(getResources().getColor(R.color.cpb_blue));
                    btGainCode.setClickable(true);
                    btGainCode.setEnabled(true);
                    if (switchTimer != null) {
                        switchTimer.cancel();
                        switchTimer = null;
                    }
                } else {
                    btGainCode.setText("(" + mi + ")重新发送");
                }
            }
        }
    };
}

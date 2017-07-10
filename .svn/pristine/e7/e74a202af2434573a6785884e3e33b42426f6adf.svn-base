package com.zbxn.main.activity.memberCenter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.utils.ValidationUtil;
import com.zbxn.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingAlterEmailActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etDeleteNum)
    ImageView etDeleteNum;
    @BindView(R.id.num_line)
    View numLine;
    @BindView(R.id.etVerificationCode)
    EditText etVerificationCode;
    @BindView(R.id.deletePassword)
    ImageView deletePassword;
    @BindView(R.id.btGainCode)
    TextView btGainCode;
    @BindView(R.id.psw_line)
    View pswLine;
    @BindView(R.id.btNext)
    TextView btNext;
    @BindView(R.id.activity_setting_alter_email)
    CoordinatorLayout activitySettingAlterEmail;
    private int mi = 60;
    private Timer switchTimer;
    boolean isEmail;
    boolean isAlready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_alter_email);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        hideToolbar();
        btNext.setEnabled(false);
        //验证码输入框
        etVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("WrongConstant")
            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                deletePassword.setVisibility(isEmpty ? View.INVISIBLE : View.VISIBLE);
                if (!isEmpty && StringUtils.getEditText(etVerificationCode).length() == 6) {
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
                if (isAlready && isEmail) {
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
                String mEmail = StringUtils.getEditText(etPhoneNumber);//eMail
                if (!isEmpty && ValidationUtil.isMailbox(mEmail.trim())) {
                    isEmail = true;
                } else {
                    isEmail = false;
                }
                if (isAlready && isEmail) {
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
                btGainCode.setEnabled(false);
                if (validate()) {
                    send();
                } else {
                    btGainCode.setEnabled(true);
                }
                break;
            //完成
            case R.id.btNext:
                finish();
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
        btGainCode.setTextColor(0xffffffff);
        btGainCode.setClickable(false);
        MyToast.showToast("验证码已发送到您的手机请注意查收！");
    }

    //验证是手机号码否为空
    private boolean validate() {
        if (StringUtils.isEmpty(etPhoneNumber)) {
            MyToast.showToast("请输入手机号码");
            return false;
        }
        return true;
    }

    //验证是验证码否为空
    private boolean validate2() {
        if (StringUtils.isEmpty(etVerificationCode)) {
            MyToast.showToast("验证码不能为空");
            return false;
        } else if (StringUtils.getEditText(etVerificationCode).length() != 6) {
            MyToast.showToast("请确认是否输入六位验证码");
        }
        return true;
    }

    //验证是否是手机号码
    private boolean validate1() {
        String mEmail = StringUtils.getEditText(etPhoneNumber);//手机号
        if (ValidationUtil.isMobile(mEmail.trim())) {//验证手机号的格式
            isEmail = true;
            return true;
        } else {
            MyToast.showToast("你输入的邮箱格式不对");
            isEmail = false;
            return false;

        }

    }


    /**
     * 发送邮件
     */
    public void RuleTimeDataList(Context context, String phoneNumber) {
//        String ssid = PreferencesUtils.getString(BaseApp.getContext(), "ssid");
//        //请求网络
//        Call call = HttpRequest.getIResourceOA().GetFindPassword(ssid, phoneNumber);
//        callRequest(call, new HttpCallBack(ResetPasswordEntity.class, context, true) {
//
//
//            @Override
//            public void onSuccess(ResultData mResult) {
//                if ("0".equals(mResult.getSuccess())) {//0成功
//                    List<ResetPasswordEntity> list = mResult.getDataList();
//                } else {
//                    String message = mResult.getMsg();
//                    MyToast.showToast(message);
//                }
//            }
//
//            @Override
//            public void onFailure(String string) {
//                MyToast.showToast("获取网络数据失败");
//            }
//        });
    }

    private Handler imageSwitcherHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == 0) {
                if (mi <= 0) {
                    btGainCode.setText("重新发送");
                    btGainCode.setTextColor(0xffffffff);
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

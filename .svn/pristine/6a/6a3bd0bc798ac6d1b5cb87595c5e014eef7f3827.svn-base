package com.zbxn.main.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
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
import com.pub.utils.KEY;
import com.pub.utils.MyToast;
import com.pub.utils.PreferencesUtils;
import com.pub.utils.StringUtils;
import com.pub.utils.ValidationUtil;
import com.umeng.analytics.MobclickAgent;
import com.zbxn.R;
import com.zbxn.main.activity.MainActivity;
import com.zbxn.main.entity.SmsCodeEntity;
import com.zbxn.main.entity.UserEntity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/3/6.
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.et_phoneNum)
    EditText etPhoneNum;
    @BindView(R.id.deletePhoneNum)
    ImageView deletePhoneNum;
    @BindView(R.id.v_phoneNumline)
    View vPhoneNumline;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.deleteNumber)
    ImageView deleteNumber;
    @BindView(R.id.tv_getNum)
    TextView tvGetNum;
    @BindView(R.id.v_numberline)
    View vNumberline;
    @BindView(R.id.tv_register)
    TextView tvregister;
    @BindView(R.id.tt_servicepaper)
    TextView ttServicepaper;
    @BindView(R.id.mContentLayout)
    CoordinatorLayout mContentLayout;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.et_Name)
    EditText etName;
    @BindView(R.id.deleteName)
    ImageView deleteName;
    @BindView(R.id.v_Nameline)
    View vNameline;
    @BindView(R.id.et_Password)
    EditText etPassword;
    @BindView(R.id.isPassWordVis)
    ImageView isPassWordVis;
    @BindView(R.id.deletePassword)
    ImageView deletePassword;
    @BindView(R.id.v_Passwordline)
    View vPasswordline;
    private boolean isPhoneNum;/*手机号*/
    private boolean isNumber;/*验证码*/
    private int mScreenHeight = 0;
    private int mKeyHeight = 0;
    private int mi = 60;
    private Timer switchTimer;
    private String missionId = "";
    private boolean isPSW;
    private int isVisible = 2;
    private boolean isName;
    private View decorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newregister);
//        绑定控件
        ButterKnife.bind(this);
//        //设置标题
        setTitle("注册");
        tvregister.setEnabled(false);
        hideToolbar();
        //获取屏幕高度  
        mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();

        //阀值设置为屏幕高度的1/3  
        mKeyHeight = mScreenHeight / 3;
        initView();

    }

    private void initView() {
        setEditTextInhibitInputSpace(etName);

        //设置是否显示
        etPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                deletePhoneNum.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deletePhoneNum.setVisibility(View.VISIBLE);
            }

            @SuppressLint("WrongConstant")
            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                deletePhoneNum.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
                if (ValidationUtil.isMobile(StringUtils.getEditText(etPhoneNum))) {
                    isPhoneNum = true;
                } else {
                    isPhoneNum = false;
                }
                if (isPhoneNum && isNumber && isName && isPSW) {
                    tvregister.setEnabled(true);
                } else {
                    tvregister.setEnabled(false);
                }

            }
        });

        etPhoneNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    vPhoneNumline.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    vPhoneNumline.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });
        etNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    vNumberline.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    vNumberline.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });

        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                deleteNumber.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
                if (s.length() == 6) {
                    isNumber = true;
                } else {
                    isNumber = false;
                }
                if (StringUtils.getEditText(etPhoneNum).length() == 11 && StringUtils.getEditText(etNumber).length() >= 6) {
                    tvregister.setEnabled(true);
                } else {
                    tvregister.setEnabled(false);
                }
            }
        });

        isPassWordVis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible == 1) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isPassWordVis.setImageResource(R.mipmap.temp143);
                    isVisible = 2;
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPassWordVis.setImageResource(R.mipmap.temp142);
                    isVisible = 1;
                }
                etPassword.setSelection(etPassword.getText().toString().length());

            }
        });
        //用户名
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                deleteName.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deleteName.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                deleteName.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
                if (!StringUtils.isEmpty(StringUtils.getEditText(etName))) {
                    isName = true;
                } else {
                    isName = false;
                }
                if (isName && isPSW && StringUtils.getEditText(etPhoneNum).length() == 11 && StringUtils.getEditText(etNumber).length() >= 6) {
                    tvregister.setEnabled(true);
                } else {
                    tvregister.setEnabled(false);
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    vPasswordline.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    vPasswordline.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    vNameline.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    vNameline.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isPassWordVis.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPassWordVis.setVisibility(View.GONE);
            }


            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isEmpty(s.toString())) {
                    isPassWordVis.setVisibility(View.GONE);
                } else {
                    isPassWordVis.setVisibility(View.VISIBLE);
                }

                boolean isEmpty = (s == null || s.length() == 0);
                deletePassword.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
                if (StringUtils.getEditText(etPassword).length() >= 8 && StringUtils.getEditText(etPassword).length() <= 20) {
                    isPSW = true;
                } else {
                    isPSW = false;
                }
                if (isName && isPSW && StringUtils.getEditText(etPhoneNum).length() == 11 && StringUtils.getEditText(etNumber).length() >= 6) {
                    tvregister.setEnabled(true);
                } else {
                    tvregister.setEnabled(false);
                }
            }
        });

//        //window中最顶层view
//        decorView = getWindow().getDecorView();
//        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect r = new Rect();
//                //获取可视区域大小，本身并没有返回值，通过outRect更新结果
//                decorView.getWindowVisibleDisplayFrame(r);
//                //屏幕高度
//                int screenHeight = decorView.getRootView().getHeight();
//                int softInputHeight = screenHeight - r.bottom;
//                if (softInputHeight != 0 && softInputHeight > 200) {
//                    llLogin.setVisibility(View.GONE);
//                    tvCancel.setVisibility(View.GONE);
//                } else {
//                    llLogin.setVisibility(View.VISIBLE);
//                    tvCancel.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }

    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" "))
                    return "";
                else
                    return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    @OnClick({R.id.deletePhoneNum,
            R.id.deleteNumber,
            R.id.tv_getNum, R.id.tv_register, R.id.tv_cancel,
            R.id.tt_servicepaper, R.id.deleteName, R.id.deletePassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deletePhoneNum:
                etPhoneNum.setText("");
                break;
            case R.id.deleteNumber:
                etNumber.setText("");
                break;
            case R.id.tv_getNum:
                //获取验证码
                if (validate()) {
                    send();
                    getSmsCode(StringUtils.getEditText(etPhoneNum));
                }

                break;
            case R.id.tv_register:
//                注册
                if (validate1()) {
                    GetRegedit(missionId, StringUtils.getEditText(etNumber), StringUtils.getEditText(etPhoneNum), StringUtils.getEditText(etPassword), StringUtils.getEditText(etName));
                }
                break;
            case R.id.tt_servicepaper:
//                Intent intent = new Intent(this, WebViewDetailsActivity.class);
//                String url = "http://www.zbzbx.com/zms_app_intro/ZBX_introduce.aspx";
//                intent.putExtra("url", url);
//                intent.putExtra("title", "五的N次方用户服务协议");
//                startActivity(intent);

//                Intent intent = new Intent(this, WebviewActivity.class);
//                intent.putExtra("url", "file:///android_asset/projectBank/serviceRule.html");
//                startActivity(intent);

                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.deleteName:
                etName.setText("");
                break;
            case R.id.deletePassword:
                etPassword.setText("");
                isPassWordVis.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 提交前验证
     *
     * @return
     */
    private boolean validate() {
        if (!ValidationUtil.isMobile(StringUtils.getEditText(etPhoneNum))) {
            MyToast.showToast("请检查输入的手机号格式是否正确");
            return false;
        }
        if (StringUtils.getEditText(etName).contains(" ")) {
            MyToast.showToast("用户名中不能包含空格");
            return false;
        }
        return true;
    }

    /**
     * 提交前验证
     *
     * @return
     */
    private boolean validate1() {
        if (!ValidationUtil.isMobile(StringUtils.getEditText(etPhoneNum))) {
            MyToast.showToast("请检查输入的手机号格式是否正确");
            return false;
        }
        if (StringUtils.getEditText(etNumber).length() != 6) {
            MyToast.showToast("请检查输入的验证码是否是六位的");
            return false;
        }
        if (StringUtils.getEditText(etPassword).length() < 8 || StringUtils.getEditText(etPassword).length() > 20) {
            MyToast.showToast("请检查密码格式是否正确");
            return false;
        }
        if (!isName) {
            MyToast.showToast("请输入用户名");
            return false;
        }
        return true;
    }

    /**
     * 获取验证码
     */
    public void getSmsCode(String phoneNum) {

        //请求网络
        Call call = HttpRequest.getIResource().GetSmsCode(phoneNum, "China");
        callRequest(call, new HttpCallBack(SmsCodeEntity.class, RegisterActivity.this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//0成功
                    SmsCodeEntity entity = (SmsCodeEntity) mResult.getData();
                    missionId = entity.getMissionId();
                    MyToast.showToast("验证码已发送到您的手机请注意查收！");
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

    /**
     * 注册接口
     */
    public void GetRegedit(String missionId, String smsCode, final String phoneNum, String Psw, String realName) {
        //请求网络
        Call call = HttpRequest.getIResource().GetRegedit("1", phoneNum, Psw, missionId, realName, smsCode);
        callRequest(call, new HttpCallBack(UserEntity.class, RegisterActivity.this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//1成功
                    UserEntity entity = (UserEntity) mResult.getData();

                    PreferencesUtils.putString(RegisterActivity.this, KEY.FLAG_KEY, entity.getKey());
                    PreferencesUtils.putString(RegisterActivity.this, KEY.FLAG_IV, entity.getIv());
                    PreferencesUtils.putString(RegisterActivity.this, KEY.FLAG_TOOKENID, entity.getTokenId());
                    PreferencesUtils.putString(RegisterActivity.this, KEY.FLAG_USERNAME, phoneNum);
                    PreferencesUtils.putString(RegisterActivity.this, KEY.FLAG_PHONE, entity.getPhone() + "");
                    PreferencesUtils.putBoolean(RegisterActivity.this, KEY.FLAG_ISLOGIN, true);

                    //友盟统计 账号的统计
                    MobclickAgent.onProfileSignIn(entity.getPhone());

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                    MyToast.showToast("注册成功");
                    finish();
//                    MyToast.showToast(mResult.getMsg());
                } else {
                    MyToast.showToast("验证码无效，请重新获取");
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }

    private Handler imageSwitcherHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == 0) {
                if (mi <= 0) {
                    tvGetNum.setText("重新发送");
                    tvGetNum.setTextColor(getResources().getColor(R.color.cpb_blue));
                    tvGetNum.setClickable(true);
                    if (switchTimer != null) {
                        switchTimer.cancel();
                        switchTimer = null;
                    }
                } else {
                    tvGetNum.setText("(" + mi + ")重新发送");
                }
            }
        }
    };

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
        tvGetNum.setText("(" + mi + ")重新发送");
        tvGetNum.setTextColor(getResources().getColor(R.color.cpb_blue));
        tvGetNum.setClickable(false);
    }

//    //软键盘的监听
//    @Override
//    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//
//        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > mKeyHeight)) {
//            //弹出高度大于1/3则默认弹出
//            llLogin.setVisibility(View.GONE);
//        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > mKeyHeight)) {
//            //关闭
//            llLogin.setVisibility(View.VISIBLE);
//        }
//
//    }


}

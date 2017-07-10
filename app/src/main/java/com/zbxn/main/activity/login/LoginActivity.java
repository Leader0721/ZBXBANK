package com.zbxn.main.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.KEY;
import com.pub.utils.MyToast;
import com.pub.utils.PreferencesUtils;
import com.pub.utils.StringUtils;
import com.pub.utils.ValidationUtil;
import com.pub.widget.dbutils.DBUtils;
import com.umeng.analytics.MobclickAgent;
import com.zbxn.R;
import com.zbxn.main.activity.MainActivity;
import com.zbxn.main.entity.UserEntity;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import retrofit2.Call;

public class LoginActivity extends BaseActivity {
    public static final String FLAG_INPUT_HEADURL = "headUrl";
    public static final String FLAG_INPUT_ID = "id";
    public static final String FLAG_SSID = "ssid";
    public static final String FLAG_ZMSCOMPANYID = "zmsCompanyId";
    public static final String FLAG_ZMSCOMPANYNAME = "zmsCompanyName";
    public static final String FLAG_INFO_MSG = "info_msg";
    public static final String FLAG_INFO_MSG_COUNT = "info_msg_count";
    public static final String FLAG_PERMISSIONIDS = "permissionIds";
    public static final String FLAG_DEPARMENTNAME = "deparmentName";
    public static final String FLAG_INPUT_LOGINNAME = "loginname";
    public static final String FLAG_INPUT_TELEPHONE = "telephone";
    public static final String FLAG_INPUT_COMPANY = "zmsCompanyName";
    @BindView(R.id.myUserName)
    ImageView myUserName;
    @BindView(R.id.mUserName)
    EditText mUserName;
    @BindView(R.id.deleteNum)
    ImageView deleteNum;
    @BindView(R.id.myPassword)
    ImageView myPassword;
    @BindView(R.id.mPassword)
    EditText mPassword;
    @BindView(R.id.isPassWordVis)
    ImageView isPassWordVis;
    @BindView(R.id.deletePassword)
    ImageView deletePassword;
    @BindView(R.id.mForgetPassword)
    TextView mForgetPassword;
    @BindView(R.id.mRegister)
    TextView mRegister;
    @BindView(R.id.mContentLayout)
    CoordinatorLayout mContentLayout;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.num_line)
    View numLine;
    @BindView(R.id.psw_line)
    View pswLine;
    boolean isUserName;
    boolean isPassWord;

    private int isVisible = 1;


    private int mScreenHeight = 0;
    private int mKeyHeight = 0;
    private View decorView;
    private DBUtils<UserEntity> mDBUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        绑定控件
        ButterKnife.bind(this);

        //获取屏幕高度  
        mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();

        //阀值设置为屏幕高度的1/3  
        mKeyHeight = mScreenHeight / 3;
        init();
        in();
        initDeleteHint();
    }


    private void initDeleteHint() {
        if (!StringUtils.isEmpty(mUserName.getText().toString())) {
            deleteNum.setVisibility(View.VISIBLE);
        }
        isPassWordVis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible == 1) {
                    mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isPassWordVis.setImageResource(R.mipmap.temp143);
                    isVisible = 2;
                } else {
                    mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPassWordVis.setImageResource(R.mipmap.temp142);
                    isVisible = 1;
                }
                mPassword.setSelection(mPassword.getText().toString().length());

            }
        });


        //设置是否显示
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                deleteNum.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deleteNum.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                deleteNum.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
                if (!StringUtils.isEmpty(mUserName)) {
                    isUserName = true;
                } else {
                    isUserName = false;
                }
                if (isUserName && isPassWord) {
                    tvLogin.setEnabled(true);
                } else {
                    tvLogin.setEnabled(false);
                }

            }
        });
        mUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    numLine.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    numLine.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });
        deleteNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserName.setText("");
            }
        });

        deletePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPassword.setText("");
                isPassWordVis.setVisibility(View.INVISIBLE);
            }
        });
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isPassWordVis.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPassWordVis.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

                boolean isEmpty = (s == null || s.length() == 0);
                deletePassword.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
                if (StringUtils.isEmpty(mPassword)) {
                    isPassWordVis.setVisibility(View.GONE);
                } else {
                    isPassWordVis.setVisibility(View.VISIBLE);
                }
                if (!StringUtils.isEmpty(mPassword)) {
                    isPassWord = true;
                } else {
                    isPassWord = false;
                }
                if (isUserName && isPassWord) {
                    tvLogin.setEnabled(true);
                } else {
                    tvLogin.setEnabled(false);
                }
            }
        });

        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pswLine.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    pswLine.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });

    }

    private void in() {
        String username = PreferencesUtils.getString(this, KEY.FLAG_USERNAME);
        if (!StringUtils.isEmpty(username)) {
            mUserName.setText(username);
            deleteNum.setVisibility(View.VISIBLE);
        } else {
            mUserName.setText("");
            deleteNum.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(mPassword)) {
            mPassword.setText(StringUtils.getEditText(mPassword));
            isPassWordVis.setVisibility(View.VISIBLE);
            deletePassword.setVisibility(View.VISIBLE);
        } else {
            mPassword.setText("");
            isPassWordVis.setVisibility(View.GONE);
            deletePassword.setVisibility(View.GONE);
        }
        if (StringUtils.getEditText(mPassword).length() >= 8 && StringUtils.getEditText(mPassword).length() <= 20) {
            isPassWord = true;
        } else {
            isPassWord = false;
        }

        if (StringUtils.getEditText(mUserName).length() >= 8) {
            isUserName = true;
        } else {
            isUserName = false;
        }
    }

    @OnClick({R.id.tv_login, R.id.mUserName, R.id.mPassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                if (validate()) {
                    login(this, StringUtils.getEditText(mUserName), StringUtils.getEditText(mPassword), true);
                }
                break;
        }
    }

    //提交前验证
    private boolean validate() {
        if (StringUtils.isEmpty(mUserName)) {
            MyToast.showToast("用户名不能为空");
            return false;
        }
        if (StringUtils.isEmpty(mPassword)) {
            MyToast.showToast("密码不能为空");
            return false;
        }
//        if (!ValidationUtil.isNumeric(StringUtils.getEditText(mUserName))) {
//            MyToast.showToast("账号输入的格式有误");
//            return false;
//        }
        if (!ValidationUtil.isABCNumber(StringUtils.getEditText(mPassword))) {
            MyToast.showToast("密码输入的格式有误");
            return false;
        }
        return true;
    }

    private void init() {
        //退出登录操作
        BaseApp.logOut(false);

        tvLogin.setEnabled(false);
        if (mDBUtils == null) {
            mDBUtils = new DBUtils<>(UserEntity.class);
        }
        hideToolbar();
        mUserName.setText("");
        mPassword.setText("");
        isPassWordVis.setVisibility(View.INVISIBLE);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));//跳转注册界面
            }
        });
        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordVerifyActivity.class));//跳转找回密码界面
            }
        });

    }

    public void finishForResult(boolean b) {
        if (b) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    /**
     * 登录
     */
    public void login(Context context, final String userName, final String password, boolean isShowProgress) {
        //请求网络
        Call call = HttpRequest.getIResource().GetLogin("1", userName, password);
        callRequest(call, new HttpCallBack(UserEntity.class, this, isShowProgress) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//0成功
                    UserEntity entity = (UserEntity) mResult.getData();

                    //保存初始化信息
                    PreferencesUtils.putString(LoginActivity.this, KEY.FLAG_KEY, entity.getKey());
                    PreferencesUtils.putString(LoginActivity.this, KEY.FLAG_IV, entity.getIv());
                    PreferencesUtils.putString(LoginActivity.this, KEY.FLAG_TOOKENID, entity.getTokenId());
                    PreferencesUtils.putString(LoginActivity.this, KEY.FLAG_USERNAME, userName);
                    PreferencesUtils.putString(LoginActivity.this, KEY.FLAG_PHONE, entity.getPhone());
                    PreferencesUtils.putBoolean(LoginActivity.this, KEY.FLAG_ISLOGIN, true);

                    //友盟统计 账号的统计
                    MobclickAgent.onProfileSignIn(entity.getPhone());

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    finish();
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
     * ************* 设置别名 *************************
     */
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    System.out.println("Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(LoginActivity.this,
                            (String) msg.obj, null, mAliasCallback);
                    break;
                default:
                    System.out.println("Unhandled msg - " + msg.what);
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    System.out.println("alia退出:" + alias + "|");
                    System.out.println(logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。

                    // 保存是否设置别名状态
                    PreferencesUtils.putBoolean(LoginActivity.this, KEY.ISSETALIAS, false);

                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    System.out.println(logs);

                    // 保存是否设置别名状态
                    // 保存是否设置别名状态
                    PreferencesUtils.putBoolean(LoginActivity.this, KEY.ISSETALIAS, false);

                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(
                            mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    System.out.println(logs);
            }
        }
    };
}

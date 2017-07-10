package com.zbxn.main.activity.memberCenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.zbxn.R;
import com.zbxn.main.entity.PersonalMessageEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class SettingChangePSWActivity extends BaseActivity {

    @BindView(R.id.et_PSW1)
    EditText etPSW1;
    @BindView(R.id.iv_deletepsw1)
    ImageView ivDeletepsw1;
    @BindView(R.id.psw_line1)
    View pswLine1;
    @BindView(R.id.et_PSW2)
    EditText etPSW2;
    @BindView(R.id.iv_deletepsw2)
    ImageView ivDeletepsw2;
    @BindView(R.id.psw_line2)
    View pswLine2;
    @BindView(R.id.et_PSW3)
    EditText etPSW3;
    @BindView(R.id.iv_deletepsw3)
    ImageView ivDeletepsw3;
    @BindView(R.id.psw_line3)
    View pswLine3;
    @BindView(R.id.btNext)
    TextView btNext;
    @BindView(R.id.activity_setting_change_psw)
    LinearLayout activitySettingChangePsw;
    private String mPSW1;
    private String mPSW2;
    private String mPSW3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_change_psw);
        ButterKnife.bind(this);
        setTitle("重置密码");
        initView();
        //完成
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPSW1 = StringUtils.getEditText(etPSW1);
                mPSW2 = StringUtils.getEditText(etPSW2);
                mPSW3 = StringUtils.getEditText(etPSW3);
                if (validate()) {
                    GetMessageListByUserPhone();
                }
            }
        });
    }

    /**
     * 提交前验证
     *
     * @return
     */
    private boolean validate() {
        if (StringUtils.isEmpty(mPSW1)) {
            MyToast.showToast("请输入原密码");
            return false;
        }
        if (mPSW1.length() < 8 || mPSW1.length() > 20) {
            MyToast.showToast("请确认输入的原密码是8-20位");
            return false;
        }
        if (StringUtils.isEmpty(mPSW2)) {
            MyToast.showToast("请输入新密码");
            return false;
        }
        if (mPSW2.length() < 8 || mPSW2.length() > 20) {
            MyToast.showToast("请确认输入的新密码是8-20位");
            return false;
        }
        if (StringUtils.isEmpty(mPSW3)) {
            MyToast.showToast("请再次输入新密码");
            return false;
        }
        if (mPSW3.length() < 8 || mPSW3.length() > 20) {
            MyToast.showToast("请确认再次输入的新密码是8-20位");
            return false;
        }
        if (!mPSW2.equals(mPSW3)) {
            MyToast.showToast("请确认两次输入的新密码一致");
            return false;
        }
        return true;
    }

    private void initView() {
        //原密码

        etPSW1.addTextChangedListener(new TextWatcher() {
            @SuppressLint("WrongConstant")
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ivDeletepsw1.setVisibility(View.VISIBLE);
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivDeletepsw1.setVisibility(View.VISIBLE);
            }

            @SuppressLint("WrongConstant")
            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                ivDeletepsw1.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            }
        });
        ivDeletepsw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPSW1.setText("");
            }
        });
        etPSW1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pswLine1.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    pswLine1.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });
        //新密码
        etPSW2.addTextChangedListener(new TextWatcher() {
            @SuppressLint("WrongConstant")
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ivDeletepsw1.setVisibility(View.VISIBLE);
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivDeletepsw1.setVisibility(View.VISIBLE);
            }

            @SuppressLint("WrongConstant")
            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                ivDeletepsw1.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            }
        });
        ivDeletepsw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPSW1.setText("");
            }
        });
        etPSW2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pswLine2.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    pswLine2.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });
        //再次输入新密码
        etPSW3.addTextChangedListener(new TextWatcher() {
            @SuppressLint("WrongConstant")
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ivDeletepsw1.setVisibility(View.VISIBLE);
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivDeletepsw1.setVisibility(View.VISIBLE);
            }

            @SuppressLint("WrongConstant")
            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                ivDeletepsw1.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            }
        });
        ivDeletepsw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPSW1.setText("");
            }
        });
        etPSW3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pswLine3.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    pswLine3.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });
    }

    //修改密码
    private void GetMessageListByUserPhone() {
        Call call = HttpRequest.getIResource().ChangePws(mPSW1, mPSW2);
        callRequest(call, new HttpCallBack(PersonalMessageEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    MyToast.showToast(mResult.getMsg());
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

}

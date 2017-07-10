package com.zbxn.main.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
import com.zbxn.main.entity.ResetPasswordEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ResetNewPswActivity extends BaseActivity {


    @BindView(R.id.etPsw1)
    EditText etPsw1;
    @BindView(R.id.isPassWordVis1)
    ImageView isPassWordVis1;
    @BindView(R.id.etDeletePsw1)
    ImageView etDeletePsw1;
    @BindView(R.id.btSure)
    TextView btSure;
    @BindView(R.id.activity_reset_new_psw)
    LinearLayout activityResetNewPsw;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.num_line)
    View numLine;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int isVisible1 = 1;
    private String mPhoneNumber;
    private String mPsw1;
    private String newPsw;
    private String missionId;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_reset_psw);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        hideToolbar();
        missionId = getIntent().getStringExtra("missionId");
        code = getIntent().getStringExtra("Code");
        isPassWordVis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible1 == 1) {
                    etPsw1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isPassWordVis1.setImageResource(R.mipmap.temp143);
                    isVisible1 = 2;
                } else {
                    etPsw1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPassWordVis1.setImageResource(R.mipmap.temp142);
                    isVisible1 = 1;
                }
                etPsw1.setSelection(StringUtils.getEditText(etPsw1).length());
            }
        });
        isPassWordVis1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    numLine.setBackgroundColor(getResources().getColor(R.color.cpb_blue));
                } else {
                    numLine.setBackgroundColor(getResources().getColor(R.color.pub_line_color));
                }
            }
        });

        mPhoneNumber = getIntent().getStringExtra("mPhoneNumber");
        etPsw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                etDeletePsw1.setVisibility(isEmpty ? View.INVISIBLE : View.VISIBLE);
                etDeletePsw1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etPsw1.setText("");
                    }
                });
                newPsw = StringUtils.getEditText(etPsw1);
                if (newPsw.length()>0){
                    btSure.setEnabled(true);
                }else {
                    btSure.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.btSure, R.id.tv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSure:
                if (validate()) {
                   //提交接口
                    ResetUserPws();
                }
                break;
            case R.id.tv_back:
                finish();
                break;
        }


    }

    //验证是否是在8-20为之间
    private boolean validate() {
        if (StringUtils.isEmpty(etPsw1)){
            MyToast.showToast("输入密码为空");
            return false;
        }
        if (newPsw.length()<8||newPsw.length()>20){
            MyToast.showToast("请确定输入密码在8-20位之间");
            return false;
        }
        return true;
    }


    public void ResetUserPws() {
        //请求网络
        Call call = HttpRequest.getIResource().ResetUserPws(code, missionId,StringUtils.getEditText(etPsw1));
        callRequest(call, new HttpCallBack(ResetPasswordEntity.class, this, true) {


            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//1成功
                    MyToast.showToast("修改成功");
                    startActivity(new Intent(ResetNewPswActivity.this,LoginActivity.class));
                    finish();

                } else {
                    String message = mResult.getMsg();
                    MyToast.showToast(message);
                    finish();
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast("获取网络数据失败");
            }
        });
    }

}

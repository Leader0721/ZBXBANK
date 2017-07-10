package com.zbxn.main.activity.memberCenter;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.zbxn.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingPrivacyActivity extends BaseActivity {

    @BindView(R.id.layout_all)
    LinearLayout layoutAll;
    @BindView(R.id.layout_none)
    LinearLayout layoutNone;
    @BindView(R.id.layout_verify)
    LinearLayout layoutVerify;
    @BindView(R.id.activity_setting_privacy)
    LinearLayout activitySettingPrivacy;
    @BindView(R.id.rb_all)
    TextView rbAll;
    @BindView(R.id.rb_none)
    TextView rbNone;
    @BindView(R.id.rb_verify)
    TextView rbVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_privacy);
        ButterKnife.bind(this);
        setTitle("隐私设置");
        rbAll.setSelected(true);
        rbNone.setSelected(false);
        rbVerify.setSelected(false);
    }

    @OnClick({R.id.layout_all, R.id.layout_none, R.id.layout_verify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_all:
                rbAll.setSelected(true);
                rbNone.setSelected(false);
                rbVerify.setSelected(false);
                break;
            case R.id.layout_none:
                rbAll.setSelected(false);
                rbNone.setSelected(true);
                rbVerify.setSelected(false);
                break;
            case R.id.layout_verify:
                rbAll.setSelected(false);
                rbNone.setSelected(false);
                rbVerify.setSelected(true);
                break;

        }
    }

}

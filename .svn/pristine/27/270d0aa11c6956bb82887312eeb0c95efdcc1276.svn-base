package com.zbxn.main.activity.memberCenter;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.utils.PreferencesUtils;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.MainActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingLanguageActivity extends BaseActivity {

    @BindView(R.id.layout_mChinese)
    LinearLayout layoutMChinese;
    @BindView(R.id.layout_mThai)
    LinearLayout layoutMThai;
    @BindView(R.id.activity_setting_language)
    LinearLayout activitySettingLanguage;
    @BindView(R.id.tv_Chinese)
    TextView tvChinese;
    @BindView(R.id.tv_Thai)
    TextView tvThai;
    private Configuration config;
    private DisplayMetrics dm;
    private Resources resources;
    public static final String FLAG_SETTING_LANGUAGE = "language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_language);
        ButterKnife.bind(this);
        setTitle("多语言");
        initView();

    }

    private void initView() {
        String language = PreferencesUtils.getString(this, SettingLanguageActivity.FLAG_SETTING_LANGUAGE);
        resources = getResources();
        config = resources.getConfiguration();
        dm = resources.getDisplayMetrics();
        if (!StringUtils.isEmpty(language)) {
            if ("th".equals(language)) {
                tvChinese.setSelected(false);
                tvThai.setSelected(true);
                config.locale = new Locale("th");
            } else {
                tvChinese.setSelected(true);
                tvThai.setSelected(false);
                config.locale = Locale.CHINA;
            }
        } else {
            tvChinese.setSelected(true);
            tvThai.setSelected(false);
            config.locale = Locale.CHINA;
        }

    }

    @OnClick({R.id.layout_mChinese, R.id.layout_mThai})
    public void onClick(View view) {

        switch (view.getId()) {
            //中文
            case R.id.layout_mChinese:
                tvChinese.setSelected(true);
                tvThai.setSelected(false);
                config.locale = Locale.CHINA;
                PreferencesUtils.putString(BaseApp.getContext(),
                        SettingLanguageActivity.FLAG_SETTING_LANGUAGE, "ch");
                break;
            //泰文
            case R.id.layout_mThai:
                tvChinese.setSelected(false);
                tvThai.setSelected(true);
                config.locale = new Locale("th");
                PreferencesUtils.putString(BaseApp.getContext(),
                        SettingLanguageActivity.FLAG_SETTING_LANGUAGE, "th");
                break;
        }
        resources.updateConfiguration(config, dm);
        Intent intent = new Intent(SettingLanguageActivity.this, MainActivity.class);
        intent.putExtra("language", "language");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

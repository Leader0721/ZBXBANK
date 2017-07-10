package com.zbxn.main.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pub.utils.DeviceUtils;
import com.pub.utils.KEY;
import com.pub.utils.PreferencesUtils;
import com.umeng.analytics.MobclickAgent;
import com.zbxn.R;
import com.zbxn.main.activity.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * 启动页
 */
public class LauncherActivity extends Activity {

    /**
     * 启动页停留时间
     */
    private static final int DURATION = 2000;

    @BindView(R.id.mLogo)
    ImageView mLogo;
    @BindView(R.id.mVersion)
    TextView mVersion;
    @BindView(R.id.launch_layout)
    RelativeLayout launchLayout;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);

        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mLogo, "alpha", 0f, 1.0f);
        animator.setDuration(1600);
        animator.start();

        //友盟统计
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.openActivityDurationTrack(false);
        // MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, "58f84a61a3251175d9001db6", "", MobclickAgent.EScenarioType.E_UM_NORMAL));
        MobclickAgent.enableEncrypt(true);//6.0.0版本及以后日志加密
        MobclickAgent.setDebugMode(true);//自己测试的时候调用此方法收集打印日志，正式发版需要删除
        //友盟统计end

        mVersion.setText("版本 " + DeviceUtils.getInstance(this).getAppVersionName());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                next();
            }
        }, DURATION);
    }

    /**
     * 跳转到下一步
     */
    private void next() {
        //是不是第一次安装启动
        boolean isFirst = PreferencesUtils.getBoolean(this, KEY.FLAG_ISFIRST_START, true);
        if (isFirst) {
            PreferencesUtils.putBoolean(LauncherActivity.this, KEY.FLAG_ISFIRST_START, false);
            startActivity(new Intent(this, GuideActivity.class));
            finish();
            return;
        } else {
            if (PreferencesUtils.getBoolean(this, KEY.FLAG_ISLOGIN)) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        }
    }

    //友盟统计
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}

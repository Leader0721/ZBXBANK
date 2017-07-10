package com.zbxn.main.activity.memberCenter;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.pub.base.BaseActivity;
import com.zbxn.R;
import com.zcw.togglebutton.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingMessageActivity extends BaseActivity {

    @BindView(R.id.tb_message)
    ToggleButton tbMessage;
    @BindView(R.id.tb_basic)
    ToggleButton tbBasic;
    @BindView(R.id.tb_update)
    ToggleButton tbUpdate;
    @BindView(R.id.tb_sound)
    ToggleButton tbSound;
    @BindView(R.id.tb_vibration)
    ToggleButton tbVibration;
    @BindView(R.id.activity_setting_message)
    LinearLayout activitySettingMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_message);
        ButterKnife.bind(this);
        setTitle("通知");
        tbMessage.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    //接受官方
                } else {
                    //关闭官方
                }
            }
        });
        tbBasic.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    //接受基本动态推送
                } else {
                    //关闭基本动态推送
                }
            }
        });
        tbUpdate.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    //接受更新动态推送
                } else {
                    //关闭更新动态推送
                }
            }
        });
        tbSound.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    //开启声音
                } else {
                    //关闭声音
                }
            }
        });
        tbVibration.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    //开启震动
                } else {
                    //关闭震动
                }
            }
        });
    }

}

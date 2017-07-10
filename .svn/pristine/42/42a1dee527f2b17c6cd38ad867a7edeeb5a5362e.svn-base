package com.zbxn.main.activity.memberCenter;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.dialog.ZBXAlertDialog;
import com.pub.dialog.ZBXAlertListener;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.FileUtils;
import com.pub.utils.KEY;
import com.pub.utils.MyToast;
import com.pub.utils.PreferencesUtils;
import com.zbxn.R;
import com.zbxn.main.activity.weex.WeexUpdateLogActivity;
import com.zbxn.main.entity.DynamicInviteEntity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class SettingActivity extends BaseActivity {

    /**
     * 检查缓存
     */
    private static final int Flag_Message_LoadCache = 1001;

    /**
     * 清理缓存
     */
    private static final int Flag_Message_ClearCache = 1002;
    @BindView(R.id.layout_mSecurity)
    LinearLayout layoutMSecurity;
    @BindView(R.id.layout_mLanguage)
    LinearLayout layoutMLanguage;
    @BindView(R.id.layout_mMessage)
    LinearLayout layoutMMessage;
    @BindView(R.id.layout_mPrivacy)
    LinearLayout layoutMPrivacy;
    @BindView(R.id.layout_mUsinghelp)
    LinearLayout layoutMUsinghelp;
    @BindView(R.id.layout_mCache)
    LinearLayout layoutMCache;
    @BindView(R.id.layout_mFeedback)
    LinearLayout layoutMFeedback;
    @BindView(R.id.layout_mAbout)
    LinearLayout layoutMAbout;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.activity_settings)
    LinearLayout activitySettings;
    @BindView(R.id.tv_Cache)
    TextView tvCache;

    private ZBXAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        loadCache();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Flag_Message_LoadCache:
                    if (msg.obj == null) {
                        tvCache.setText("0 MB");
                    } else {
                        double cachesize = Double.valueOf(msg.obj + "");
                        String result = String.format("%.2f", cachesize);
                        tvCache.setText(result + " MB");
                    }
                    break;
                case Flag_Message_ClearCache:
                    MyToast.showToast("清理完成");
                    loadCache();
                    break;
                default:
                    break;
            }
        }
    };

    //获得缓存大小
    public void loadCache() {
        new Thread() {
            @Override
            public void run() {
//                File file = Glide.getPhotoCacheDir(SettingActivity.this);
                File file = ImageLoader.getInstance().getDiskCache().getDirectory();
                if (!file.exists()) {
                    return;
                } else {
                    double size = FileUtils.sizeOfDirectory(file);
                    size = size / 1024 / 1024;
                    Message message = Message.obtain();
                    message.what = Flag_Message_LoadCache;
                    message.obj = size;
                    mHandler.sendMessage(message);
                }
            }
        }.start();
    }

    // 清理缓存
    private void clearCache() {
        new Thread() {
            public void run() {
//                Glide.get(SettingActivity.this).clearDiskCache();
                ImageLoader.getInstance().clearDiskCache();
                Message message = Message.obtain();
                message.what = Flag_Message_ClearCache;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    private void showClearCacheDialog() {
        mDialog = new ZBXAlertDialog(this, new ZBXAlertListener() {
            @Override
            public void onDialogOk(Dialog dlg) {
                clearCache();
                mDialog.dismiss();
            }

            @Override
            public void onDialogCancel(Dialog dlg) {
                mDialog.dismiss();
            }
        }, "提示", "确定要清除缓存吗?");
        mDialog.show();
    }


    @Override
    public void initRight() {
        super.initRight();
        setTitle("设置");
    }

    @OnClick({R.id.layout_mSecurity, R.id.layout_mLanguage, R.id.layout_mMessage, R.id.layout_mPrivacy, R.id.layout_mUsinghelp,
            R.id.layout_mCache, R.id.layout_mFeedback, R.id.tv_exit, R.id.layout_mAbout})
    public void onClick(View view) {
        switch (view.getId()) {
            //安全设置
            case R.id.layout_mSecurity:
                Intent intent2 = new Intent(this, SettingsSecurityActivity.class);
//                intent2.putExtra("phoneNum",) 需要传过去
                startActivity(intent2);
                break;
            //多语言
            case R.id.layout_mLanguage:
                startActivity(new Intent(this, SettingLanguageActivity.class));
                break;
            //通知
            case R.id.layout_mMessage:
                startActivity(new Intent(this, SettingMessageActivity.class));
                break;
            //隐私
            case R.id.layout_mPrivacy:
                startActivity(new Intent(this, SettingPrivacyActivity.class));
                break;
            //使用帮助
            case R.id.layout_mUsinghelp:
                Intent intent = new Intent(this, WebViewDetailsActivity.class);
                String url = "http://www.zbzbx.com/zms_app_intro/ZBX_introduce.aspx";
                intent.putExtra("url", url);
                intent.putExtra("title", "使用帮助");
                startActivity(intent);
                break;
            //清理缓存
            case R.id.layout_mCache:
                showClearCacheDialog();
                break;
            //意见反馈
            case R.id.layout_mFeedback:
                startActivity(new Intent(this, SettingFeedbackActivity.class));
                break;
            //关于我们
            case R.id.layout_mAbout:
                startActivity(new Intent(this, WeexUpdateLogActivity.class));
                break;
            //退出登录
            case R.id.tv_exit:
                mDialog = new ZBXAlertDialog(this, new ZBXAlertListener() {
                    @Override
                    public void onDialogOk(Dialog dlg) {
                        LogOut();
                        mDialog.dismiss();
                    }

                    @Override
                    public void onDialogCancel(Dialog dlg) {
                        mDialog.dismiss();
                    }
                }, "提示", "确定要退出登录吗?");
                mDialog.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog!=null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    //退出登录
    private void LogOut() {
        Call call = HttpRequest.getIResource().LogOut(PreferencesUtils.getString(SettingActivity.this, KEY.FLAG_TOOKENID));
        callRequest(call, new HttpCallBack(DynamicInviteEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {

                } else {
                }
                //请求接口响应后，无论是否退出成功都退出，onFailure中不予退出成功

                //退出登录操作
                BaseApp.logOut(true);
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }

}

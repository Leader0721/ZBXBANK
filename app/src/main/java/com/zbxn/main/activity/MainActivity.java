package com.zbxn.main.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.dialog.MessageDialog;
import com.pub.entity.UserEntity;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.KEY;
import com.pub.utils.MyToast;
import com.pub.utils.PreferencesUtils;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.activity.login.LoginActivity;
import com.zbxn.main.activity.memberCenter.CompanyCreateActivity;
import com.zbxn.main.activity.service.AppUpdateUtils;
import com.zbxn.main.activity.service.AppVersion;
import com.zbxn.main.entity.CompanyEntity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import retrofit2.Call;

public class MainActivity extends BaseActivity {

    //两次按返回键的间隔
    private static final long BACK_DURATION = 2000;
    @BindView(R.id.framlayout_content)
    FrameLayout framlayoutContent;
    @BindView(R.id.layout_bottombar)
    LinearLayout layoutBottombar;
    @BindView(R.id.mShortCutContainer)
    FrameLayout mShortCutContainer;

    private MainFragmentManager mPagerManager;

    //第一次按返回键的时间
    private long mFistPressBackTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //隐藏返回按钮
        setBackGone();
        //设置标题
        setTitle("首页");
        mPagerManager = new MainFragmentManager(this);

        String tokenid = PreferencesUtils.getString(BaseApp.getContext(), KEY.FLAG_TOOKENID, "");
        tokenid = tokenid.replaceAll("-", "");
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, tokenid));
        //如果停止了resume
        if (JPushInterface.isPushStopped(this)) {
            JPushInterface.resumePush(this);
        }

        String info_msg = PreferencesUtils.getString(BaseApp.getContext(),
                LoginActivity.FLAG_INFO_MSG, "");
        int info_msg_count = PreferencesUtils.getInt(BaseApp.getContext(),
                LoginActivity.FLAG_INFO_MSG_COUNT, 0);
        if (!StringUtils.isEmpty(info_msg) && info_msg_count < 3) {
            info_msg_count++;
            PreferencesUtils.putInt(BaseApp.getContext(), LoginActivity.FLAG_INFO_MSG_COUNT, info_msg_count);
            MessageDialog dialog = new MessageDialog(this);
            dialog.setTitle("提示");
            dialog.setMessage(info_msg);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int arg1) {

                }
            });
            dialog.show();
        }

        setData(getIntent(), false);

        if (!StringUtils.isEmpty(tokenid)) {
            getNewVersion();
            GetUserLoginUserInfo();
            GetCompanyListByUID();
        }
    }

    //首次进入显示创建企业
    private void initDialog() {
        if (PreferencesUtils.getBoolean(MainActivity.this, KEY.FLAG_ISFIRST_CREATECOMPANY, true)) {
            PreferencesUtils.putBoolean(MainActivity.this, KEY.FLAG_ISFIRST_CREATECOMPANY, false);
            final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new BitmapDrawable());
            window.setContentView(R.layout.dialog_company_create);
            ImageView ivCreate = (ImageView) window.findViewById(R.id.iv_create);
            ImageView ivCancel = (ImageView) window.findViewById(R.id.iv_cancel);
            //取消
            ivCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            //创建
            ivCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, CompanyCreateActivity.class));
                    dialog.dismiss();
                }
            });
            Resources resources = BaseApp.getContext().getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            int windowWidth = dm.widthPixels;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = windowWidth * 5 / 6;//定义宽度
            //lp.height = windowHeight * 1 / 3;//定义高度
            dialog.getWindow().setAttributes(lp);
        }
    }

    @Override
    public void initRight() {
        setRight2Show(false);
        switch (mPagerManager.getCurrentPageIndex()) {
            case 0:
//                setRight1Show(true);
//                setRight1("添加好友");
//                setRight1Icon(R.mipmap.pub_contacts_add);
                break;
            case 1:
                break;
            case 2:
//                setRight1Show(true);
//                setRight1("添加好友");
//                setRight1Icon(R.mipmap.pub_contacts_add);
//                setRight2Show(true);
//                setRight2("搜索");
//                setRight2Icon(R.mipmap.pub_contacts_search);
                break;
            default:
                setRight1Show(false);
                setRight2Show(false);
                break;
        }
    }

    @Override
    public void actionRight1(MenuItem menuItem) {
        switch (mPagerManager.getCurrentPageIndex()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
//                Intent intent = new Intent(this, ContactsInviteActivity.class);
//                startActivity(intent);
//                ScreenUtil.snapShotWithStatusBar(this);
                break;
            default:
                break;
        }

        super.actionRight1(menuItem);
    }

    @Override
    public void actionRight2(MenuItem menuItem) {
        switch (mPagerManager.getCurrentPageIndex()) {
            case 0:


                break;
            case 1:

                break;
            case 2:
//                Intent intent = new Intent(this, ContactsSearchActivity.class);
//                intent.putExtra("type", 0);
//                startActivity(intent);
//                ScreenUtil.snapShotWithStatusBar(this);
                break;
            default:
                break;
        }

        super.actionRight1(menuItem);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long duration = System.currentTimeMillis() - mFistPressBackTime;
            if (duration < BACK_DURATION) {
                return super.onKeyDown(keyCode, event);
            } else {
                mFistPressBackTime = System.currentTimeMillis();
                MyToast.showToast("再按一次退出应用");
                return false;
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        setData(intent, true);
    }

    private void setData(Intent intent1, final boolean isNewIntent) {
        //退出登录
        String exit = getIntent().getStringExtra("exit");
        if ("exit".equals(exit)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        String extra = intent1.getStringExtra("extra");
        JpushController.doJump(this, extra, new ICustomListener() {
            @Override
            public void onCustomListener(int obj0, Object obj1, int position) {
                switch (obj0) {
                    case 0:
                        mPagerManager.setTabSelection(0, 1);
                        layoutBottombar.post(new Runnable() {
                            @Override
                            public void run() {
                                mPagerManager.setRefresh(0, 1);
                            }
                        });
                        break;
                }
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
                    Set<String> tag = new HashSet<String>();
                    tag.add("EnterpriseApp");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj, tag, mAliasCallback);

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
                    System.out.println(logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。

                    // 保存是否设置别名状态
                    PreferencesUtils.putBoolean(MainActivity.this, KEY.ISSETALIAS, true);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    System.out.println(logs);

                    // 保存是否设置别名状态
                    PreferencesUtils.putBoolean(MainActivity.this, KEY.ISSETALIAS, false);

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

    /**
     * 自动升级
     */
    public void getNewVersion() {
        Call call = HttpRequest.getIResource().getNewVersion("1");
        callRequest(call, new HttpCallBack(AppVersion.class, MainActivity.this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    AppVersion entity = (AppVersion) mResult.getData();
                    AppUpdateUtils.init(MainActivity.this, entity, true, true);
                    AppUpdateUtils.upDate();
                } else {
                    //MyToast.showToast(mResult.getMsg());
                }
            }

            @Override
            public void onFailure(String string) {
                //MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }

    /**
     * 获取公司列表用于判断是否显示蒙版引导
     */
    public void GetCompanyListByUID() {
        //请求网络
        Call call = HttpRequest.getIResource().GetCompanyListByUserId();
        callRequest(call, new HttpCallBack(CompanyEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//0成功
                    List<CompanyEntity> childList = mResult.getDataList();
                    if (StringUtils.isEmpty(childList)) {
                        initDialog();
                    }
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

    //获取详情
    public void GetUserLoginUserInfo() {
        Call call = HttpRequest.getIResource().GetUserLoginUserInfo(PreferencesUtils.getString(this, KEY.FLAG_TOOKENID));
        callRequest(call, new HttpCallBack(UserEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    BaseApp.mUserEntity = (UserEntity) mResult.getData();
                    EventCustom eventCustom = new EventCustom();
                    eventCustom.setTag(EventKey.PERMISSIONS);
                    eventCustom.setObj("");
                    EventBus.getDefault().post(eventCustom);
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

    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (EventKey.COMPANYREFRESH.equals(eventCustom.getTag())) {
            GetUserLoginUserInfo();
        }
    }
}

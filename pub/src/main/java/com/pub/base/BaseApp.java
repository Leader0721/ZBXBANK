package com.pub.base;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lidroid.xutils.DbUtils;
import com.pub.application.IAppInitControl;
import com.pub.entity.UserEntity;
import com.pub.utils.ConfigUtils;
import com.pub.utils.DeviceUtils;
import com.pub.utils.FileAccessor;
import com.pub.utils.ImageAdapter;
import com.pub.utils.ImageLoaderConfig;
import com.pub.utils.KEY;
import com.pub.utils.MyToast;
import com.pub.utils.PreferencesUtils;
import com.pub.weexutils.WXEventModule;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
/**
 * Created by Leader on 2017/5/22.
 *
 */

public class BaseApp extends Application {
    public static Context CONTEXT = null;
    public static DbUtils DBLoader = null;
    private IAppInitControl mInitController;


    public static UserEntity mUserEntity = null;
    //友盟分享三方平台appkey
    {
        PlatformConfig.setWeixin("wx9bb363fafd76660e", "b399ae72821cf2b61cd31adeacee7130");
        //PlatformConfig.setSinaWeibo("", "");
//        PlatformConfig.setQQZone("1105740972", "qtrJRc4zRjfHE8Jp");
        PlatformConfig.setQQZone("1105735303", "WUYGMADwPBRSw0HB");
    }

    // 百度地图定位
    private LocationClient mLocationClient;

    public static boolean DEBUG = false;

    public static Context getContext() {
        if (CONTEXT != null) {
            return CONTEXT.getApplicationContext();
        } else {
            return new BaseApp().getApplicationContext();
        }
    }

    @Override
    public void onCreate() {
        CONTEXT = this;

        String debug = ConfigUtils.getConfig(ConfigUtils.KEY.debug);
        if (debug != null && debug.equals("true")) {
            DEBUG = true;
        }

        initDatabase();

        ImageLoaderConfig.initImageLoader(this);

        FileAccessor.initFileAccess();

        /**
         * 防止7.0及以上 FileUriExposedException
         * 如果报错，或没有N 可以把 Build.VERSION_CODES.N 换成 24
         */
        if (Build.VERSION.SDK_INT >= 24) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        // 将“12345678”替换成您申请的APPID，申请地址：http://open.voicecloud.cn
//        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=587da70c");

        //极光推送
        JPushInterface.setDebugMode(DEBUG);
        JPushInterface.init(this);

        // 注册百度地图定位事件
        mLocationClient = new LocationClient(this);
        setLocationOption();

        //友盟分享
        UMShareAPI.get(this);
//        Config.REDIRECT_URL = "您新浪后台的回调地址";

        Class<?> clazz = null;
        try {
            clazz = Class
                    .forName("com.pub.application.AppInitControl");
            mInitController = (IAppInitControl) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mInitController.init(this);

        InitConfig config=new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build();
        WXSDKEngine.initialize(this,config);
        try {
            WXSDKEngine.registerModule("WXEventModule", WXEventModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
        initDebugEnvironment(true, "192.168.1.104"/*"DEBUG_SERVER_HOST"*/);
        WXSDKEngine.reload();
        super.onCreate();
    }

    private void initDebugEnvironment(boolean enable, String host) {
        WXEnvironment.sRemoteDebugMode = enable;
        WXEnvironment.sRemoteDebugProxyUrl = "ws://" + host + ":8088/debugProxy/native";
    }
    //接下来是百度地图的一些方法

    /**
     * 获取mLocationClient是否启动
     *
     * @return
     */
    public boolean isStartedLocationClient() {
        return mLocationClient.isStarted();
    }

    /**
     * 启动定位
     *
     * @param listener
     */
    public void startLocationClient(BDLocationListener listener) {
        mLocationClient.registerLocationListener(listener);
        mLocationClient.start();
    }

    /**
     * 停止定位
     */
    public void stopLocationClient(BDLocationListener listener) {
        mLocationClient.unRegisterLocationListener(listener);
        mLocationClient.stop();
    }

    /**
     * 重新定位
     *
     * @param listener
     */
    public void requestLocationClient(BDLocationListener listener) {
        mLocationClient.registerLocationListener(listener);
        mLocationClient.requestLocation();
    }

    // 设置相关参数
    public void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
//		option.setScanSpan(600000);// 设置发起定位请求的间隔时间为5000ms
        // option.setScanSpan(3000);// 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option);
    }

    //解决在Android Studio 运行的时候报E/dalvikvm: Could not find class ‘xxx’,
    //但是在android5.0以上不会报此错误能运行成功。
    //minifyEnabled false 意思是 是否进行混淆
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //   MultiDex.install(this);
    }


    /**
     * 获取该应用公共缓存路径
     *
     * @return
     * @author GISirFive
     */
    public static String getDiskCachePath() {
        return Environment.getExternalStorageDirectory() + "/SeaDreams/";
    }

    public void initDatabase() {
        // 获取应用程序版本号
        int appVersion = DeviceUtils.getInstance(this).getAppVersion();

        DbUtils.DaoConfig config = new DbUtils.DaoConfig(this);
        config.setDbName(ConfigUtils.getConfig(ConfigUtils.KEY.privateInfo) + ".db");
        config.setDbVersion(appVersion);
        config.setDbUpgradeListener(new DbUtils.DbUpgradeListener() {

            /** 当程序目录中的数据库版本与当前应用程序的版本不一致时，会调用此方法 */
            @Override
            public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
            }
        });
        BaseApp.DBLoader = DbUtils.create(config);
        BaseApp.DBLoader.configDebug(DEBUG);
    }

    /**
     * 退出登录操作
     */
    public static void logOut(boolean isJump) {
        // 调用 Handler 来异步设置别名
        mHandlerJPush.sendMessage(mHandlerJPush.obtainMessage(MSG_SET_ALIAS, ""));
        JPushInterface.clearAllNotifications(BaseApp.getContext());
        //是否登录
        PreferencesUtils.putBoolean(BaseApp.getContext(), KEY.FLAG_ISLOGIN, false);
        PreferencesUtils.putString(BaseApp.getContext(), KEY.FLAG_TOOKENID, "");
        //友盟统计 账号的统计 登出后不再统计登录账号的启动等情况
        MobclickAgent.onProfileSignOff();

        if (isJump) {
            MyToast.showToast("退出登录成功");

            Intent intent = new Intent();
            ComponentName cn = new ComponentName("com.zbxn", "com.zbxn.main.activity.MainActivity");
            intent.setComponent(cn);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("exit", "exit");
            BaseApp.getContext().startActivity(intent);
        }
    }

    /**
     * ************* 设置别名 *************************
     */
    private static final int MSG_SET_ALIAS = 1001;
    private static final Handler mHandlerJPush = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    System.out.println("Set alias in handler.");
                    Set<String> tag = new HashSet<String>();
                    tag.add("EnterpriseApp");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getContext(), (String) msg.obj, tag, mAliasCallback);

                    break;
                default:
                    System.out.println("Unhandled msg - " + msg.what);
            }
        }
    };
    private static final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    System.out.println(logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。

                    // 保存是否设置别名状态
                    PreferencesUtils.putBoolean(getContext(), KEY.ISSETALIAS, true);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    System.out.println(logs);

                    // 保存是否设置别名状态
                    PreferencesUtils.putBoolean(getContext(), KEY.ISSETALIAS, false);

                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandlerJPush.sendMessageDelayed(
                            mHandlerJPush.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    System.out.println(logs);
            }
        }
    };
}

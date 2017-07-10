package com.zbxn.main.activity.weex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;
import com.zbxn.R;

public class WeexUpdateLogActivity extends AppCompatActivity implements IWXRenderListener {

    WXSDKInstance mWXSDKInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_weex);
        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);

        /**
         * WXSample 可以替换成自定义的字符串，针对埋点有效。
         * template 是.we transform 后的 js文件。
         * option 可以为空，或者通过option传入 js需要的参数。例如bundle js的地址等。
         * jsonInitData 可以为空。
         * width 为-1 默认全屏，可以自己定制。
         * height =-1 默认全屏，可以自己定制。
         */

//        mWXSDKInstance.render(getPackageName(),  WXFileUtils.loadAsset("http://192.168.1.104:8088/weex/ui_mifi_updatelog.js?_wx_tpl=http%3A%2F%2F192.168.1.104%3A8088%2Fweex%2Fui_mifi_updatelog.js", this), null, null, WXRenderStrategy.APPEND_ASYNC);
//        mWXSDKInstance.renderByUrl(getPackageName(), "http://192.168.1.104:8080/dist/ui/mifi/ui_mifi_updatelog.js", null, null, WXRenderStrategy.APPEND_ASYNC);
        mWXSDKInstance.render(getPackageName(),  WXFileUtils.loadAsset("weex/ui/mifi/ui_mifi_updatelog.js", this), null, null, WXRenderStrategy.APPEND_ASYNC);
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        setContentView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWXSDKInstance!=null){
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityStop();
        }
    }
}

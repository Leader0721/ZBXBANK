package com.pub.weexutils;

import android.widget.Toast;

import com.pub.base.BaseApp;
import com.pub.utils.KEY;
import com.pub.utils.PreferencesUtils;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXModuleAnno;

/**
 * Created by ZBX on 2017/7/7.
 */
public class WXEventModule extends WXModule {
    @WXModuleAnno(runOnUIThread = true)
    public float getNavBarHeight() {
        return 88;
    }

    @WXModuleAnno(runOnUIThread = true)
    public String getTokenId() {
        String tokenid = PreferencesUtils.getString(BaseApp.getContext(), KEY.FLAG_TOOKENID, "");
        return tokenid;
    }
}

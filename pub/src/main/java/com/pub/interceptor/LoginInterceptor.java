package com.pub.interceptor;


import com.pub.base.BaseApp;
import com.pub.utils.MyToast;

/**
 * @author ZBX
 * @time 2017/3/7
 */

public class LoginInterceptor {
    /**
     * 拦截器处理错误码
     *
     * @param code
     */
    public static void parseCode(int code) {
        if (code == 10000) {
            MyToast.showToast("会话失效，请重新登录");

            //退出登录操作
            BaseApp.logOut(true);
        } else if (code == -10000) {
            MyToast.showToast("解码失败");
        }
    }

    public static void parseCosttime(String costTime) {
        int costtime = Integer.parseInt(costTime);
        if (costtime > 15) {

        }

    }
}

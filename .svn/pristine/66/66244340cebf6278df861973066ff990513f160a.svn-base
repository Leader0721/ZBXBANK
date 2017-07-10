package com.zbxn.main.activity.h5common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.dialog.ZBXAlertDialog;
import com.pub.dialog.ZBXAlertListener;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.ProgressWebView;
import com.zbxn.R;
import com.zbxn.main.activity.credit.AdjunctActivity;
import com.zbxn.main.entity.DraftEntity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * 浏览器Hybrid页面
 *
 * @author: ysj
 * @date: 2016-10-11 14:08
 */
public class WebviewActivity extends BaseActivity {
    @BindView(R.id.webview_progress)
    ProgressWebView webviewProgress;
    @BindView(R.id.tv_set)
    TextView tvSet;
    @BindView(R.id.tv_clear)
    TextView tvClear;

    private String tag = "WebviewActivity";


    private JSHook mJSHook = new JSHook();

    private String mUrl = "";
    private String mJson = "";
    private String mFormInfo = "";
    private String mFrom;//1新建  2查看申请 银 3草稿箱  Bank 来自行端
    private int mStep;//当前步
    private String mLoanApplyId;
    private String mVersionId;
    private String mAction;
    private String mPhone = "";   //这个是电话
    private int mStatusCode;//0未提交 1待审核 2已通过 3已拒绝

    //0--没开始加载  1--加载中  2--加载完成
    private int mWebviewState = 0;

    private int mStepNew = 0;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        mJson = getIntent().getStringExtra("json");
        mFrom = getIntent().getStringExtra("From");
        mStep = getIntent().getIntExtra("Step", 0);
        mLoanApplyId = getIntent().getStringExtra("LoanApplyId");
        mVersionId = getIntent().getStringExtra("VersionId");
        mUrl = getIntent().getStringExtra("Url");
        mAction = getIntent().getStringExtra("Action");
        mPhone = getIntent().getStringExtra("Phone");
        mStatusCode = getIntent().getIntExtra("StatusCode", 0);

        setTitle("日志浏览器壳");
        hideToolbar();

        initView();

        if (BaseApp.DEBUG) {
            tvSet.setVisibility(View.VISIBLE);
        } else {
            tvSet.setVisibility(View.GONE);
        }

        if (StringUtils.isEmpty(mUrl)) {
            setStep();
        }

        getRequestInfo(WebviewActivity.this, mAction, mJson, true);

//        if ("1".equals(mFrom)) {
        mWebviewState = 1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                //0--没开始加载  1--加载中  2--加载完成
                while (mWebviewState == 1) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mWebviewState = 1;
                handler.sendEmptyMessage(0);
            }
        }).start();
        webviewProgress.loadUrl(mUrl);
//        } else if ("2".equals(mFrom)) {
//            getState();
//        } else if ("3".equals(mFrom)) {
//            getState();
//        }
        getApply();
    }


    private void initView() {
//        webviewProgress.getSettings().setDefaultFontSize(14);
        webviewProgress.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webviewProgress.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webviewProgress.getSettings().setBuiltInZoomControls(true);
        // 不显示webview缩放按钮
        webviewProgress.getSettings().setDisplayZoomControls(false);
        // 扩大比例的缩放
        webviewProgress.getSettings().setUseWideViewPort(true);
        // 自适应屏幕
        webviewProgress.getSettings().setLayoutAlgorithm(
                WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webviewProgress.getSettings().setLoadWithOverviewMode(true);
        webviewProgress.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                System.out.println("url:" + url);
                mWebviewState = 2;
                super.onPageFinished(view, url);
            }

        });

        webviewProgress.addJavascriptInterface(mJSHook, "objectModel"); //在JSHook类里实现javascript想调用的方法，并将其实例化传入webview, "hello"这个字串告诉javascript调用哪个实例的方法

    }

    private void setStep() {
        if (mStep == 1) {
            mUrl = "file:///android_asset/projectBank/u_borrower.html";
            mAction = "ChinaRCB/getBorrower";
        } else if (mStep == 2) {
            mUrl = "file:///android_asset/projectBank/u_borrowerBondsman.html";
            mAction = "ChinaRCB/getGuarantorOne";
        } else if (mStep == 3) {
            mUrl = "file:///android_asset/projectBank/u_borrowerBondsman2.html";
            mAction = "ChinaRCB/getGuarantorTwo";
        } else if (mStep == 4) {
            mUrl = "file:///android_asset/projectBank/u_loanApplication.html";
            mAction = "ChinaRCB/getApplication";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1000) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class JSHook {

        @JavascriptInterface
        public void javaMethod(String p) {
            Log.d(tag, "JSHook.JavaMethod() called! + " + p);
        }

        @JavascriptInterface
        public String getInfo() {
            return "获取手机内的信息！！";
        }

        //Request对象用于浏览器提交的数据
        @JavascriptInterface
        public void submitObject(String param, String action, String submitType, String isShowMsg) {//, boolean isShowProgress
            //param转map
            Map<String, String> map = new HashMap<String, String>();

            map = toMap(param);

            getRequest(WebviewActivity.this, action, param, true, submitType, isShowMsg);
        }

        @JavascriptInterface
        public void goBack() {
            finish();
        }

        @JavascriptInterface
        public void alertMsg(String msg) {
            MyToast.showToast(msg);
        }

        @JavascriptInterface
        public void showAlert() {
            Dialog dialog = new ZBXAlertDialog(WebviewActivity.this, new ZBXAlertListener() {
                @Override
                public void onDialogOk(Dialog dlg) {
                    finish();
                }

                @Override
                public void onDialogCancel(Dialog dlg) {
                    dlg.dismiss();
                }
            }, "提示", "您有修改内容未保存，确定要关闭吗?");
            dialog.show();
        }

        @JavascriptInterface
        public void pushStep(int step) {
            mStep = step;
            setStep();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("LoanApplyId", mLoanApplyId);
            jsonObject.addProperty("VersionId", mVersionId);
            jsonObject.addProperty("CreateType", "0");//用户：0；银行：1
            if ("2".equals(mFrom)) {//1新建  2查看申请  3草稿箱
                jsonObject.addProperty("StatusCode", mStatusCode);//0未提交 1待审核 2已通过 3已拒绝
            }
            jsonObject.addProperty("Step", mStep);
            Intent intent = null;
            if (mStep == 5) {
                intent = new Intent(WebviewActivity.this, AdjunctActivity.class);
            } else {
                intent = new Intent(WebviewActivity.this, WebviewActivity.class);
            }
            intent.putExtra("url", mUrl);
            intent.putExtra("Action", mAction);
            intent.putExtra("json", jsonObject.toString());
            intent.putExtra("From", mFrom);
            intent.putExtra("Step", mStep);
            intent.putExtra("LoanApplyId", mLoanApplyId);
            intent.putExtra("VersionId", mVersionId);
            intent.putExtra("StatusCode", mStatusCode);
            startActivity(intent);
            finish();
        }

    }

    /**
     * 获取已保存或已提交表单内容
     */
    public void getRequestInfo(Context context, final String action, String param, boolean isShowProgress) {
        //请求网络
        Call call = HttpRequest.getIResource().GetRequestBody(action, jsonToBody(param));
        callRequest(call, new HttpCallBack(String.class, context, isShowProgress) {

            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {
                    mFormInfo = (String) mResult.getData();

                    handler.sendEmptyMessage(0);
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(string);
            }
        });
    }

    /**
     * 保存或提交接口
     */
    public void getRequest(Context context, final String action, String param, boolean isShowProgress, final String submitType, final String isShowMsg) {
        //请求网络
        Call call = HttpRequest.getIResource().GetRequestBody(action, jsonToBody(param));
        callRequest(call, new HttpCallBack(String.class, context, isShowProgress) {

            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {
                    String result = (String) mResult.getData();
                    //mJSHook.setVector(result);
                    if ("ChinaRCB/addBorrower".equals(action) ||//4.	借款人资料提交（银行端修改）
                            "ChinaRCB/addGuarantorOne".equals(action) ||//5.	第一担保人资料提交（银行端修改）
                            "ChinaRCB/addGuarantorTwo".equals(action) ||//6.	第二担保人资料提交（银行端修改）
                            "ChinaRCB/addLoanApplication".equals(action)//7.	企业借款申请书提交
                            ) {
                        //submitType:(数据请求后的处理)0为不做处理,1为跳转下一步,2为返回上一步,3为审核
                        if (!"0".equals(submitType)) {
                            finish();
                        } else {
                            MyToast.showToast("保存成功");
                        }
//                        if ("1".equals(isShowMsg) && !"1".equals(isShowMsg)) {
//                            MyToast.showToast("保存成功");
//                        }

                        //submitType:(数据请求后的处理)0为不做处理,1为跳转下一步,2为返回上一步,3为审核
//                        if (!"Bank".equals(mFrom)&&"1".equals(submitType)) {
                        if ("1".equals(submitType)) {
                            Intent intent = null;
                            if (mStep + 1 == 5) {
                                intent = new Intent(WebviewActivity.this, AdjunctActivity.class);
                            } else {
                                intent = new Intent(WebviewActivity.this, WebviewActivity.class);
                            }
                            JsonObject jsonObject = new JsonParser().parse(mJson).getAsJsonObject();
                            jsonObject.remove("Step");
                            jsonObject.addProperty("Step", mStep + 1);
                            intent.putExtra("json", jsonObject.toString());
                            intent.putExtra("From", mFrom);//1新建  2查看申请  3草稿箱
                            intent.putExtra("Step", mStep + 1);
                            intent.putExtra("StatusCode", mStatusCode);
                            intent.putExtra("LoanApplyId", mLoanApplyId);
                            intent.putExtra("VersionId", mVersionId);
                            startActivity(intent);
                        }
                    } else if ("ChinaRCB/AddDraft".equals(action)) {
                        MyToast.showToast("草稿保存成功");
                    } else {
                        MyToast.showToast("保存成功");
                    }
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(string);
            }
        });
    }

    /**
     * json转map
     *
     * @param json
     * @return
     */
    private Map<String, String> toMap(String json) {
        Map<String, String> data = new HashMap<String, String>();
        try {
            // 将json字符串转换成jsonObject
            JSONObject jsonObject = new JSONObject(json);
            Iterator ite = jsonObject.keys();
            // 遍历jsonObject数据,添加到Map对象
            while (ite.hasNext()) {
                String key = ite.next().toString();
                String value = jsonObject.get(key).toString();
                data.put(key, value);
            }
            // 或者直接将 jsonObject赋值给Map
            // data = jsonObject;
        } catch (Exception e) {

        }
        return data;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    webviewProgress.loadUrl("javascript:setVector('" + mJson + "')");
                    if (!StringUtils.isEmpty(mFormInfo)) {
                        //字符串转义
                        String formInfoTemp = mFormInfo.replace("\\t", "\\\\t");
                        System.out.print("formInfoTemp:" + formInfoTemp);
                        webviewProgress.loadUrl("javascript:matchObjectValue('" + formInfoTemp + "')");
                    }
                    if (!StringUtils.isEmpty(mPhone)) {
                        String result = "{\"companyName\":\"companyName\",\"companyTel\":\"" + mPhone + "\"}";
                        webviewProgress.loadUrl("javascript:initBaseInfo('" + result + "')");
                    }
                    break;
            }
        }
    };

    @OnClick({R.id.tv_set, R.id.tv_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_set:
                webviewProgress.loadUrl("javascript:resetValue()");
                break;
            case R.id.tv_clear:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            webviewProgress.loadUrl("javascript:goBack()");
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void getApply() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoanApplyId", mLoanApplyId);
        String raw = jsonObject.toString();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, raw);
        Call call = HttpRequest.getIResource().getApply(body);
        callRequest(call, new HttpCallBack(DraftEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    DraftEntity entity = (DraftEntity) mResult.getData();
                    mStepNew = entity.getStep();

                    JsonObject jsonObject = new JsonParser().parse(mJson).getAsJsonObject();
                    if (mStepNew + 1 == mStep) {
                        jsonObject.remove("IsCurrent");
                        jsonObject.addProperty("IsCurrent", true);
                    } else {
                        jsonObject.remove("IsCurrent");
                        jsonObject.addProperty("IsCurrent", false);
                    }

                    mJson = jsonObject.toString();
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

}

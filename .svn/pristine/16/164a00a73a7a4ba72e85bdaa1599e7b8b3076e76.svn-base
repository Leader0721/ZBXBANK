package com.zbxn.main.activity.steward;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pub.base.BaseActivity;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.LogUtil;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.credit.AdjunctActivity;
import com.zbxn.main.activity.h5common.WebviewActivity;
import com.zbxn.main.entity.DraftEntity;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/3/12.
 */
public class CustomInfoActivity extends BaseActivity {
    //    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    @BindView(R.id.iv_jiekuan)
    ImageView ivJiekuan;
    @BindView(R.id.ll_first)
    LinearLayout llFirst;
    @BindView(R.id.iv_liudong)
    ImageView ivLiudong;
    @BindView(R.id.ll_four)
    LinearLayout llFour;
    @BindView(R.id.tv_check)
    TextView tvCheck;
    @BindView(R.id.v_liudong)
    View vLiudong;
    @BindView(R.id.v_bottomLine)
    View vBottomLine;
    @BindView(R.id.v_jiekuanren)
    View vJiekuanren;
    private int isFirstShow = 1;//1代表的是一个展示   2表示隐藏
    private int isFourShow = 1;
    private String mLoanApplyId = "";//这个是申请单ID  从跳转页传过来
    private String mVersionId = "";   //这个是版本号ID
    private String mPhone = "";   //这个是电话
    private boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steward_custominfo);
        ButterKnife.bind(this);
        mLoanApplyId = getIntent().getStringExtra("LoanApplyId");
        mVersionId = getIntent().getStringExtra("VersionId");
        mPhone = getIntent().getStringExtra("Phone");
        getApply();
        setTitle("客户信息");
    }

    @OnClick({R.id.rl_jiekuan, R.id.rl_jiekuanren, R.id.rl_danbaoren1,
            R.id.rl_danbaoren2, R.id.rl_shenqing, R.id.rl_basicInfo,
            R.id.rl_liudong, R.id.rl_hefaxing, R.id.rl_anquanxingone,
            R.id.rl_anquanxingtwo, R.id.rl_suggestion, R.id.rl_xuqiuliang, R.id.iv_callphone,
            R.id.rl_enterpriseInfo, R.id.rl_caiwuExcel, R.id.tv_check})
    public void onClick(View view) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoanApplyId", mLoanApplyId);
//        jsonObject.addProperty("VersionId", mVersionId);
        jsonObject.addProperty("CreateType", "1");//用户：0；银行：1
        jsonObject.addProperty("LoanApplyId", mLoanApplyId);
        jsonObject.addProperty("Flag", "2");//1为贷款申请,2为信贷管理,不传走原来流程
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_jiekuan:
                if (isFirstShow == 1) {
                    llFirst.setVisibility(View.VISIBLE);
                    vJiekuanren.setVisibility(View.VISIBLE);
                    isFirstShow = 2;
                } else {
                    llFirst.setVisibility(View.GONE);
                    vJiekuanren.setVisibility(View.GONE);
                    isFirstShow = 1;
                }
                break;
            case R.id.rl_jiekuanren://借款人
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("Url", "file:///android_asset/projectBank/b_borrower.html");
                intent.putExtra("Action", "ChinaRCB/getBorrower");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.rl_danbaoren1://第一担保人
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("Url", "file:///android_asset/projectBank/b_borrowerBondsman.html");
                intent.putExtra("Action", "ChinaRCB/getGuarantorOne");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.rl_danbaoren2://第二担保人
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("Url", "file:///android_asset/projectBank/b_borrowerBondsman2.html");
                intent.putExtra("Action", "ChinaRCB/getGuarantorTwo");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.rl_shenqing://借款申请书
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("Url", "file:///android_asset/projectBank/b_loanApplication.html");
                intent.putExtra("Action", "ChinaRCB/getApplication");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.rl_basicInfo:
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("Url", "file:///android_asset/projectBank/b_basicInvestigation.html");
                intent.putExtra("Action", "ChinaRCB/GetBaseInvestigate");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.iv_callphone:
                String string = mPhone + "";
                showCallServiceDialog(string);
                break;
            case R.id.rl_liudong://流动资金贷款调查
                if (isFourShow == 1) {
                    llFour.setVisibility(View.VISIBLE);
                    isFourShow = 2;
                    vLiudong.setVisibility(View.VISIBLE);
                } else {
                    llFour.setVisibility(View.GONE);
                    isFourShow = 1;
                    vLiudong.setVisibility(View.GONE);
                }
                break;
            case R.id.rl_hefaxing://合法性认定
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("Url", "file:///android_asset/projectBank/b_legality.html");
                intent.putExtra("Action", "ChinaRCB/GetInvestigateOne");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.rl_anquanxingone://安全性认定-第一还款来源
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("Url", "file:///android_asset/projectBank/b_legalitySource1.html");
                intent.putExtra("Action", "ChinaRCB/GetInvestigateTwo");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.rl_anquanxingtwo://安全性认定-第二还款来源
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("Url", "file:///android_asset/projectBank/b_legalitySource2.html");
                intent.putExtra("Action", "ChinaRCB/GetInvestigateThree");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.rl_suggestion://调查意见
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("Url", "file:///android_asset/projectBank/b_opinionSurvey.html");
                intent.putExtra("Action", "ChinaRCB/GetInvestigateFour");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.rl_xuqiuliang://需求量测算
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("Url", "file:///android_asset/projectBank/b_demandMeasure.html");
                intent.putExtra("Action", "ChinaRCB/GetInvestigateFive");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.rl_enterpriseInfo://企业资料
                intent = new Intent(this, AdjunctActivity.class);
                intent.putExtra("show", true);
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.rl_caiwuExcel://财务报表
                intent = new Intent(CustomInfoActivity.this, FinaExcelActivity.class);
                intent.putExtra("bank", true);
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("From", "Bank");
                intent.putExtra("Phone", mPhone);
                startActivity(intent);
                break;
            case R.id.tv_check:
                ApplyPopup(vBottomLine);
                break;
        }
    }


    @SuppressLint("NewApi")
    public void ApplyPopup(View view) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_custominfo, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);//参数四：设置popupwindow是否占有焦点
        popupWindow.setAnimationStyle(R.style.AnimationFromButtom);
        final TextView tvPass = (TextView) contentView.findViewById(R.id.tv_pass);
        final TextView tvReject = (TextView) contentView.findViewById(R.id.tv_reject);
        final EditText etContent = (EditText) contentView.findViewById(R.id.et_suggestion);
        TextView tvCertain = (TextView) contentView.findViewById(R.id.tv_certain);
        TextView tvCancel = (TextView) contentView.findViewById(R.id.tv_cancel);

//        设置使背景颜色半透明
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });


        //默认设置通过
        tvPass.setSelected(true);

        tvCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isPass = false;
                if (tvPass.isSelected()) {
                    isPass = true;
                } else {
                    isPass = false;
                }

                if (checked) {
                    MyToast.showToast("该申请已经经过处理");
                } else {
                    auditApply(isPass, StringUtils.getEditText(etContent));
                }
                popupWindow.dismiss();
            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        tvPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPass.setSelected(true);
                tvReject.setSelected(false);
            }
        });
        tvReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPass.setSelected(false);
                tvReject.setSelected(true);
            }
        });
        popupWindow.setOutsideTouchable(true);

//    	！！！如果想让setOutsideTouchable起作用必须设置setBackgroundDrawable
//    	这个属于Android漏洞
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(R.style.AnimBottom);
//    	参数一：只要是当前Activity下的View控件----->getWindowToken();---->识别是当前的窗体
//    	参数三四是偏移量左边负右边是正；上边是负下边是正
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }


    //打电话
    private void showCallServiceDialog(final String string) {
        if (StringUtils.isEmpty(string)) {
            MyToast.showToast("企业电话不存在");
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + string);
            intent.setData(data);
            startActivity(intent);
        }

    }

    public void auditApply(boolean status, final String message) {
        Call call = HttpRequest.getIResource().AuditApply(mLoanApplyId, status, message);
        callRequest(call, new HttpCallBack(JsonObject.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    EventCustom eventCustom = new EventCustom();
                    eventCustom.setTag(EventKey.STEWARDLIST);
                    EventBus.getDefault().post(eventCustom);
                    tvCheck.setVisibility(View.GONE);
                    finish();
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


    public void addLoanApplyByAccountManager() {
        Call call = HttpRequest.getIResource().addLoanApplyByAccountManager(mLoanApplyId);
        callRequest(call, new HttpCallBack(JsonObject.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                } else {
                }
            }

            @Override
            public void onFailure(String string) {
            }
        });
    }


    @Override
    public void actionBack() {
        addLoanApplyByAccountManager();
        super.actionBack();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            actionBack();
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
                    if (entity.getStautsId() == 1) {
                        tvCheck.setVisibility(View.VISIBLE);
                        checked = false;
                    } else {
                        tvCheck.setVisibility(View.GONE);
                        checked = true;
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

}


//    @TargetApi(Build.VERSION_CODES.M)
//    private void getPermission(){
//
//        if (!Settings.canDrawOverlays(this)) {
//            Toast.makeText(CustomInfoActivity.this, "当前无权限，请授权！", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                    Uri.parse("package:" + getPackageName()));
//            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
//        }
//
//
//
////        int permission = ContextCompat.checkSelfPermission(CustomInfoActivity.this, Manifest.permission.SYSTEM_ALERT_WINDOW);
////        if (permission == PackageManager.PERMISSION_GRANTED) {
////        } else {//请求权限
////            MyToast.showToast("请设置悬浮窗权限");
////            Intent localIntent = new Intent();
////            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
////            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
////            startActivity(localIntent);
////        }
//    }
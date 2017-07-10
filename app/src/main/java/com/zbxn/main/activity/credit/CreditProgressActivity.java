package com.zbxn.main.activity.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pub.base.BaseActivity;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.widget.stepview.StepView;
import com.pub.widget.stepview.bean.StepBean;
import com.zbxn.R;
import com.zbxn.main.activity.h5common.WebviewActivity;
import com.zbxn.main.entity.DraftEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * 申请流程
 *
 * @author: ysj
 * @date: 2017-03-14 14:51
 */
public class CreditProgressActivity extends BaseActivity {

    private static final int Flag_CallBack_Progress = 10001;

    @BindView(R.id.mStepView)
    StepView mStepView;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_name)
    TextView tvName;
    private List<StepBean> stepsBeanList;
    private int mStepSelect = 0;//记录当前是第几步  起始为0 如果为0  第一步灰色，下面按钮显示进入第一步
    private String mLoanApplyId;
    private String mVersionId;
    private String mFrom;//1新建  2查看申请  3草稿箱
    private int mStatusCode;//0未提交 1待审核 2已通过 3已拒绝

    private int mStepNew = 0;
    private int code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_progress);
        ButterKnife.bind(this);
        setTitle("申请贷款");
        initData();
        initView();
    }

    private void initData() {
        mFrom = getIntent().getStringExtra("From");
        mLoanApplyId = getIntent().getStringExtra("LoanApplyId");
        mVersionId = getIntent().getStringExtra("VersionId");
        mStatusCode = getIntent().getIntExtra("StatusCode", 0);
        code = getIntent().getIntExtra("finish", 0);
        if (code == 1) {
            setResult(RESULT_OK);
            finish();
        }
        if (getIntent().getStringExtra("StatusCode") != null) {
            mStatusCode = Integer.decode(getIntent().getStringExtra("StatusCode"));
        }
        stepsBeanList = new ArrayList<>();
        stepsBeanList.add(new StepBean("借款人", 0));
        stepsBeanList.add(new StepBean("第一担保人", 0));
        stepsBeanList.add(new StepBean("第二担保人", 0));
        stepsBeanList.add(new StepBean("申请书", 0));
        stepsBeanList.add(new StepBean("上传附件", 0));
        mStepView.setmList(stepsBeanList);
    }

    private void initView() {
        mStepView.setItemClickListener(new StepView.OnStepViewItemClickListener() {
            @Override
            public void itemClickListener(int position) {
                if (position == 0) {
                    mStepSelect = position;
                    tvName.setText("进入" + stepsBeanList.get(mStepSelect).getName());
                    tvName.performClick();
                    return;
                }
                if (mStepView.isSelect(position)) {
                    mStepSelect = position;
                    tvName.setText("进入" + stepsBeanList.get(mStepSelect).getName());
                    tvName.performClick();
                } else {
                    MyToast.showToast("请先完成前面的流程");
                }
            }
        });

//        if ("1".equals(mFrom) || "3".equals(mFrom)) {//1新建  2查看申请  3草稿箱
        if (code != 1) {
            getApply();
        }
//        }

        //StatusCode 0未提交 1待审核 2已通过 3已拒绝
        if ("2".equals(mFrom) && (1 == mStatusCode || 2 == mStatusCode)) {//1新建  2查看申请  3草稿箱
            tvContent.setText("温馨提示：点击上方按钮可以切换查看详情内容");
            tvName.setVisibility(View.GONE);
        }
    }

    @Override
    public void initRight() {
        super.initRight();
        setRight1Icon(0);
        setRight1("提交审核");

        if ("2".equals(mFrom)) {//1新建  2查看申请  3草稿箱
            mStepSelect = 4;
            for (int i = 0; i < 5; i++) {
                stepsBeanList.get(i).setState(1);
            }
            mStepView.setmList(stepsBeanList);
            setRight1Show(true);
            tvName.setText("进入" + stepsBeanList.get(mStepSelect).getName());
        }
        if (mStatusCode == 2 || mStatusCode == 1) {
            setRight1Show(false);
        }
    }

    @Override
    public void actionRight1(MenuItem menuItem) {
        super.actionRight1(menuItem);
        submitApply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mStatusCode == 2 || mStatusCode == 1) {
            setRight1Show(false);
        } else {
            getApply();
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
                    if (!"2".equals(mFrom)) {
                        mStepNew = entity.getStep();
                        for (int i = 0; i < entity.getStep(); i++) {
                            stepsBeanList.get(i).setState(1);
                        }
                        mStepView.setmList(stepsBeanList);

                        if (entity.getStep() >= stepsBeanList.size()) {
                            mStepSelect = stepsBeanList.size() - 1;
                            setRight1Show(true);
                        } else {
                            mStepSelect = entity.getStep();
//                        setRight1Show(false);
                        }
                        tvName.setText("进入" + stepsBeanList.get(mStepSelect).getName());
                    } else {
                        mStatusCode = entity.getStautsId();
                        if (mStatusCode == 2 || mStatusCode == 1) {
                            setRight1Show(false);
                            tvContent.setText("温馨提示：点击上方按钮可以切换查看详情内容");
                            tvName.setVisibility(View.GONE);
                        }
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

    @OnClick(R.id.tv_name)
    public void onClick() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoanApplyId", mLoanApplyId);
        jsonObject.addProperty("VersionId", mVersionId);
        jsonObject.addProperty("CreateType", "0");//用户：0；银行：1
        jsonObject.addProperty("Flag", "1");//1为贷款申请,2为信贷管理,不传走原来流程
        if ("2".equals(mFrom)) {//1新建  2查看申请  3草稿箱
            jsonObject.addProperty("StatusCode", mStatusCode);//0未提交 1待审核 2已通过 3已拒绝
        }
        if (mStepNew == mStepSelect) {
            jsonObject.addProperty("IsCurrent", true);
        }
        Intent intent = null;
        switch (mStepSelect) {
            case 0://借款人0
                jsonObject.addProperty("Step", 1);
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("url", "file:///android_asset/projectBank/u_borrower.html");
                intent.putExtra("Action", "ChinaRCB/getBorrower");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("From", mFrom);
                intent.putExtra("Step", mStepSelect + 1);
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("StatusCode", mStatusCode);
                startActivityForResult(intent, Flag_CallBack_Progress);
                break;
            case 1://第一担保人
                jsonObject.addProperty("Step", 2);
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("url", "file:///android_asset/projectBank/u_borrowerBondsman.html");
                intent.putExtra("Action", "ChinaRCB/getGuarantorOne");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("From", mFrom);
                intent.putExtra("Step", mStepSelect + 1);
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("StatusCode", mStatusCode);
                startActivityForResult(intent, Flag_CallBack_Progress);
                break;
            case 2://第二担保人
                jsonObject.addProperty("Step", 3);
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("url", "file:///android_asset/projectBank/u_borrowerBondsman2.html");
                intent.putExtra("Action", "ChinaRCB/getGuarantorTwo");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("From", mFrom);
                intent.putExtra("Step", mStepSelect + 1);
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("StatusCode", mStatusCode);
                startActivityForResult(intent, Flag_CallBack_Progress);
                break;
            case 3://申请书
                jsonObject.addProperty("Step", 4);
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("url", "file:///android_asset/projectBank/u_loanApplication.html");
                intent.putExtra("Action", "ChinaRCB/getApplication");
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("From", mFrom);
                intent.putExtra("Step", mStepSelect + 1);
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("StatusCode", mStatusCode);
                startActivityForResult(intent, Flag_CallBack_Progress);
                break;
            case 4://上传附件
                intent = new Intent(CreditProgressActivity.this, AdjunctActivity.class);
                intent.putExtra("LoanApplyId", mLoanApplyId);
                intent.putExtra("VersionId", mVersionId);
                intent.putExtra("StatusCode", mStatusCode);
                startActivityForResult(intent, Flag_CallBack_Progress);
                break;
            default:
                break;
        }
    }

    /**
     * 提交申请表单
     */
    public void submitApply() {
        Call call = HttpRequest.getIResource().SubmitApply(mLoanApplyId);
        callRequest(call, new HttpCallBack(JsonObject.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    MyToast.showToast("提交成功");
                    Intent intent = new Intent(CreditProgressActivity.this, CreditActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
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

    @Override
    public void actionBack() {
        if ("1".equals(mFrom)) {//1新建  2查看申请  3草稿箱
            MyToast.showToast("您的申请已保存，请去草稿箱查看");
        }
        super.actionBack();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            actionBack();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}

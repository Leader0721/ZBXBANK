package com.zbxn.main.activity.memberCenter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.base.BaseActivity;
import com.pub.common.EventCustom;
import com.pub.dialog.ZBXAlertDialog;
import com.pub.dialog.ZBXAlertListener;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.entity.CompanyEntity;
import com.zbxn.main.entity.UserEntity;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class CompanyDetailActivity extends BaseActivity {

    @BindView(R.id.tv_firstCompanyNmae)
    TextView tvFirstCompanyNmae;
    @BindView(R.id.mCompanyName)
    TextView mCompanyName;
    @BindView(R.id.tv_promissiom)
    TextView tvPromissiom;
    @BindView(R.id.tv_property)
    TextView tvProperty;
    @BindView(R.id.activity_company_detail)
    LinearLayout activityCompanyDetail;
    @BindView(R.id.m_exit)
    TextView mExit;
    @BindView(R.id.civ_Portrait)
    CircleImageView civPortrait;
    @BindView(R.id.tv_mum)
    TextView tvMum;
    private UserEntity.OrganizationBean entity;
    private String id;
    private String userId;
    private ZBXAlertDialog dialog;
    public static final String SUCCESS = "CompanyDetailSuccess";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        id = getIntent().getStringExtra("id");
        if (!StringUtils.isEmpty(id)) {
            GetCompanyInfo();
        }
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ZBXAlertDialog(CompanyDetailActivity.this, new ZBXAlertListener() {
                    @Override
                    public void onDialogOk(Dialog dlg) {
                        ExitCompany();
                        dialog.dismiss();
                    }

                    @Override
                    public void onDialogCancel(Dialog dlg) {
                        dialog.dismiss();
                    }
                }, "提示", "确定退出该企业?");
                dialog.show();
            }
        });

    }

    /**
     * 获取公司详情
     */
    public void GetCompanyInfo() {
        //请求网络
        Call call = HttpRequest.getIResource().GetCompanyInfo(id);
        callRequest(call, new HttpCallBack(CompanyEntity.class, this, true) {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//1成功
                    CompanyEntity entity = (CompanyEntity) mResult.getData();
                    if (entity != null) {
                        //获取到的数据显示上去
                        if (!StringUtils.isEmpty(entity.getLogoUrl())) {
                            civPortrait.setVisibility(View.VISIBLE);
                            tvFirstCompanyNmae.setVisibility(View.GONE);
                            DisplayImageOptions options = new DisplayImageOptions.Builder()
                                    .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                                    .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                                    .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                                    .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                                    //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                                    .build();                                   // 创建配置过得DisplayImageOption对象
                            ImageLoader.getInstance().displayImage(entity.getLogoUrl(), civPortrait, options);
                        } else {
                            civPortrait.setVisibility(View.GONE);
                            tvFirstCompanyNmae.setVisibility(View.VISIBLE);
                            if (entity.getCompanyName().length() >= 1 && !StringUtils.isEmpty(entity.getCompanyName())){
                                tvFirstCompanyNmae.setText(entity.getCompanyName().substring(0, 1));
                            }
                        }
                        mCompanyName.setText(entity.getCompanyName());
                        tvMum.setText(entity.getUserCount() + "");
                        tvPromissiom.setText(entity.getUserPermission());
                        tvProperty.setText(entity.getCompanyType());
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

    /**
     * 退出企业
     */
    public void ExitCompany() {
        //请求网络
        Call call = HttpRequest.getIResource().ExitCompany(id);
        callRequest(call, new HttpCallBack(String.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//1成功
                    if (!"false".equals(mResult.getData())) {
                        MyToast.showToast("操作成功");
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        MyToast.showToast("管理员不能退出企业");
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

    @Override
    public void initRight() {
        super.initRight();
        setTitle("企业");
    }
}

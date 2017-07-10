package com.zbxn.main.activity.dynamic;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.base.BaseActivity;
import com.pub.common.EventCustom;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.ConfigUtils;
import com.pub.utils.MyToast;
import com.pub.utils.ScreenUtils;
import com.pub.widget.pulltozoomview.PullToZoomScrollViewEx;
import com.zbxn.R;
import com.zbxn.main.entity.DynamicInviteEntity;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class CompanyRequestsActivity extends BaseActivity {


    @BindView(R.id.mScrollView)
    PullToZoomScrollViewEx mScrollView;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_achieve)
    TextView tvAchieve;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    private String inviteId;
    private String messageId;
    public static final String SUCCESS = "CompanyRequestSuccess";
    private String body;
    private CircleImageView imgHead;
    private TextView tvName;
    private TextView tvMsg;
    private TextView tvAddress;
    private TextView tvCompanyType;
    private TextView tvDec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_requests);
        ButterKnife.bind(this);
        imgHead = (CircleImageView) findViewById(R.id.img_head);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvCompanyType = (TextView) findViewById(R.id.tv_companyType);
        tvDec = (TextView) findViewById(R.id.tv_Dec);
        hideToolbar();
        initView();
    }

    private void initView() {
        // 调整ZoomView的高度
        int screenWidth = ScreenUtils.getScreenWidth(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                screenWidth, (int) (screenWidth * 9 / 16));
        mScrollView.setHeaderLayoutParams(params);


        inviteId = getIntent().getStringExtra("InviteId");
        messageId = getIntent().getStringExtra("id");
        body = getIntent().getStringExtra("body");
        //拒绝
        tvAchieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefuseInvite();
            }
        });
        //同意
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgreeInvite();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        GetMessageListByUserPhone();
    }

    //获取详情
    private void GetMessageListByUserPhone() {
        Call call = HttpRequest.getIResource().GetInviteInfoByID(inviteId);
        callRequest(call, new HttpCallBack(DynamicInviteEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    DynamicInviteEntity entity = (DynamicInviteEntity) mResult.getData();
                    String companyName = entity.getCompanyName();
                    tvName.setText(companyName);
                    tvAddress.setText(entity.getAddress());
                    tvCompanyType.setText(entity.getCompanyType());
                    tvDec.setText(entity.getComopanyDesc());
                    String mBaseUrl = ConfigUtils.getConfig(ConfigUtils.KEY.webServer);
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                            //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                            .build();                                   // 创建配置过得DisplayImageOption对象

                    ImageLoader.getInstance().displayImage(entity.getLogoUrl(), imgHead, options);
                    tvMsg.setText(body);
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

    //同意
    private void AgreeInvite() {
        Call call = HttpRequest.getIResource().AgreeInvite(inviteId, messageId);
        callRequest(call, new HttpCallBack(DynamicInviteEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    EventCustom eventCustom = new EventCustom();
                    eventCustom.setTag(CompanyRequestsActivity.SUCCESS);
                    EventBus.getDefault().post(eventCustom);
                    finish();
                } else {

                }
                MyToast.showToast(mResult.getMsg());
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }

    //拒绝
    private void RefuseInvite() {
        Call call = HttpRequest.getIResource().RefuseInvite(inviteId, messageId);
        callRequest(call, new HttpCallBack(DynamicInviteEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    EventCustom eventCustom = new EventCustom();
                    eventCustom.setTag(CompanyRequestsActivity.SUCCESS);
                    EventBus.getDefault().post(eventCustom);
                    finish();
                } else {

                }
                MyToast.showToast(mResult.getMsg());
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }

}

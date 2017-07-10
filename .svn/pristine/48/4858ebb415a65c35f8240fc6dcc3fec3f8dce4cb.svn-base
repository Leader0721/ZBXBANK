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
import com.pub.utils.MyToast;
import com.pub.utils.ScreenUtils;
import com.pub.utils.StringUtils;
import com.pub.widget.pulltozoomview.PullToZoomScrollViewEx;
import com.zbxn.R;
import com.zbxn.main.entity.ContactsDetailEntity;
import com.zbxn.main.entity.DynamicInviteEntity;
import com.zbxn.main.entity.PersonalMessageEntity;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class StaffRequestActivity extends BaseActivity {

    @BindView(R.id.mScrollView)
    PullToZoomScrollViewEx mScrollView;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_add_friend)
    TextView tvAddFriend;
    @BindView(R.id.tv_achieve)
    TextView tvAchieve;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.activity_company_request)
    LinearLayout activityCompanyRequest;
    private String messageId;
    private String inviteId;
    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvBirthday;
    private TextView tvCompany;
    private TextView tvAddress;
    private TextView tvName;
    private CircleImageView imgHead;
    private PersonalMessageEntity entity;
    private String id;
    public static final String  SUCCESS="StaffRequestSuccess";
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_request);
        ButterKnife.bind(this);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvEmail = (TextView) findViewById(R.id.tv_email);
//        tvCompany = (TextView) findViewById(R.id.tv_company);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvBirthday = (TextView) findViewById(R.id.tv_birthday);
        tvName = (TextView) findViewById(R.id.tv_name);
        imgHead = (CircleImageView) findViewById(R.id.img_head);
        hideToolbar();
        initView();
    }

    private void initView() {

        userID = getIntent().getStringExtra("userID");
        id = getIntent().getStringExtra("id");
        // 调整ZoomView的高度
        int screenWidth = ScreenUtils.getScreenWidth(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                screenWidth, (int) (screenWidth * 9 / 16));
        mScrollView.setHeaderLayoutParams(params);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //同意
        tvAchieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RespondAddFriend("true");
            }
        });
        //拒绝
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RespondAddFriend("false");
            }
        });
        GetUserInfo();


    }
    //同意或拒绝请求
    private void RespondAddFriend(String isagree) {
        Call call = HttpRequest.getIResource().RespondAddFriend(id,isagree);
        callRequest(call, new HttpCallBack(DynamicInviteEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    EventCustom eventCustom = new EventCustom();
                    eventCustom.setTag(StaffRequestActivity.SUCCESS);
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
    //获取用户信息
    private void GetUserInfo() {
        Call call = HttpRequest.getIResource().GetUserInfo(userID);
        callRequest(call, new HttpCallBack(ContactsDetailEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    ContactsDetailEntity entity = (ContactsDetailEntity) mResult.getData();
                    tvPhone.setText(entity.getPhone());
                    tvEmail.setText(entity.getEmail());
//                    tvCompany.setText("");
                    tvAddress.setText(entity.getAddress());
                    tvName.setText(entity.getRealName());
                    tvBirthday.setText(StringUtils.convertDateDD(entity.getBirthDayStr()));
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
//                            .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                            .build();                                   // 创建配置过得DisplayImageOption对象
                    String headUrl = entity.getHeadImgUrl();
                    ImageLoader.getInstance().displayImage(headUrl, imgHead, options);
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

package com.zbxn.main.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.base.BaseApp;
import com.pub.base.BaseFragment;
import com.pub.common.EventCustom;
import com.pub.dialog.ZBXAlertDialog;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.ConfigUtils;
import com.pub.utils.KEY;
import com.pub.utils.MyToast;
import com.pub.utils.PreferencesUtils;
import com.pub.utils.StringUtils;
import com.pub.widget.eventbus.EventMember;
import com.zbxn.R;
import com.zbxn.main.activity.memberCenter.CompanyActivity;
import com.zbxn.main.activity.memberCenter.MemberCenterActivity;
import com.zbxn.main.activity.memberCenter.SettingActivity;
import com.zbxn.main.entity.MemberCenterEntity;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class MainPersonalFragment extends BaseFragment {

    @BindView(R.id.mPortrait)
    CircleImageView mPortrait;
    @BindView(R.id.mRemarkName)
    TextView mRemarkName;
    @BindView(R.id.mPhonenumber)
    TextView mPhonenumber;
    @BindView(R.id.MyHeadPortrait)
    LinearLayout MyHeadPortrait;
    @BindView(R.id.tv_mCompany)
    TextView tvMCompany;
    @BindView(R.id.mCompany)
    LinearLayout mCompany;
    @BindView(R.id.tv_mCollect)
    TextView tvMCollect;
    @BindView(R.id.mCollect)
    LinearLayout mCollect;
    @BindView(R.id.tv_mAchievement)
    TextView tvMAchievement;
    @BindView(R.id.mAchievement)
    LinearLayout mAchievement;
    @BindView(R.id.tv_mSetting)
    TextView tvMSetting;
    @BindView(R.id.mSetting)
    LinearLayout mSetting;
    private String userId;

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main_personal, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;

    }

    private void initView() {
        refreshUI(null);
        if (PreferencesUtils.getBoolean(BaseApp.getContext(), KEY.FLAG_ISFIRST_PERSONAL, true)) {
            PreferencesUtils.putBoolean(BaseApp.getContext(), KEY.FLAG_ISFIRST_PERSONAL, false);
            MyHeadPortrait.post(new Runnable() {
                @Override
                public void run() {
                    int[] location = new int[2];
                    MyHeadPortrait.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
                    showGuide(x, y, MyHeadPortrait.getHeight(), width);
                }
            });
        }
    }

    private void showGuide(float x, float y, final int height, final int width) {
        //第一次登陆，当切换到个人中心时，显示蒙版引导
        final AlertDialog ad = new AlertDialog.Builder(getContext(), R.style.Dialog_Fullscreen).create();// 创建
        ad.setCanceledOnTouchOutside(false);
        ad.setCancelable(true);
        // 显示对话框
        ad.show();
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = ad.getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        ad.getWindow().setAttributes(lp);
        Window window = ad.getWindow();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setContentView(R.layout.dialog_guide_personal);

        ImageView ivNext = (ImageView) window.findViewById(R.id.iv_next);
        ImageView ivDetails = (ImageView) window.findViewById(R.id.iv_details);
        ivDetails.setX(x);
        ivDetails.setY(y);
        ivDetails.setMaxHeight(height);
        ivDetails.setMaxWidth(width);
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
    }

    @OnClick({R.id.MyHeadPortrait, R.id.mCompany, R.id.mCollect, R.id.mAchievement, R.id.mSetting})
    public void onClick(View view) {
        switch (view.getId()) {
            //个人中心
            case R.id.MyHeadPortrait:
                startActivity(new Intent(getActivity(), MemberCenterActivity.class));
                break;
            //我的企业
            case R.id.mCompany:
                Intent intent = new Intent(getActivity(), CompanyActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
//                startActivity(new Intent(BaseApp.getContext(), GuaranteeActivity.class));
                break;
            //收藏
            case R.id.mCollect:
                break;
            //成就
            case R.id.mAchievement:
                break;
            //设置
            case R.id.mSetting:
                startActivity(new Intent(getActivity(), SettingActivity.class));

                break;


        }
    }

    //
    private ZBXAlertDialog zbxAlertDialog;

    // 刷新界面
    @Subscriber
    private void refreshUI(EventMember event) {
//        GetUserLoginUserInfo();
        if (StringUtils.isEmpty(BaseApp.mUserEntity.getHeadImgUrl())) {
        } else {
            String mBaseUrl = ConfigUtils.getConfig(ConfigUtils.KEY.webServer);
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                    //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                    .build();                                   // 创建配置过得DisplayImageOption对象
            ImageLoader.getInstance().displayImage(BaseApp.mUserEntity.getHeadImgUrl(), mPortrait, options);
        }
        if (!StringUtils.isEmpty(BaseApp.mUserEntity.getRealName())) {
            mRemarkName.setText(BaseApp.mUserEntity.getRealName());
        }
        if (!StringUtils.isEmpty(BaseApp.mUserEntity.getPhone())) {
            mPhonenumber.setText(BaseApp.mUserEntity.getPhone());
        }
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {
        refreshUI(null);
    }

    //获取数据
    public void GetUserLoginUserInfo() {
        Call call = HttpRequest.getIResource().GetUserLoginUserInfo(PreferencesUtils.getString(getActivity(), KEY.FLAG_TOOKENID));
        callRequest(call, new HttpCallBack(MemberCenterEntity.class, getActivity(), false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    MemberCenterEntity entity = (MemberCenterEntity) mResult.getData();
                    userId = entity.getUserId();
                    if (StringUtils.isEmpty(entity.getHeadImgUrl())) {
                    } else {
                        String mBaseUrl = ConfigUtils.getConfig(ConfigUtils.KEY.webServer);
                        DisplayImageOptions options = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                                //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                                .build();                                   // 创建配置过得DisplayImageOption对象
                        ImageLoader.getInstance().displayImage(entity.getHeadImgUrl(), mPortrait, options);
                    }
                    if (!StringUtils.isEmpty(entity.getRealName())) {
                        mRemarkName.setText(entity.getRealName());
                    }
                    if (!StringUtils.isEmpty(entity.getPhone())) {
                        mPhonenumber.setText(entity.getPhone());
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

    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (MemberCenterActivity.SUCCESS.equals(eventCustom.getTag())) {
            refreshUI(null);
        }

    }
}

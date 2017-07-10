package com.zbxn.main.activity.contacts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.base.BaseActivity;
import com.pub.common.EventCustom;
import com.pub.dialog.MessageDialog;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.ScreenUtils;
import com.pub.utils.StringUtils;
import com.pub.widget.pulltozoomview.PullToZoomScrollViewEx;
import com.zbxn.R;
import com.zbxn.main.entity.ContactsDetailEntity;
import com.zbxn.main.entity.ContactsEntity;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * 联系人详情
 *
 * @author Wuzy
 * @time 2016/8/10
 */
public class ContactsDetailActivity extends BaseActivity {

    public static final String Flag_Input_Contactor = "contactor";

    @BindView(R.id.mScrollView)
    PullToZoomScrollViewEx mScrollView;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.ll_company)
    LinearLayout llCompany;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_achieve)
    TextView tvAchieve;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_add_friend)
    TextView tvAddFriend;


    private ContactsEntity mContacts;
    public static final String SUCCESS = "ContactsDeleteSuccess";
    private ContactsDetailEntity mContactsDetail;
    private boolean isFriend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_detail);

        //隐藏toolbar
        hideToolbar();

        ButterKnife.bind(this);
        mContacts = (ContactsEntity) getIntent().getSerializableExtra(Flag_Input_Contactor);
        isFriend = getIntent().getBooleanExtra("isFriends", false);
        if (mContacts == null) {
            finish();
        }

        initView();
        setDataUI();

        GetUserInfo();




    }

    private void setDataUI() {
        tvName.setText(mContacts.getUserName());

//        String mBaseUrl = ConfigUtils.getConfig(ConfigUtils.KEY.webServer);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                .build();                                   // 创建配置过得DisplayImageOption对象
        ImageLoader.getInstance().displayImage(mContacts.getHeadImgUrl(), imgHead, options);

    }

    @OnClick({R.id.ll_email, R.id.ll_phone, R.id.img_back,
            R.id.tv_achieve, R.id.tv_message, R.id.tv_add_friend
            , R.id.tv_phone, R.id.tv_email
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_email:
            case R.id.tv_email:
                if (!TextUtils.isEmpty(mContactsDetail.getEmail())) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setType("text/plain");
                        intent.setData(Uri.parse("mailto:" + mContactsDetail.getEmail()));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "邮件标题");
                        intent.putExtra(Intent.EXTRA_TEXT, "邮件内容");
                        startActivity(intent);
                    } catch (Exception e) {
//                        ZBXAlertDialog dialog = new ZBXAlertDialog(ContactsDetailActivity.this, new ZBXAlertListener() {
//                            @Override
//                            public void onDialogOk(Dialog dlg) {
//                                dlg.dismiss();
//                            }
//
//                            @Override
//                            public void onDialogCancel(Dialog dlg) {
//                                dlg.dismiss();
//                            }
//                        }, "", "邮箱地址有误");
//                        dialog.setCancelGone();
//                        dialog.show();
                    }
                }
                break;
            case R.id.ll_phone:
            case R.id.tv_phone:
                if (!TextUtils.isEmpty(mContactsDetail.getPhone())) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + mContactsDetail.getPhone());
                    intent.setData(data);
                    startActivity(intent);
//                    showCallDialog(mContacts.getTelephone());
                } else {
                    MyToast.showToast("电话号码不存在");
                }
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_achieve:

                break;
            case R.id.tv_message:

                break;
            case R.id.tv_add_friend:
                if (isFriend) {
                    DeleteFriendRel(this, mContacts.getUserId(), true);
                } else {
                    AddFriendRequest(this, mContacts.getUserId(), true);
                }

                break;
        }
    }

    private void initView() {
        // 调整ZoomView的高度
        int screenWidth = ScreenUtils.getScreenWidth(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                screenWidth, (int) (screenWidth * 9 / 16));
        mScrollView.setHeaderLayoutParams(params);
    }

    //获取数据
    private void GetUserInfo() {
        Call call = HttpRequest.getIResource().GetUserInfo(mContacts.getUserId());
        callRequest(call, new HttpCallBack(ContactsDetailEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    mContactsDetail = (ContactsDetailEntity) mResult.getData();
                    setData();
                } else {
                    MyToast.showToast(mResult.getMsg());
                    finish();
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }

    private void setData() {
        tvName.setText(mContactsDetail.getRealName());

//        String mBaseUrl = ConfigUtils.getConfig(ConfigUtils.KEY.webServer);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                .build();                                   // 创建配置过得DisplayImageOption对象
        ImageLoader.getInstance().displayImage(mContactsDetail.getHeadImgUrl(), imgHead, options);

        tvPhone.setText(mContactsDetail.getPhone());
        tvEmail.setText(mContactsDetail.getEmail());
        tvBirthday.setText(StringUtils.convertDate(mContactsDetail.getBirthDayStr()));
//        tvCompany.setText(mContactsDetail.get());
        llCompany.setVisibility(View.GONE);
        tvAddress.setText(mContactsDetail.getAddress());
        if (isFriend) {
            tvAddFriend.setText("删除好友");
        } else {
            tvAddFriend.setText("添加好友");
        }
    }

    /**
     * 请求添加好友
     */
    public void AddFriendRequest(Context context, String ToUserId, boolean isShowProgress) {
        Call call = HttpRequest.getIResource().AddFriendRequest(mContactsDetail.getPhone(), "1");
        callRequest(call, new HttpCallBack(String.class, context, isShowProgress) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//0成功
                    MyToast.showToast("好友请求已发送，等待对方同意");
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
     * 删除好友
     */
    public void DeleteFriendRel(Context context, String ToUserId, boolean isShowProgress) {
        Call call = HttpRequest.getIResource().DeleteFriendRel(ToUserId);
        callRequest(call, new HttpCallBack(String.class, context, isShowProgress) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//0成功
                    MyToast.showToast("好友删除成功");
                    EventCustom eventCustom = new EventCustom();
                    eventCustom.setTag(ContactsDetailActivity.SUCCESS);
                    EventBus.getDefault().post(eventCustom);
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

//
//    private void setClipBorad(TextView textView) {
//        ClipboardManager cmb = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
//        cmb.setText(textView.getText().toString().trim());
//        MyToast.showToast("复制文本成功");
//    }
//

    /**
     * 显示拨打电话提示对话框
     *
     * @param number
     */
    private void showCallDialog(final String number) {
        MessageDialog dialog = new MessageDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("确定拨打 " + number + "?");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + number);
                intent.setData(data);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("取消");
        dialog.show();
    }


}

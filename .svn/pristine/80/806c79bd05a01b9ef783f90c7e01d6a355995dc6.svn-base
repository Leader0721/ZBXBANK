package com.zbxn.main.activity.memberCenter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.dialog.InputInfoDialog;
import com.pub.dialog.InputInfoListener;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.http.uploadfile.BaseAsyncTaskFile;
import com.pub.http.uploadfile.BaseAsyncTaskInterface;
import com.pub.utils.ConfigUtils;
import com.pub.utils.DemoUtils;
import com.pub.utils.FileAccessor;
import com.pub.utils.KEY;
import com.pub.utils.MyToast;
import com.pub.utils.PreferencesUtils;
import com.pub.utils.StringUtils;
import com.pub.utils.ValidationUtil;
import com.pub.widget.dbutils.DBUtils;
import com.pub.widget.slidedatetimepicker.NewSlideDateTimeDialogFragment;
import com.pub.widget.slidedatetimepicker.SlideDateTimeListener;
import com.pub.widget.slidedatetimepicker.SlideDateTimePicker;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.activity.popupwindow.PopupWindowSelectPic;
import com.zbxn.main.entity.FileHeadEntity;
import com.zbxn.main.entity.MemberCenterEntity;
import com.zbxn.main.entity.UserEntity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

import static com.pub.utils.StringUtils.getEditText;

public class MemberCenterActivity extends BaseActivity {


    public static final String SUCCESS = "MemberCenterSuccess";
    @BindView(R.id.mPortrait)
    CircleImageView mPortrait;
    @BindView(R.id.mSetphoto)
    TextView mSetphoto;
    @BindView(R.id.tv_branch1)
    TextView tvBranch1;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.layout_mName)
    LinearLayout layoutMName;
    @BindView(R.id.tv_branch2)
    TextView tvBranch2;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.layout_account)
    RelativeLayout layoutAccount;
    @BindView(R.id.tv_branch3)
    TextView tvBranch3;
    @BindView(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.layout_phoneNumber)
    RelativeLayout layoutPhoneNumber;
    @BindView(R.id.tv_branch4)
    TextView tvBranch4;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.layout_email)
    LinearLayout layoutEmail;
    @BindView(R.id.tv_branch5)
    TextView tvBranch5;
    @BindView(R.id.tv_companyNmae)
    TextView tvCompanyNmae;
    @BindView(R.id.imageView7)
    ImageView imageView7;
    @BindView(R.id.layout_companyNmae)
    RelativeLayout layoutCompanyNmae;
    @BindView(R.id.tv_branch6)
    TextView tvBranch6;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.imageView9)
    ImageView imageView9;
    @BindView(R.id.layout_birthday)
    LinearLayout layoutBirthday;
    @BindView(R.id.tv_branch7)
    TextView tvBranch7;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.imageView10)
    ImageView imageView10;
    @BindView(R.id.layout_address)
    LinearLayout layoutAddress;
    @BindView(R.id.activity_member_center)
    LinearLayout activityMemberCenter;
    private DBUtils<UserEntity> mDBUtils;
    private InputInfoDialog inputDialog;
    private ArrayList<String> messageList;
    private String title;
    private String dialogValue;
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    private String myBirthday;
    private FileHeadEntity entity;
    private String headImgUrl;
    private String realName;
    private String phone;
    private String email;
    private String dateTime;
    private String address;
    private String dateTime1;
    private String nickName;
    private String fileNameTag = "";
    private File fileSmall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_center);
        ButterKnife.bind(this);

//        Log.d("====AAAAAAA====",StringUtils.getGuid());
        verifyStoragePermissions(this);
        refreshUI();
    }

    @OnClick({R.id.mSetphoto, R.id.layout_mName, R.id.layout_account,
            R.id.layout_phoneNumber, R.id.layout_companyNmae
            , R.id.layout_birthday, R.id.layout_address, R.id.layout_email, R.id.mPortrait
            , R.id.tv_name, R.id.tv_email, R.id.tv_address, R.id.tv_birthday
    })
    public void onClick(View view) {
        switch (view.getId()) {
            //设置头像
            case R.id.mPortrait:
            case R.id.mSetphoto:
                PopupWindowSelectPic menuWindow = new PopupWindowSelectPic(MemberCenterActivity.this, listenerPic);
                //显示窗口，并设置窗口显示的位置
                menuWindow.showAtLocation(MemberCenterActivity.this.findViewById(R.id.mPortrait), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            //设置名字
            case R.id.layout_mName:
            case R.id.tv_name:
                getDialogValues(0);
                break;
            //设置生日
            case R.id.layout_birthday:
            case R.id.tv_birthday:
                /**
                 * 日期 选择器
                 */
                String time = getEditText(tvBirthday);
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(new SlideDateTimeListener() {
                            @Override
                            public void onDateTimeSet(Date date) {
                                dateTime = format1.format(date);
                                ChangeUserInfo();
                            }
                        })
                        .setInitialDate(StringUtils.convertToDate(format1, time))
                        .setIs24HourTime(true)
                        .setIsHaveTime(NewSlideDateTimeDialogFragment.Have_Date)
                        .build()
                        .show();
                break;
            //设置地址
            case R.id.layout_address:
            case R.id.tv_address:
                getDialogValues(1);
                break;
            //设置邮箱
            case R.id.layout_email:
            case R.id.tv_email:
                getDialogValues(2);
                break;

        }
    }

    // 刷新界面
    @Subscriber
    private void refreshUI() {

        //放置姓名等基本信息
        GetUserLoginUserInfo();
    }

    //获取详情
    public void GetUserLoginUserInfo() {
        messageList = new ArrayList<>();
        String tokenid = PreferencesUtils.getString(this, KEY.FLAG_TOOKENID);
        Call call = HttpRequest.getIResource().GetUserLoginUserInfo(tokenid);
        callRequest(call, new HttpCallBack(MemberCenterEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    MemberCenterEntity entity = (MemberCenterEntity) mResult.getData();
                    realName = entity.getRealName();
                    nickName = entity.getNickName();
                    tvName.setText(realName);
                    BaseApp.mUserEntity.setRealName(realName);
                    if (entity.getPhone() == null) {
                        tvPhoneNumber.setText("");
                    } else {
                        phone = entity.getPhone();
                        tvPhoneNumber.setText(phone);
                        BaseApp.mUserEntity.setPhone(phone);
                    }
                    if (entity.getEmail() == null) {
                        tvEmail.setText("");
                    } else {
                        email = entity.getEmail();
                        tvEmail.setText(email);
                    }
                    if (entity.getBirthDay() == null) {
                        tvBirthday.setText("");
                    } else {
                        dateTime = entity.getBirthDayStr();
                        tvBirthday.setText(dateTime.substring(0, 10));
                    }
                    if (entity.getAddress() == null) {
                        tvAddress.setText("");
                    } else {
                        address = entity.getAddress();
                        tvAddress.setText(address);
                    }
                    if (entity.getHeadImgUrl() == null) {

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
                        headImgUrl = entity.getHeadImgUrl();
                        ImageLoader.getInstance().displayImage(headImgUrl, mPortrait, options);
                        BaseApp.mUserEntity.setHeadImgUrl(headImgUrl);
                    }
                    messageList.add(realName);
                    messageList.add(address);
                    messageList.add(email);
                    EventCustom eventCustom = new EventCustom();
                    eventCustom.setTag(SUCCESS);
                    EventBus.getDefault().post(eventCustom);
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

    //修改详情
    public void ChangeUserInfo() {
        Call call = HttpRequest.getIResource().ChangeUserInfo(headImgUrl, realName, address, nickName, dateTime, email);
        callRequest(call, new HttpCallBack(MemberCenterEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    refreshUI();
                    MyToast.showToast("修改成功");
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
     * 选择图片拍照路径
     */
    private String mFilePath;
    /**
     * request code for tack pic
     */
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x3;
    public static final int REQUEST_CODE_LOAD_IMAGE = 0x4;
    private ICustomListener listenerPic = new ICustomListener() {
        @Override
        public void onCustomListener(int obj0, Object obj1, int position) {
            switch (obj0) {
                case 0:
                    //调用照相机
                    // 扫描功能
                    if (ContextCompat.checkSelfPermission(MemberCenterActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //申请CAMERA权限
                        ActivityCompat.requestPermissions(MemberCenterActivity.this, new String[]{Manifest.permission.CAMERA}, 3);
                    } else {
                        if (!FileAccessor.isExistExternalStore()) {
                            return;
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File file = FileAccessor.getTackPicFilePath();
                        if (file != null) {
                            Uri uri = null;
                            if (Build.VERSION.SDK_INT >= 24) {
                                uri = getImageContentUri(file);
                            } else {
                                uri = Uri.fromFile(file);
                            }
                            if (uri != null) {
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            }
                            mFilePath = file.getAbsolutePath();
                        }
                        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
                    }

                    break;
                case 1:
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, REQUEST_CODE_LOAD_IMAGE);
                    break;
            }
        }
    };

    /**
     * 转换 content:// uri
     *
     * @param imageFile
     * @return
     */
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public File downloadFile(Context context, String filePath, Bitmap bitmap1) {
        String state = Environment.getExternalStorageState();
        File file;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            file = context.getExternalCacheDir();
//            file = getSDPath();
        } else {
            file = context.getCacheDir();
        }
        File newFile = new File(file.getAbsolutePath() + filePath);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        //华为荣耀bug(jira编号:HBB-3202):如果在外部缓存目录创建目录失败,则直接使用内部缓存目录
        if (!newFile.exists()) {
            newFile = new File(context.getCacheDir().getAbsolutePath() + filePath);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
        }
        fileNameTag = getFileName() + ".png";
        File tmpFile = new File(newFile, fileNameTag);
        try {
            tmpFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(tmpFile);
            bitmap1.compress(Bitmap.CompressFormat.PNG, 50, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            MyToast.showToast("修改失败");
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
            MyToast.showToast("修改失败");
        }
        return tmpFile;
    }

    public static String getCharacterAndNumber() {
        String rel = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        rel = formatter.format(curDate);
        return rel;
    }

    public static String getFileName() {
        String fileNameRandom = getCharacterAndNumber();
        return fileNameRandom;
    }

    //  进行头像裁剪完之后的处理
    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (eventCustom.getTag().equals(EventKey.ClipImage)) {
            Bitmap cropped = AppController.cropped;
            fileSmall = downloadFile(this, "", cropped);
            uploadFile(MemberCenterActivity.this, fileSmall);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         /*回调内容*/
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_TAKE_PICTURE
                || requestCode == REQUEST_CODE_LOAD_IMAGE) {
            if (requestCode == REQUEST_CODE_LOAD_IMAGE) {
                mFilePath = DemoUtils.resolvePhotoFromIntent(this, data, FileAccessor.IMESSAGE_IMAGE);
            }
            if (TextUtils.isEmpty(mFilePath)) {
                return;
            }
            File file = new File(mFilePath);
            if (file == null || !file.exists()) {
                return;
            }
            fileSmall = null;
            String filePathStr = "";
            try {
                filePathStr = FileAccessor.getSmallPicture(file.getPath()); // 压缩文件
                fileSmall = new File(filePathStr);
            } catch (Exception e) {
                System.out.println("上传图片错误：" + e.toString());
                e.printStackTrace();
                fileSmall = file;
            }
            Intent intent = new Intent(MemberCenterActivity.this, ClipImageViewActivity.class);
            intent.putExtra("download", false);
            intent.putExtra("delete", false);
            intent.putExtra("path", filePathStr);
            intent.putExtra("position", 0);
            startActivity(intent);
            return;
        }
    }

    //请求权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (3 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //授权
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = FileAccessor.getTackPicFilePath();
                if (file != null) {
                    Uri uri = Uri.fromFile(file);
                    if (uri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    }
                    mFilePath = file.getAbsolutePath();
                }
                startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
            } else {
                //未授权
                MyToast.showToast("请到设置中开启照相机权限");
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (inputDialog != null) {
            if (inputDialog.isShowing()) {
                inputDialog.dismiss();
            }
        }
        super.onDestroy();
    }

    /**
     * 个人中心中
     * 上传更改后的文件
     *
     * @param context
     * @param file
     */
    public void uploadFile(Context context, final File file) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("width", "120");
        map.put("height", "120");
        map.put("x", "120");
        map.put("y", "120");
        map.put("Cut", "0");

        Map<String, File> mapFile = new HashMap<>();
        mapFile.put("avatar", file);

        String server = ConfigUtils.getConfig(ConfigUtils.KEY.server);
        new BaseAsyncTaskFile(context, true, 0, server + "file/ImgCrop", new BaseAsyncTaskInterface() {
            @Override
            public void dataSubmit(int funId) {

            }

            @Override
            public Object dataParse(int funId, String json) throws Exception {
                return new ResultData<FileHeadEntity>().parse(json, FileHeadEntity.class);
            }

            @Override
            public void dataSuccess(int funId, Object result) {
                ResultData mResult = (ResultData) result;
                if ("1".equals(mResult.getSuccess())) {//0成功
                    entity = (FileHeadEntity) mResult.getData();
                    headImgUrl = entity.getVisitedUrl();
                    ChangeUserInfo();
                } else {
                    String message = mResult.getMsg();
                    MyToast.showToast(message);
                }
            }

            @Override
            public void dataError(int funId) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        }, mapFile).execute(map);
    }

    @Override
    public void initRight() {
        super.initRight();
        setTitle("个人信息");
    }

    public void getDialogValues(final int position) {
        switch (position) {
            case 0:
                title = "修改姓名";
                dialogValue = StringUtils.getEditText(tvName);
                break;
            case 1:
                title = "修改地址";
                dialogValue = StringUtils.getEditText(tvAddress);
                break;
            case 2:
                title = "修改邮箱";
                dialogValue = StringUtils.getEditText(tvEmail);
                break;
            default:
                break;
        }
        inputDialog = new InputInfoDialog(this, new InputInfoListener() {
            @Override
            public void onDialogOk(Dialog dlg, Object param) {
                String content = param.toString();
                if (content.length() > 0) {
                    if (messageList.size() > 0) {
                        if (content.equals(messageList.get(position))) {
//                            MyToast.showToast("你输入的已存在");
//                            return;
                            inputDialog.dismiss();
                        } else {
                            switch (position) {
                                case 0:
                                    if (content.length() > 20) {
                                        MyToast.showToast("输入姓名长度超过限制");
                                        return;
                                    } else {
                                        realName = content;
                                    }
                                    break;
                                case 1:
                                    address = content;
                                    break;
                                case 2:
                                    if (ValidationUtil.isMailbox(content.trim())) {
                                        email = content;
                                    } else {
                                        MyToast.showToast("填写的邮箱格式有误");
                                        return;
                                    }
                                    break;
                            }
                            ChangeUserInfo();
                            inputDialog.dismiss();
                        }
                    } else {
                        //如果size为0，则说明网络请求没有获取到数据
                        MyToast.showToast("请检查登录状态");
                        inputDialog.dismiss();
                    }

                } else {
                    MyToast.showToast("修改内容不能为空");
                }
            }

            @Override
            public void onDialogCancel(Dialog dlg, Object param) {

            }
        }, title, "");
        inputDialog.setTitle(title);
        inputDialog.setInputValue(dialogValue);
        inputDialog.show();
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CRMERA = 2;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 3;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void verifyStoragePermissions(Activity activity) {
        if (!permissionIsOpen(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    public boolean permissionIsOpen(Activity activity, String permission) {
        // Check if we have write permission
        int per = ActivityCompat.checkSelfPermission(activity, permission);
        if (per == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
}

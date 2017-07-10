package com.zbxn.main.activity.credit;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pub.base.BaseActivity;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.dialog.ZBXAlertDialog;
import com.pub.dialog.ZBXAlertListener;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.http.uploadfile.BaseAsyncTaskFile;
import com.pub.http.uploadfile.BaseAsyncTaskInterface;
import com.pub.popupwindow.PopupWindowHelper;
import com.pub.utils.ConfigUtils;
import com.pub.utils.DemoUtils;
import com.pub.utils.FileAccessor;
import com.pub.utils.JsonUtil;
import com.pub.utils.MyToast;
import com.pub.utils.PreferencesUtils;
import com.pub.utils.StringUtils;
import com.pub.utils.WaterMarkUtils;
import com.pub.widget.MyListView;
import com.pub.widget.fileselector.FileSelector;
import com.pub.widget.fileselector.FileSelectorActivity;
import com.pub.widget.fileselector.config.FileConfig;
import com.pub.widget.fileselector.utils.FileUtils;
import com.pub.widget.multi_image_selector.MultiImageSelector;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.activity.steward.PhotoDetailActivity;
import com.zbxn.main.adapter.AdjunctAdapter;
import com.zbxn.main.adapter.EnterpriseInfoAdapter;
import com.zbxn.main.adapter.PopupListAdapter;
import com.zbxn.main.entity.AdjunctEntity;
import com.zbxn.main.entity.AttachmentBindingEntity;
import com.zbxn.main.entity.AttachmentEntity;
import com.zbxn.main.entity.AttachmentGetEntity;
import com.zbxn.main.entity.BankSelectEntity;
import com.zbxn.main.entity.FileEntity;

import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * 上传附件的类，获取数据后建议实体转为AdjunctEntity,以方便操作
 *
 * @author: ysj
 * @date: 2017-03-16 15:39
 */
public class AdjunctActivity extends BaseActivity {

    public static final int REQUEST_CODE_TAKE_PICTURE = 0x3;
    public static final int REQUEST_CODE_LOAD_IMAGE = 0x4;
    //获取图片
    private static final int REQUEST_IMAGE = 2002;
    private static final int REQUEST_IMAGE_DETAILS = 2003;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    @BindView(R.id.iv_callphone)
    ImageView ivCallphone;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.mGridView)
    GridView mGridView;
    @BindView(R.id.mGridViewShow)
    GridView mGridViewShow;
    @BindView(R.id.ll_nodata)
    LinearLayout ll_nodata;
    @BindView(R.id.rl_nodata)
    RelativeLayout rl_nodata;
    private AdjunctAdapter mAdapter;
    private List<AdjunctEntity> mList;
    private ArrayList<AdjunctEntity.Attachment> imgList;
    private List<File> files = null;
    private boolean isOne;

    //图片路径
    private String mFilePath;
    //图片路径的集合
    private ArrayList<String> mSelectPath;

    private FileConfig fileConfig;
    //记录点击的第几个item
    private int imgPosition;
    private boolean show = false;

    /*这个是进行企业资料的展示*/
    private List<AttachmentGetEntity> mListShow;
    private EnterpriseInfoAdapter mAdapterShow;
    private List<File> fileList;

    private String loanApplyId;
    private String versionId;
    private String formInfo;
    private AttachmentGetEntity entity;
    private Map<String, String> fileIdMap = new HashMap<>();

    private int step = 5;
    private String formString;
    private PopupWindowHelper mHelper;
    private View popView;
    //当前申请单是否添加过附件
    private boolean isSubmit;
    //记录点击的按钮是否是保存
    private boolean isSave;
    //记录图片是否多选
    private boolean isMoreSelect;
    //记录是否是拍照
    private boolean isCamera;
    //重新上传时是否显示dialog
    private boolean isRestart;
    private ZBXAlertDialog dialog;
    private String mPhone = "";
    //申请单状态
    private int mStatusCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_adjunct);
        ButterKnife.bind(this);
        getToolbarHelper().setShadowEnable(false);
        show = getIntent().getBooleanExtra("show", false);
        loanApplyId = getIntent().getStringExtra("LoanApplyId");
        versionId = getIntent().getStringExtra("VersionId");
        mPhone = getIntent().getStringExtra("Phone");
        mStatusCode = getIntent().getIntExtra("StatusCode", 0);
        isSubmit = PreferencesUtils.getBoolean(this, loanApplyId, false);
        isOne = false;
        verifyStoragePermissions(this);

        if (show) {
            setTitle("企业资料");
            ivCallphone.setVisibility(View.VISIBLE);
            rl_nodata.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.GONE);
            mGridViewShow.setVisibility(View.VISIBLE);
            mGridViewShow.setEmptyView(ll_nodata);
        } else {
            setTitle("上传附件");
            initData();
            if (mStatusCode != 2 && mStatusCode != 1) {
                GetAttachmentBindingId();
            }
            rl_nodata.setVisibility(View.GONE);
            ivCallphone.setVisibility(View.GONE);
            mGridViewShow.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);
            fileConfig = new FileConfig();
        }
        initView();
        getAttachment();
    }

    @Override
    public void initRight() {
        super.initRight();
        if (show) {
            setRight1Show(false);
        } else {
            setRight1Icon(R.mipmap.my_more);
            setRight1("更多");
            if (mStatusCode == 2 || mStatusCode == 1) {//已通过
                setRight1Show(false);
            } else {
                setRight1Show(true);
            }
        }
    }

    @Override
    public void actionRight1(MenuItem menuItem) {
        super.actionRight1(menuItem);
        //加载PopupWindow布局
        popView = LayoutInflater.from(this).inflate(R.layout.popupview, null);
        //写在PopupWindowHelper实例化之前，避免出现listview的高度为第一个item的高度的问题
        List<String> list = new ArrayList<>();
        list.add("提交");
        list.add("保存");
        PopupListAdapter adapter = new PopupListAdapter(this, list);
        MyListView myListView = (MyListView) popView.findViewById(R.id.mListView);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://提交
                        isSave = false;
                        if (isSubmit()) {
                            addAttachment();
                        } else {
                            MyToast.showToast("当前存在未上传的文件，请先上传");
                        }
                        mHelper.dismiss();
                        break;
                    case 1://保存
                        isSave = true;
                        formString = JsonUtil.toJsonString(fileIdMap);
                        formString = formString.replaceAll("\\s+", "");//去掉空格、换行符等
                        if (isSubmit) {
                            addAttachment();
                        } else {
                            AddDraft();
                        }
                        mHelper.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
        mHelper = new PopupWindowHelper(this, popView, getWindow());
        mHelper.showAsDropDown(tvMore, -50, 0);
    }

    private void initData() {
        //初始化数据
        mList = new ArrayList<>();
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, true, "营业执照(上传)", "BusinessLicense", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, true, "开户许可证(上传)", "AccountOpeningPermit", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, false, "税务登记证(上传)", "TaxCertificate", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, true, "法人代表身份证(上传)", "CorporationIDCard", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, true, "财务负责人身份证(上传)", "FinanceIDCard", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, false, "股东身份证(上传)", "PartnerIDCard", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, true, "财务报表(上传)", "FinanceReport", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, true, "企业章程(上传)", "EnterpriseArticles", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, false, "验资证明(上传)", "CapitalVerification", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, false, "土地租赁协议/房产证明(上传)", "HouseProperty", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, true, "企业变更(上传)", "EnterpriseHistory", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, false, "项目可行性研究报告(上传)", "FeasibilityStudy", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, false, "相关部门许可证明(上传)", "OtherApproval", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, false, "组织机构代码证(上传)", "OrganizationCode", "", true));
        mList.add(new AdjunctEntity(new ArrayList<AdjunctEntity.Attachment>(), null, false, true, "机构信用代码证(上传)", "CreditCode", "", true));
        imgList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            fileIdMap.put(mList.get(i).getFileNameStr(), null);
        }
    }

    private void initView() {
        mAdapter = new AdjunctAdapter(this, mList, listener, mStatusCode);
        if (show) {
            mGridViewShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mListShow.get(position).getAttachment().size() != 0) {
                        if (!StringUtils.isEmpty(mListShow.get(position).getAttachment().get(0).getFilePath())) {
                            ArrayList<String> list_Ads = new ArrayList<>();

                            if (mListShow.get(position).getAttachment().size() == 1) {
//                        这个是对于单图的时候
                                String img = mListShow.get(position).getAttachment().get(0).getFilePath();
                                list_Ads.add(img);
                            } else {
                                //                        这个是对于多图的时候
                                for (int i = 0; i < mListShow.get(position).getAttachment().size(); i++) {
                                    list_Ads.add(mListShow.get(position).getAttachment().get(i).getFilePath());
                                }
                            }
                            if (list_Ads.size() == 0) {
                                MyToast.showToast("请上传图片");
                            } else {
                                Intent intent = new Intent(AdjunctActivity.this, PhotoDetailActivity.class);
                                intent.putExtra("file", true);
                                intent.putExtra("download", true);
                                intent.putExtra("delete", false);
                                intent.putExtra("list", list_Ads);
                                intent.putExtra("position", 0);
                                startActivity(intent);
                            }
                        } else {
                            startActivity(new Intent(AdjunctActivity.this, AdjunctActivity.class));
                        }
                    } else {
                    }
                }
            });
        } else {
            mGridView.setAdapter(mAdapter);
            //ToolBar 返回键监听 写在post里面吧，否则获取不到点击事件
            getToolbarHelper().getToolBar().post(new Runnable() {
                @Override
                public void run() {
                    getToolbarHelper().getToolBar().setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mStatusCode == 2 || mStatusCode == 1) {//如果申请单状态为已通过或待审核
                                finish();
                            } else {
                                back();
                            }
                        }
                    });
                }
            });
        }
    }

    public void back() {
        dialog = new ZBXAlertDialog(AdjunctActivity.this, new ZBXAlertListener() {
            @Override
            public void onDialogOk(Dialog dlg) {
                if (isSubmit()) {
                    addAttachment();
                } else {
                    MyToast.showToast("当前存在未上传的文件，请先上传");
                }
                dlg.dismiss();
            }

            @Override
            public void onDialogCancel(Dialog dlg) {
                finish();
            }
        }, "提示", "当前有未提交文件，是否提交审核");
        if (!isFileNull()) {//文件列表不为空
            dialog.show();
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
                return;
            }
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止窗体溢出
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!show) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (mStatusCode == 2 || mStatusCode == 1) {
                    finish();
                } else {
                    back();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 是否未上传任何文件
     *
     * @return
     */
    public boolean isFileNull() {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isUpload()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证是否可提交
     *
     * @return
     */
    public boolean isSubmit() {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isMust()) {//是否必传
                if (!mList.get(i).isUpload()) {//是否已提交
                    return false;
                }
            }
        }
        return true;
    }


    private ICustomListener listener = new ICustomListener() {
        @Override
        public void onCustomListener(int obj0, Object obj1, int position) {
            imgPosition = position;
            if (imgList != null) {
                imgList.clear();
            }
            if (mList.get(imgPosition).isMore()) {
                isMoreSelect = true;
            } else {
                isMoreSelect = false;
            }
            switch (obj0) {
                case 0://图片主体
                    if (mList.get(imgPosition).isUpload()) {//已设置的在这看详情
//                        if (mList.get(imgPosition).getFileType().equals("png") || mList.get(imgPosition).getFileType().equals("jpg") || mList.get(imgPosition).getFileType().equals("jpeg")) {//如果是图片及拍照的，打开大图
//                            imgList.clear();
//                            for (int i = 0; i < mList.get(position).getFileList().size(); i++) {
//                                AdjunctEntity.Attachment attachment = new AdjunctEntity.Attachment();
//                                attachment.setFileUrl(mList.get(position).getFileList().get(i).getFileUrl());
//                                attachment.setFileId(mList.get(position).getFileList().get(i).getFileId());
//                                attachment.setDownloadPath(mList.get(position).getFileList().get(i).getDownloadPath());
//                                imgList.add(attachment);
//                            }
//                            Intent intent = new Intent(AdjunctActivity.this, PhotoDetailActivity.class);
//                            intent.putParcelableArrayListExtra("lists", imgList);
//                            intent.putExtra("file", true);
//                            intent.putExtra("position", 0);
//                            intent.putExtra("download", true);
//                            intent.putExtra("delete", true);
//                            intent.putExtra("state", mStatusCode);
//                            startActivity(intent);
//                        } else {//打开文件
                            if (!StringUtils.isEmpty(mList.get(imgPosition).getFileList())) {
                                imgList.clear();
                                for (int i = 0; i < mList.get(imgPosition).getFileList().size(); i++) {
                                    AdjunctEntity.Attachment attachment = new AdjunctEntity.Attachment();
                                    attachment.setFileUrl(mList.get(position).getFileList().get(i).getFileUrl());
                                    attachment.setFileId(mList.get(position).getFileList().get(i).getFileId());
                                    attachment.setFileName(mList.get(position).getFileList().get(i).getFileName());
                                    attachment.setDownloadPath(mList.get(position).getFileList().get(i).getDownloadPath());
                                    imgList.add(attachment);
                                }
                                Intent intent = new Intent(AdjunctActivity.this, PhotoDetailActivity.class);
                                intent.putParcelableArrayListExtra("lists", imgList);
                                intent.putExtra("file", true);
                                intent.putExtra("position", 0);
                                intent.putExtra("download", true);
                                intent.putExtra("delete", true);
                                intent.putExtra("state", mStatusCode);
                                startActivity(intent);
                            } else {
                                MyToast.showToast("该文件不存在");
                                Log.d("open file", "url --> null");
                            }
//                        }
                    } else {//未设置的在这设置
                        boolean isDetail = (boolean) obj1;
                        if (isDetail) {//如果是只查看，不做处理
                        } else {//上传
                            //财务报表 文件选择
                            if (mList.get(imgPosition).getFileNameStr().equals("FinanceReport")) {
                                isCamera = false;
                                isMoreSelect = false;
                                Intent intent = new Intent(getApplicationContext(), FileSelectorActivity.class);
                                fileConfig.startPath = Environment.getExternalStorageDirectory().getPath();
                                fileConfig.multiModel = mList.get(imgPosition).isMore();

                                fileConfig.rootPath = "/";
                                intent.putExtra(FileConfig.FILE_CONFIG, fileConfig);
                                if (mStatusCode == 1 || mStatusCode == 2) {
                                } else {
                                    if (permissionIsOpen(AdjunctActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//如果有读写权限
                                        startActivityForResult(intent, 0);
                                    } else {//没有权限
                                        ActivityCompat.requestPermissions(AdjunctActivity.this, PERMISSIONS_STORAGE,
                                                REQUEST_EXTERNAL_STORAGE);
                                    }
                                }
                                return;
                            }
                            if (mStatusCode == 1 || mStatusCode == 2) {
                            } else {//申请单状态不是 已通过 或 已待审核
                                showDialog();
                            }
                        }
                    }
                    break;
                case 1://右上角图片重选
                    if (mList.get(imgPosition).isUpload()) {//已设置后才能重选，如不需要这个判断，删掉if else就好
                        //单选重新上传先删除
                        if (!isMoreSelect) {
                            if (mList.get(imgPosition).getFileNameStr().equals("FinanceReport")) {
                                Intent intent = new Intent(getApplicationContext(), FileSelectorActivity.class);
                                fileConfig.startPath = Environment.getExternalStorageDirectory().getPath();
                                fileConfig.multiModel = mList.get(imgPosition).isMore();

                                fileConfig.rootPath = "/";
                                intent.putExtra(FileConfig.FILE_CONFIG, fileConfig);
                                if (mStatusCode == 1 || mStatusCode == 2) {
                                } else {
                                    if (permissionIsOpen(AdjunctActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//如果有读写权限
                                        startActivityForResult(intent, 0);
                                    } else {//没有权限
                                        ActivityCompat.requestPermissions(AdjunctActivity.this, PERMISSIONS_STORAGE,
                                                REQUEST_EXTERNAL_STORAGE);
                                    }
                                }
                            } else {
                                showDialog();
                            }
                        } else {
                            if (mList.get(imgPosition).getFileNameStr().equals("FinanceReport")) {
                                Intent intent = new Intent(getApplicationContext(), FileSelectorActivity.class);
                                fileConfig.startPath = Environment.getExternalStorageDirectory().getPath();
                                fileConfig.multiModel = mList.get(imgPosition).isMore();

                                fileConfig.rootPath = "/";
                                intent.putExtra(FileConfig.FILE_CONFIG, fileConfig);
                                if (mStatusCode == 1 || mStatusCode == 2) {
                                } else {
                                    if (permissionIsOpen(AdjunctActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//如果有读写权限
                                        startActivityForResult(intent, 0);
                                    } else {//没有权限
                                        ActivityCompat.requestPermissions(AdjunctActivity.this, PERMISSIONS_STORAGE,
                                                REQUEST_EXTERNAL_STORAGE);
                                    }
                                }
                            } else {
                                showDialog();
                            }
                        }
                    } else {
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (EventKey.IMAGEDELETEUPDATE.equals(eventCustom.getTag())) {
            String fileId = (String) eventCustom.getObj();
            fileDelete(fileId, 0);
        }
    }

    /**
     * 删除文件
     *
     * @param attachmentId
     */
    private void fileDelete(String attachmentId, final int type) {
        Call call = HttpRequest.getIResource().deleteFile(attachmentId);
        callRequest(call, new HttpCallBack(JsonObject.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    if (type == 1) {
                        uploadFile(AdjunctActivity.this, fileList, mICustomListener);
                    } else {
                        MyToast.showToast("删除成功");
                    }
                    getAttachment();
                } else {
                    MyToast.showToast("删除失败，请重试");
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }

    //显示选择对话框
    private void showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        // 显示对话框
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setContentView(R.layout.dialog_adjunct);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int windowWidth = dm.widthPixels;
        int windowHeight = dm.heightPixels;

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = windowWidth * 5 / 6;//定义宽度
        dialog.getWindow().setAttributes(lp);

        LinearLayout llImg = (LinearLayout) window.findViewById(R.id.ll_img);
        llImg.setOnClickListener(new View.OnClickListener() {//选择图片
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isCamera = false;
                pickImage();
            }
        });
        LinearLayout llDoc = (LinearLayout) window.findViewById(R.id.ll_doc);
        llDoc.setOnClickListener(new View.OnClickListener() {//选择文档
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        LinearLayout llPhoto = (LinearLayout) window.findViewById(R.id.ll_photo);
        llPhoto.setOnClickListener(new View.OnClickListener() {//选择拍照
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isCamera = true;
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
                if (permissionIsOpen(AdjunctActivity.this, Manifest.permission.CAMERA)) {//相机权限
                    startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
                } else {
                    ActivityCompat.requestPermissions(AdjunctActivity.this, new String[]{Manifest.permission.CAMERA},
                            REQUEST_CRMERA);
                }
            }
        });
    }

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

    /**
     * 选择图片
     */
    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            boolean showCamera = true;//是否显示相机
            int maxNum = 9;//最大选择9张
            boolean isMulti = true;//是否多选

            MultiImageSelector selector = MultiImageSelector.create(this);
            selector.showCamera(showCamera);
            selector.count(maxNum);
            if (isMoreSelect) {
                selector.multi();
            } else {
                selector.single();
            }
//            selector.origin(mSelectPath);
            selector.start(this, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(AdjunctActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            }
        }
        if (requestCode == REQUEST_CRMERA) {//相机
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_TAKE_PICTURE || requestCode == REQUEST_CODE_LOAD_IMAGE) {//拍照及图片返回处理
            if (requestCode == REQUEST_CODE_LOAD_IMAGE) {
                mFilePath = DemoUtils.resolvePhotoFromIntent(this, data, FileAccessor.IMESSAGE_IMAGE);
            }
            if (TextUtils.isEmpty(mFilePath)) {
                return;
            }
            files = new ArrayList<>();
            ArrayList<String> filePathList = new ArrayList<>();
            //压缩图片的bitmap
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap src = BitmapFactory.decodeFile(mFilePath, options);
            Bitmap water = BitmapFactory.decodeResource(getResources(), R.mipmap.watermark);
            String newFilePath = WaterMarkUtils.createBitmap(src, water, FileUtils.getFileName(mFilePath));
            filePathList.add(newFilePath);
            src.recycle();
            water.recycle();

            List<File> list = new ArrayList<>();
            fileList = new ArrayList<>();
            File file = new File(newFilePath);
            if (file == null || !file.exists()) {
                return;
            }
            File fileSmall = null;
            try {
                String filePathStr = FileAccessor.getSmallPicture(file.getPath()); // 压缩文件
                fileSmall = new File(filePathStr);
                list.add(fileSmall);
                fileList.add(fileSmall);
                files.add(fileSmall);
                setImageToView(filePathStr, "png", true);//取压缩文件，防止OOM
            } catch (Exception e) {
                System.out.println("上传图片错误：" + e.toString());
                e.printStackTrace();
                fileSmall = file;
            }

            Intent intent = new Intent(AdjunctActivity.this, PhotoDetailActivity.class);
            intent.putExtra("download", false);
            intent.putExtra("delete", false);
            intent.putExtra("list", filePathList);
            intent.putExtra("position", 0);
            startActivityForResult(intent, REQUEST_IMAGE_DETAILS);
            return;
        }
        if (requestCode == REQUEST_IMAGE) {//图片选择
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if (mList.get(imgPosition).getFileList().size() + mSelectPath.size() > 9) {
                    MyToast.showToast("最多上传9张");
                    return;
                }
                files = new ArrayList<>();
                ArrayList<String> filePathList = new ArrayList<>();
                fileList = new ArrayList<>();
                for (int i = 0; i < mSelectPath.size(); i++) {
                    String filePath = mSelectPath.get(i);
                    if (TextUtils.isEmpty(filePath)) {
                        return;
                    }
                    //压缩图片的bitmap
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap src = BitmapFactory.decodeFile(filePath, options);
                    Bitmap water = BitmapFactory.decodeResource(getResources(), R.mipmap.watermark);
                    String newFilePath = WaterMarkUtils.createBitmap(src, water, FileUtils.getFileName(filePath));
                    filePathList.add(newFilePath);
                    src.recycle();
                    water.recycle();

                    File file = new File(newFilePath);
                    if (file == null || !file.exists()) {
                        return;
                    }
                    File fileSmall = null;
                    try {
                        String filePathStr = FileAccessor.getSmallPicture(file.getPath()); // 压缩文件
                        fileSmall = new File(filePathStr);
                        files.add(fileSmall);
                        fileList.add(fileSmall);
                        setImageToView(filePathStr, "png", true);//取压缩文件，防止OOM
                    } catch (Exception e) {
                        System.out.println("上传图片错误：" + e.toString());
                        e.printStackTrace();
                        fileSmall = file;
                    }
                }
                Intent intent = new Intent(AdjunctActivity.this, PhotoDetailActivity.class);
                intent.putExtra("download", false);
                intent.putExtra("delete", false);
                intent.putExtra("list", filePathList);
                intent.putExtra("position", 0);
                startActivityForResult(intent, REQUEST_IMAGE_DETAILS);
            }
        }

        if (requestCode == REQUEST_IMAGE_DETAILS) {
            if (!isMoreSelect) {//单选删除
                if (!StringUtils.isEmpty(mList.get(imgPosition).getFileList())) {
                    if (!StringUtils.isEmpty(mList.get(imgPosition).getFileList().get(0).getFileId())) {
                        fileDelete(mList.get(imgPosition).getFileList().get(0).getFileId(), 1);
                        return;
                    }
                }
            }
            uploadFile(AdjunctActivity.this, files, mICustomListener);
        }

        if (requestCode == 0) {//选择文件的处理
            ArrayList<String> list = data.getStringArrayListExtra(FileSelector.RESULT);
            List<File> lists = new ArrayList<>();
            fileList = new ArrayList<>();
            if (list != null || list.size() >= 1) {
                for (int i = 0; i < list.size(); i++) {
                    String filePath = list.get(i);
                    setImageToView(filePath, FileUtils.getFileLastName(filePath), true);
                    File file = new File(filePath);
                    lists.add(file);
                    fileList.add(file);
                }
            }
            if (!isMoreSelect) {//单选删除
                if (!StringUtils.isEmpty(mList.get(imgPosition).getFileList())) {
                    if (!StringUtils.isEmpty(mList.get(imgPosition).getFileList().get(0).getFileId())) {
                        fileDelete(mList.get(imgPosition).getFileList().get(0).getFileId(), 1);
                        return;
                    }
                }
            }
            uploadFile(AdjunctActivity.this, lists, mICustomListener);
        }
    }

    //设置图片等信息
    public void setImageToView(String url, String type, boolean isUpload) {
        AdjunctEntity entity = mList.get(imgPosition);
        if (type != null) {//判断type，防止部分文件获取不到后缀名
            entity.setFileType(type);
        } else {//设置默认后缀为back
            entity.setFileType("back");
        }
        mAdapter.notifyDataSetChanged();
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


    @OnClick(R.id.iv_callphone)
    public void onClick() {
        showCallServiceDialog(mPhone);
    }

    /**
     * 添加附件
     */
    private void addAttachment() {
        formInfo = JsonUtil.toJsonString(fileIdMap);
        formInfo = formInfo.replaceAll("\\s+", "");//去掉空格、换行符等
        AttachmentEntity entity = new AttachmentEntity();
        entity.setLoanApplyId(loanApplyId);
        entity.setVersionId(versionId);
        entity.setCreateType(0);
        AttachmentEntity.FormInfoBean infoBean = JsonUtil.fromJsonString(formInfo, AttachmentEntity.FormInfoBean.class);
        entity.setFormInfo(infoBean);
        String jsonStr = JsonUtil.toJsonString(entity);
        Log.d("jsonStr", jsonStr);
        Call call = HttpRequest.getIResource().AddAttachment(jsonToBody(jsonStr));
        callRequest(call, new HttpCallBack(ResultData.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    ZBXAlertDialog dialog = new ZBXAlertDialog(AdjunctActivity.this, new ZBXAlertListener() {
                        @Override
                        public void onDialogOk(Dialog dlg) {
                            PreferencesUtils.putBoolean(AdjunctActivity.this, loanApplyId, true);
                            submitApply();
                            dlg.dismiss();
                        }

                        @Override
                        public void onDialogCancel(Dialog dlg) {
                            dlg.dismiss();
                        }
                    }, "", "您确定要把资料提交银行审核吗？");
                    if (!isSave) {
                        dialog.show();
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
     * 获取附件
     */
    private void getAttachment() {
        Call call = HttpRequest.getIResource().getAttachment(loanApplyId, versionId);
        callRequest(call, new HttpCallBack(AttachmentGetEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    if (show) {
                        mListShow = new ArrayList<AttachmentGetEntity>();
                        mListShow = mResult.getDataList();
                        mListShow.remove(6);
                        mAdapterShow = new EnterpriseInfoAdapter(AdjunctActivity.this, mListShow);
                        mGridViewShow.setAdapter(mAdapterShow);
                        mAdapterShow.notifyDataSetChanged();
                    } else {
                        List<AttachmentGetEntity> list = mResult.getDataList();
                        for (int i = 0; i < mList.size(); i++) {
                            if (mList.get(i).getFileList().size() != 0) {
                                mList.get(i).getFileList().clear();
                                mList.get(i).setFileType("");
                            }
                        }
                        for (int i = 0; i < list.size(); i++) {
                            fileIdMap.put(list.get(i).getTypeName(), list.get(i).getBindingId());
                            for (int j = 0; j < mList.size(); j++) {
                                if (mList.get(j).getFileNameStr().equals(list.get(i).getTypeName())) {
                                    List<AdjunctEntity.Attachment> fileList = new ArrayList<>();
                                    for (int k = 0; k < list.get(i).getAttachment().size(); k++) {
                                        AdjunctEntity.Attachment attachment = new AdjunctEntity.Attachment();
                                        attachment.setFileId(list.get(i).getAttachment().get(k).getAttachmentId());
                                        attachment.setFileUrl(list.get(i).getAttachment().get(k).getFilePath());
                                        attachment.setFileName(list.get(i).getAttachment().get(k).getRealName());
                                        attachment.setDownloadPath(list.get(i).getAttachment().get(k).getDownloadPath());
                                        fileList.add(attachment);
                                        String fileType = FileUtils.getFileLastName(list.get(i).getAttachment().get(k).getRelativePath());
                                        mList.get(j).setFileType(fileType);
                                    }
                                    if (list.get(i).getAttachment().size() != 0) {
                                        mList.get(j).setFileList(fileList);
                                        mList.get(j).setUpload(true);
                                    } else {
                                        mList.get(j).setUpload(false);
                                    }
                                }
                            }
                        }
                        mAdapter.notifyDataSetChanged(mGridView, imgPosition, isOne);
                        isOne = true;
                    }
                } else {
                    Log.d("fail", "getAttchment:" + mResult.getMsg());
                }

            }

            @Override
            public void onFailure(String string) {
                Log.d("fail", "getAttchment:网络异常");
            }
        });
    }


    /**
     * 上传附件
     * 上传更改后的文件
     *
     * @param context
     * @param list
     * @param listener
     */
    public void uploadFile(Context context, List<File> list, final ICustomListener listener) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("BindingId", mList.get(imgPosition).getGuid());
        if (!mList.get(imgPosition).getFileNameStr().equals("FinanceReport")) {
            map.put("IsAddWaterMark", "false");
        }
        Map<String, File> mapFile = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            mapFile.put("FilesMulti" + i, list.get(i));
        }

        String server = ConfigUtils.getConfig(ConfigUtils.KEY.server);
        new BaseAsyncTaskFile(context, true, 0, server + "Chinarcb/UploadFiles", new BaseAsyncTaskInterface() {
            @Override
            public void dataSubmit(int funId) {

            }

            @Override
            public Object dataParse(int funId, String json) throws Exception {
                return new ResultData<FileEntity>().parse(json, FileEntity.class);
            }

            @Override
            public void dataSuccess(int funId, Object result) {
                ResultData mResult = (ResultData) result;
                if ("1".equals(mResult.getSuccess())) {//0成功
                    listener.onCustomListener(0, mResult.getDataList(), 0);
                } else {
                    String message = mResult.getMsg();
                    MyToast.showToast(message);
                }
            }

            @Override
            public void dataError(int funId) {
                MyToast.showToast("获取网络数据失败");
            }
        }, mapFile).execute(map);
    }

    /**
     * 回调
     */
    private ICustomListener mICustomListener = new ICustomListener() {
        @Override
        public void onCustomListener(int obj0, Object obj1, int position) {
            switch (obj0) {
                case 0:
                    List<FileEntity> list = (ArrayList<FileEntity>) obj1;
                    MyToast.showToast("上传成功");
                    getAttachment();
                    break;
                case 1:
                    MyToast.showToast("上传失败,请重新上传");
                    break;
            }
        }
    };

    /**
     * 保存草稿
     */
    public void AddDraft() {
        Call call = HttpRequest.getIResource().AddDraft(loanApplyId, versionId, step, formString);
        callRequest(call, new HttpCallBack(BankSelectEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    MyToast.showToast("保存成功");
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
     * 提交申请表单
     */
    public void submitApply() {
        Call call = HttpRequest.getIResource().SubmitApply(loanApplyId);
        callRequest(call, new HttpCallBack(JsonObject.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    MyToast.showToast("提交成功");
                    Intent intent = new Intent(AdjunctActivity.this, CreditProgressActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("finish", 1);
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

    private AttachmentBindingEntity mBindingEntity;

    /**
     * 获取附件上传BindingId
     */
    public void GetAttachmentBindingId() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoanApplyId", loanApplyId);
        jsonObject.addProperty("VersionId", versionId);
        String json = jsonObject.toString();
        Call call = HttpRequest.getIResource().GetAttachmentBindingId(jsonToBody(json));
        callRequest(call, new HttpCallBack(AttachmentBindingEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    mBindingEntity = (AttachmentBindingEntity) mResult.getData();

                    for (int i = 0; i < mList.size(); i++) {
                        String fileNameStr = mList.get(i).getFileNameStr();
                        if ("BusinessLicense".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getBusinessLicense());
                            fileIdMap.put("BusinessLicense", mBindingEntity.getBusinessLicense());
                        } else if ("AccountOpeningPermit".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getAccountOpeningPermit());
                            fileIdMap.put("AccountOpeningPermit", mBindingEntity.getAccountOpeningPermit());
                        } else if ("TaxCertificate".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getTaxCertificate());
                            fileIdMap.put("TaxCertificate", mBindingEntity.getTaxCertificate());
                        } else if ("CorporationIDCard".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getCorporationIDCard());
                            fileIdMap.put("CorporationIDCard", mBindingEntity.getCorporationIDCard());
                        } else if ("FinanceIDCard".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getFinanceIDCard());
                            fileIdMap.put("FinanceIDCard", mBindingEntity.getFinanceIDCard());
                        } else if ("PartnerIDCard".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getPartnerIDCard());
                            fileIdMap.put("PartnerIDCard", mBindingEntity.getPartnerIDCard());
                        } else if ("FinanceReport".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getFinanceReport());
                            fileIdMap.put("FinanceReport", mBindingEntity.getFinanceReport());
                        } else if ("EnterpriseArticles".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getEnterpriseArticles());
                            fileIdMap.put("EnterpriseArticles", mBindingEntity.getEnterpriseArticles());
                        } else if ("CapitalVerification".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getCapitalVerification());
                            fileIdMap.put("CapitalVerification", mBindingEntity.getCapitalVerification());
                        } else if ("HouseProperty".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getHouseProperty());
                            fileIdMap.put("HouseProperty", mBindingEntity.getHouseProperty());
                        } else if ("EnterpriseHistory".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getEnterpriseHistory());
                            fileIdMap.put("EnterpriseHistory", mBindingEntity.getEnterpriseHistory());
                        } else if ("FeasibilityStudy".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getFeasibilityStudy());
                            fileIdMap.put("FeasibilityStudy", mBindingEntity.getFeasibilityStudy());
                        } else if ("OtherApproval".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getOtherApproval());
                            fileIdMap.put("OtherApproval", mBindingEntity.getOtherApproval());
                        } else if ("OrganizationCode".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getOrganizationCode());
                            fileIdMap.put("OtherApproval", mBindingEntity.getOrganizationCode());
                        } else if ("CreditCode".equals(fileNameStr)) {
                            mList.get(i).setGuid(mBindingEntity.getCreditCode());
                            fileIdMap.put("OtherApproval", mBindingEntity.getCreditCode());
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

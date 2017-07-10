package com.zbxn.main.activity.steward;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.pub.base.BaseActivity;
import com.pub.dialog.ZBXAlertDialog;
import com.pub.dialog.ZBXAlertListener;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.http.uploadfile.BaseAsyncTaskFile;
import com.pub.http.uploadfile.BaseAsyncTaskInterface;
import com.pub.utils.ConfigUtils;
import com.pub.utils.FileAccessor;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.MyProgressDialog;
import com.pub.widget.fileselector.FileSelector;
import com.pub.widget.fileselector.FileSelectorActivity;
import com.pub.widget.fileselector.config.FileConfig;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.adapter.CircleAdapter;
import com.zbxn.main.adapter.FinanceExcelAdapter;
import com.zbxn.main.entity.AttachmentBindingIdEntity;
import com.zbxn.main.entity.AttachmentGetEntity;
import com.zbxn.main.entity.FileEntity;
import com.zbxn.main.entity.ReportListEntity;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/3/23.
 */
public class FinaExcelActivity extends BaseActivity implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {
    @BindView(R.id.tabs)
    LinearLayout tabs;
    @BindView(R.id.tabsContainer)
    HorizontalScrollView mHorizontalScrollView;
    @BindView(R.id.elv_excel)
    ExpandableListView mListView;
    @BindView(R.id.iv_callphone)
    ImageView ivCallphone;
    @BindView(R.id.gv_twelveCircle)
    GridView circleCridView;
    @BindView(R.id.ll_nodata)
    LinearLayout ll_nodata;
    //将标签放到LinearLayout中
    private Calendar mCalendar;
    private int nowYear;
    private int mLastIndex;
    private List<String> mTitles = new ArrayList<>();

    private FinanceExcelAdapter mAdapter;
    private List<ReportListEntity> mList;
    private int mSelectNot = 1;  //1 代表的是展开，  2 代表的没有展开
    private FileEntity mEntity;

    private FileConfig fileConfig;

    private List<String> mListCircle = new ArrayList<>();
    private CircleAdapter mCircleAdapter;
    private String mYearString = "";
    private String mLoanApplyId = "";
    private String mVersionId = "";

    private String mDateSelect = "";  //当前要上传的月份
    private String mPhone = "";
    private ZBXAlertDialog mDialog;
    private int mDeletePosition = 0;
    private int mUpload = 1;//这个是判断是否重新上传     1代表的是上传     2代表的是重新上传
    private boolean mIsBank = true;//银行端展示或者企业端
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private File file;
    private int mIndex = 0;
    private int mState = 0;
    private String mUrl = "";
    private MyProgressDialog mMyProgressDialog = null;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steward_finaexcel);
        ButterKnife.bind(this);
        setTitle("财务报表");

        mMyProgressDialog = new MyProgressDialog(FinaExcelActivity.this);

        mListView.setEmptyView(ll_nodata);
        mListView.setGroupIndicator(null);
        mListView
                .setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS);

        mIsBank = getIntent().getBooleanExtra("bank", true);
        mLoanApplyId = getIntent().getStringExtra("LoanApplyId");
        mVersionId = getIntent().getStringExtra("VersionId");
        mPhone = getIntent().getStringExtra("Phone");
        int screenWidth = getResources().getDisplayMetrics().widthPixels;

        int distance = screenWidth / 2 - 150;
        tabs.setPadding(distance, 0, distance, 0);

        //根据传来的参数进行一个判断，1 代表的是贷管家的财务报表      2 代表的是贷管家的报表上传

        initData();
        if (mIsBank) {
            GetReportListByAccountManagerId(nowYear + "");
            ivCallphone.setVisibility(View.VISIBLE);
        } else {
            ivCallphone.setVisibility(View.GONE);
            GetReportList(nowYear + "");
        }
        initView();
        initTabs();
        initListView();
    }

    private void initListView() {
//        圆圈点击事件    银行端:点击月份面板，对于已经上传的月份，滚动至选择月份，对于未上传的月份，弹出弹窗，提示给企业发出消息
//        企业端，点击月份面板，对于所有的月份，滚动至选择月份
        if (mList.size() != 0) {
            circleCridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mIsBank) {//银行端
                        if (position == 0) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "01") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
//                                        mListView.setSelection(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        } else if (position == 1) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "02") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        } else if (position == 2) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "03") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        } else if (position == 3) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "04") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        } else if (position == 4) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "05") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        } else if (position == 5) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "06") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        } else if (position == 6) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "07") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        } else if (position == 7) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "08") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        } else if (position == 8) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "09") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        } else if (position == 9) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "10") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        } else if (position == 10) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "11") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        } else if (position == 11) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "12") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    } else {
                                        mIndex = i;
                                        showAlert();
                                    }
                                }
                            }
                        }
                    } else {//企业端
                        if (position == 0) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "01") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
//                                        mListView.setSelection(mIndex);
                                    }
                                }
                            }
                        } else if (position == 1) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "02") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    }
                                }
                            }
                        } else if (position == 2) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "03") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    }
                                }
                            }
                        } else if (position == 3) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "04") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    }
                                }
                            }
                        } else if (position == 4) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "05") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    }
                                }
                            }
                        } else if (position == 5) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "06") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    }
                                }
                            }
                        } else if (position == 6) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "07") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    }
                                }
                            }
                        } else if (position == 7) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "08") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    }
                                }
                            }
                        } else if (position == 8) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "09") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    }
                                }
                            }
                        } else if (position == 9) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "10") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    }
                                }
                            }
                        } else if (position == 10) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "11") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    }
                                }
                            }
                        } else if (position == 11) {
                            for (int i = 0; i < mList.size(); i++) {
                                mIndex = i;
                                if (StringUtils.convertDateMonth(mList.get(mIndex).getCreateDate()) == "12") {
                                    if (mList.get(mIndex).isIsUpLoad() && mList.get(mIndex).getFormInfo() != null) {
                                        mListView.smoothScrollToPosition(mIndex);
                                    }
                                }
                            }
                        }
                    }

                }
            });
        }

    }


    //进行基本数据的一个填充
    private void initData() {
        mList = new ArrayList<>();
        fileConfig = new FileConfig();
//        tabs.setPadding();
        mCalendar = Calendar.getInstance();
        nowYear = mCalendar.get(Calendar.YEAR);
        mYearString = nowYear + "";
        for (int i = nowYear - 5; i < nowYear + 1; i++) {
            mTitles.add(i + "");
        }
        for (int i = 0; i < 9; i++) {
            mListCircle.add("  " + (i + 1) + "  ");
        }
        for (int i = 9; i < 12; i++) {
            mListCircle.add((i + 1) + "");
        }
    }

    private void deleteAlert(final ReportListEntity.FormInfoBean bean) {
        mDialog = new ZBXAlertDialog(FinaExcelActivity.this, new ZBXAlertListener() {
            @Override
            public void onDialogOk(Dialog dlg) {
                fileDelete(bean.getAttachmentId());
                dlg.dismiss();
            }

            @Override
            public void onDialogCancel(Dialog dlg) {
                dlg.dismiss();
            }
        }, "", "   确定要删除  " + bean.getRealName() + "  吗？   ");
        mDialog.show();
    }


    private void showAlert() {
        mDialog = new ZBXAlertDialog(FinaExcelActivity.this, new ZBXAlertListener() {
            @Override
            public void onDialogOk(Dialog dlg) {
                RemindUploadReport(mYearString + "-" + StringUtils.convertDateMonth(mList.get(mIndex).getDate()));
                dlg.dismiss();
            }

            @Override
            public void onDialogCancel(Dialog dlg) {
                dlg.dismiss();
            }
        }, "", "确定要提醒企业上传资料吗？");
        mDialog.show();
    }


    //adapter 传一个type 其中 1 代表的是农信贷方面的      2代表的是贷管家的
    private void initView() {
        if (mIsBank) {
            mAdapter = new FinanceExcelAdapter(this, mList, "2", listener);
            mAdapter.notifyDataSetChanged();
            mListView.setAdapter(mAdapter);
            for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                mListView.expandGroup(i);
            }
        } else {
            mAdapter = new FinanceExcelAdapter(this, mList, "1", listener);
            mAdapter.notifyDataSetChanged();
            mListView.setAdapter(mAdapter);
            for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                mListView.expandGroup(i);
            }
        }

        mListView.setOnChildClickListener(this);
        mListView.setOnGroupClickListener(this);

        mCircleAdapter = new CircleAdapter(this, mListCircle);
        circleCridView.setAdapter(mCircleAdapter);
    }

    private ICustomListener listener = new ICustomListener() {
        @Override
        public void onCustomListener(int obj0, Object obj1, final int position) {
            if (!FileAccessor.isExistExternalStore()) {
                return;
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = FileAccessor.getTackPicFilePath();
            switch (obj0) {
                case 1://上传附件
                    ReportListEntity entity = (ReportListEntity) obj1;
                    mDateSelect = StringUtils.convertDate1(entity.getDate()) + "-01";
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
                    }
                    if (permissionIsOpen(FinaExcelActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {//相机权限
                        mUpload = 1;
                        Intent intent1 = new Intent(getApplicationContext(), FileSelectorActivity.class);
                        fileConfig.startPath = Environment.getExternalStorageDirectory().getPath();
                        fileConfig.multiModel = true;
                        fileConfig.rootPath = "/";
                        intent1.putExtra(FileConfig.FILE_CONFIG, fileConfig);
                        startActivityForResult(intent1, 0);
                    } else {
                        MyToast.showToast("请在设置中打开读取内存权限");
                    }

                    break;
                case 2://报表的删除
                    ReportListEntity.FormInfoBean formInfoBean = (ReportListEntity.FormInfoBean) obj1;
                    deleteAlert(formInfoBean);
                    break;
                case 3://去提醒推送
                    mIndex = position;
                    showAlert();
                    break;
                case 4://重新上传附件
                    if (file != null) {
                        Uri uri = null;
                        if (Build.VERSION.SDK_INT >= 24) {
                            uri = getImageContentUri(file);
                        } else {
                            uri = Uri.fromFile(file);
                        }
                    }
                    if (permissionIsOpen(FinaExcelActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {//相机权限
                        mUpload = 2;
                        Intent intent2 = new Intent(getApplicationContext(), FileSelectorActivity.class);
                        fileConfig.startPath = Environment.getExternalStorageDirectory().getPath();
                        fileConfig.multiModel = true;
                        fileConfig.rootPath = "/";
                        mDeletePosition = position;
                        intent2.putExtra(FileConfig.FILE_CONFIG, fileConfig);
                        startActivityForResult(intent2, 0);
                    } else {
                        MyToast.showToast("请在设置中打开读取内存权限");
                    }
                    break;
                default:
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


    private void initTabs() {
        for (int i = 0; i < mTitles.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_horizontalscrollview, null, false);
            final TextView year = (TextView) view.findViewById(R.id.tv_year);
            final ImageView arrow = (ImageView) view.findViewById(R.id.iv_arrow);
            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectNot == 1) {
                        mSelectNot = 2;
                        circleCridView.setVisibility(View.GONE);
                        arrow.setImageResource(R.mipmap.caiwubaobiao_xiala);
                    } else {
                        mSelectNot = 1;
                        circleCridView.setVisibility(View.VISIBLE);
                        arrow.setImageResource(R.mipmap.caiwubaobiao_shouqi);
                    }
                }
            });
            year.setText(mTitles.get(i));
            view.setTag(i);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //参数是对布局进行设置一个宽高
            layoutParams.rightMargin = 5;
            layoutParams.leftMargin = 5;
            view.setLayoutParams(layoutParams);
            if (i == 5) {
                arrow.setVisibility(View.VISIBLE);
                mSelectNot = 1;
                circleCridView.setVisibility(View.VISIBLE);
            } else {
                arrow.setVisibility(View.GONE);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mIndex = (int) v.getTag();
                    tabs.getChildAt(mIndex).setEnabled(false);
                    View tab = tabs.getChildAt(mIndex);

                    mYearString = (mIndex + nowYear - 5) + "";
                    final ImageView arrow = (ImageView) tab.findViewById(R.id.iv_arrow);
                    arrow.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.mipmap.caiwubaobiao_shouqi);
                    //之前选中的位置，回复一下
                    tabs.getChildAt(mLastIndex).setEnabled(true);
                    View lasttab = tabs.getChildAt(mLastIndex);
                    ImageView lastarrow = (ImageView) lasttab.findViewById(R.id.iv_arrow);
                    lastarrow.setVisibility(View.GONE);
                    if (mLastIndex != mIndex) {
                        arrow.setVisibility(View.VISIBLE);
                        circleCridView.setVisibility(View.VISIBLE);
                        mSelectNot = 1;

                        if (mIsBank) {
                            GetReportListByAccountManagerId((mIndex + nowYear - 5) + "");

                        } else {
                            GetReportList((mIndex + nowYear - 5) + "");

                        }

                    } else {
                        arrow.setVisibility(View.VISIBLE);
                        mSelectNot = 1;
                    }
                    mLastIndex = mIndex;
                    //滑动HorizaontalSrollView中间位置
                    //参数一进行获取，x轴方向上滑动距离
                    //tabs.getChildAt( position )这个是当前的控件，获取他的属性，getLeft();
                    //getLeft()-----是x轴方向上的坐标
                    int x = tabs.getChildAt(mIndex).getLeft();
                    int screenWidth = getResources().getDisplayMetrics().widthPixels;
                    int tabwidth = tabs.getChildAt(mIndex).getWidth();
                    int distance = screenWidth / 2 - tabwidth / 2;
                    x = x - distance;
                    mHorizontalScrollView.scrollTo(x, 0);
                }
            });
            //向tabs之中添加视图
            tabs.addView(view);
        }
        mHorizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
//                mHorizontalScrollView.scrollTo(800,0);
                mHorizontalScrollView.fullScroll(ScrollView.FOCUS_RIGHT);//滚动到底部
                mLastIndex = 5;
            }
        });
    }

    @OnClick({R.id.iv_callphone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_callphone:
                showCallServiceDialog(mPhone);
                break;
        }
    }


    //    这个是获取一年的报表
    private void GetReportListByAccountManagerId(String year) {
        Call call = HttpRequest.getIResource().GetReportListByAccountManagerId(mLoanApplyId, year);
        callRequest(call, new HttpCallBack(ReportListEntity.class, this, true) {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(final ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    mList = new ArrayList<ReportListEntity>();
                    mList = mResult.getDataList();
//                    for (int i = 0; i < mList.size(); i++) {
//                        if (mList.get(i).getFormInfo() == null) {
//                            mList.remove(i);
//                        }
//                    }
                    initView();
                    circleCridView.post(new Runnable() {
                        @Override
                        public void run() {
                            ReportList(mList);
                        }
                    });

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


    private void GetReportList(String year) {
        Call call = HttpRequest.getIResource().GetReportList(mLoanApplyId, year);
        callRequest(call, new HttpCallBack(ReportListEntity.class, this, true) {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(final ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    mList = new ArrayList<ReportListEntity>();
                    mList = mResult.getDataList();
//                    for (int i = 0; i < mList.size(); i++) {
//                        if (mList.get(i).getFormInfo() == null) {
//                            mList.remove(i);
//                        }
//                    }
                    initView();
                    circleCridView.post(new Runnable() {
                        @Override
                        public void run() {
                            ReportList(mList);
                        }
                    });

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


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void ReportList(List<ReportListEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            String month = StringUtils.convertDateMonth(mList.get(i).getDate());
            if (month.equals("01")) {
                View view = circleCridView.getChildAt(0);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
                if (mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
            if (month.equals("02")) {
                View view = circleCridView.getChildAt(1);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
//                if (!mList.get(i).isIsUpLoad() || mList.get(i).getFormInfo() == null) {
                if (mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
            if (month.equals("03")) {
                View view = circleCridView.getChildAt(2);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
                if (mList.get(i).getFormInfo() == null) {
//                if (!mList.get(i).isIsUpLoad() || mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
            if (month.equals("04")) {
                View view = circleCridView.getChildAt(3);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
                if (mList.get(i).getFormInfo() == null) {
//                if (!mList.get(i).isIsUpLoad() || mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
            if (month.equals("05")) {
                View view = circleCridView.getChildAt(4);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
                if (StringUtils.isEmpty(mList.get(i).getFormInfo())) {
//                if (!mList.get(i).isIsUpLoad() || mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
            if (month.equals("06")) {
                View view = circleCridView.getChildAt(5);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
                if (mList.get(i).getFormInfo() == null) {
//                if (!mList.get(i).isIsUpLoad() || mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
            if (month.equals("07")) {
                View view = circleCridView.getChildAt(6);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
                if (mList.get(i).getFormInfo() == null) {
//                if (!mList.get(i).isIsUpLoad() || mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
            if (month.equals("08")) {
                View view = circleCridView.getChildAt(7);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
                if (mList.get(i).getFormInfo() == null) {
//                if (!mList.get(i).isIsUpLoad() || mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
            if (month.equals("09")) {
                View view = circleCridView.getChildAt(8);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
                if (mList.get(i).getFormInfo() == null) {
//                if (!mList.get(i).isIsUpLoad() || mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
            if (month.equals("10")) {
                View view = circleCridView.getChildAt(9);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
                if (mList.get(i).getFormInfo() == null) {
//                if (!mList.get(i).isIsUpLoad() || mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
            if (month.equals("11")) {
                View view = circleCridView.getChildAt(10);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
                if (mList.get(i).getFormInfo() == null) {
//                if (!mList.get(i).isIsUpLoad() || mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
            if (month.equals("12")) {
                View view = circleCridView.getChildAt(11);
                TextView number = (TextView) view.findViewById(R.id.tv_number);
                if (mList.get(i).getFormInfo() == null) {
//                if (!mList.get(i).isIsUpLoad() || mList.get(i).getFormInfo() == null) {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_red));
                    number.setTextColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    number.setBackground(getResources().getDrawable(R.drawable.bg_textview_blue));
                    number.setTextColor(getResources().getColor(R.color.white));
                }
            }
        }
    }


    //    这个是进行某个月份的报表的上传
    private void AddReport(String date, String bindingId, final ICustomListener listener) {
        Call call = HttpRequest.getIResource().AddReport(mLoanApplyId, bindingId, date);
        callRequest(call, new HttpCallBack(AttachmentGetEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    if (mIsBank) {
                        GetReportListByAccountManagerId(mYearString);
                    } else {
                        GetReportList(mYearString);
                    }
                    listener.onCustomListener(0, mResult.getData(), 0);

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


    private void RemindUploadReport(String date) {
        Call call = HttpRequest.getIResource().remindUploadReport(mLoanApplyId, mVersionId, date);
        callRequest(call, new HttpCallBack(ReportListEntity.class, this, true) {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(final ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    MyToast.showToast("提醒成功");
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
     * 删除文件
     *
     * @param attachmentId
     */
    private void fileDelete(String attachmentId) {
        Call call = HttpRequest.getIResource().deleteFile(attachmentId);
        callRequest(call, new HttpCallBack(JsonObject.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
//                    GetReportBindingId(file, mDateSelect);
                    MyToast.showToast("文件删除成功");
                    if (mIsBank) {
                        GetReportListByAccountManagerId(mYearString);
                    } else {
                        GetReportList(mYearString);
                    }

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


    /**
     * 上传更改后的文件
     *
     * @param context
     * @param list
     */
    public void uploadFile(Context context, final List<File> list, String bindingId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("BindingId", bindingId);

        //对应Chinarcb/UploadFiles接口
        map.put("IsAddWaterMark", "false");
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
                    if (!StringUtils.isEmpty(mResult.getDataList())) {
                        mEntity = (FileEntity) mResult.getDataList().get(0);
                        AddReport(mDateSelect, mEntity.getBindingId(), mICustomListener);
                        //这里代表已经将修改后的文件上传成功了，之后调取删除接口，删除成功后，然后再进行一个上传
                    } else {
                        MyToast.showToast("上传失败");
                    }
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
            }

            @Override
            public void dataError(int funId) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        }, mapFile).execute(map);
    }

    /**
     * 上传更改后的文件  单文件上传
     *
     * @param context
     * @param file
     */
    public void uploadFileSingle(Context context, final File file, String bindingId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("BindingId", bindingId);

        //对应file/UploadSingleFile接口
        Map<String, File> mapFile = new HashMap<>();
        mapFile.put("File", file);


        String server = ConfigUtils.getConfig(ConfigUtils.KEY.server);
        new BaseAsyncTaskFile(context, true, 0, server + "file/UploadSingleFile", new BaseAsyncTaskInterface() {
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

                    mEntity = (FileEntity) mResult.getData();
                    AddReport(mDateSelect, mEntity.getBindingId(), mICustomListener);
//这里代表已经将修改后的文件上传成功了，之后调取删除接口，删除成功后，然后再进行一个上传
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


    /**
     * 回调
     */
    private ICustomListener mICustomListener = new ICustomListener() {
        @Override
        public void onCustomListener(int obj0, Object obj1, int position) {
            switch (obj0) {
                case 0:
                    MyToast.showToast("上传文件成功");

//                    if (isBank) {
//                        GetReportListByAccountManagerId(nowYear + "");
//
//                    } else {
//                        GetReportList(nowYear + "");
//                    }
//                    mAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    MyToast.showToast("上传文件失败");
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == 0) {//选择文件的处理
            ArrayList<String> list = data.getStringArrayListExtra(FileSelector.RESULT);
            List<File> lists = new ArrayList<>();
            if (list != null || list.size() >= 1) {
                for (int i = 0; i < list.size(); i++) {
                    String filePath = list.get(i);
                    File file = new File(filePath);
                    lists.add(file);
                }
                GetReportBindingId(lists, mDateSelect);


//                //先进行删除文件
//                if (mUpload == 1) {//1  代表的是直接上传    2     是先删除然后进行上传
//                    GetReportBindingId(file, mDateSelect);
//                } else {
//                    fileDelete(mList.get(mDeletePosition).getFormInfo().get(0).getAttachmentId());
//                }
            }
        }
    }

    /**
     * 0780adb4-1e83-4ce6-b90c-8b951a3e5792
     * 获取附件上传BindingId
     */
    public void GetReportBindingId(final List<File> file, String date) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoanApplyId", mLoanApplyId);
        jsonObject.addProperty("Date", date);
        String json = jsonObject.toString();
        Call call = HttpRequest.getIResource().GetReportBindingId(jsonToBody(json));
        callRequest(call, new HttpCallBack(AttachmentBindingIdEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    AttachmentBindingIdEntity entity = (AttachmentBindingIdEntity) mResult.getData();

                    uploadFile(FinaExcelActivity.this, file, entity.getBindingId());
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


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if (!StringUtils.isEmpty(mList.get(groupPosition).getFormInfo().get(childPosition).getFilePath())) {


//            String loanApplyIdString = mList.get(groupPosition).getLoanApplyId();
//            String permissionIdString = mList.get(groupPosition).getFilePermissions();
//            String bindingIdString = mList.get(groupPosition).getBindingId();
//
//            mUrl = "http://proxy.dev.zbzbx.com/ChinaRCB/GetReportFile?" +
//                    "LoanApplyId=" + loanApplyIdString +
//                    "&PermissionsId=" + permissionIdString +
//                    "&BindingId=" + bindingIdString + "&Type=.xlsx";
//            mUrl = "https://nj02all01.baidupcs.com/file/82bb680fa97d22849c80256784993d92?bkt=p3-140082bb680fa97d22849c80256784993d92545f09490000003c3c39&fid=3179256951-250528-906299940651214&time=1495683848&sign=FDTAXGERLBHS-DCb740ccc5511e5e8fedcff06b081203-%2BZwUikWM5Rmvoh2iS9%2B3adyb0t0%3D&to=69&size=3947577&sta_dx=3947577&sta_cs=0&sta_ft=doc&sta_ct=7&sta_mt=7&fm2=MH,Nanjing02,Netizen-anywhere,,shandong,ct&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=140082bb680fa97d22849c80256784993d92545f09490000003c3c39&sl=83034191&expires=8h&rt=sh&r=317076094&mlogid=3351107646647276221&vuk=3836512714&vbdid=163467142&fin=%E3%80%90%E4%B8%8A%E8%82%A1%E4%B8%96%E5%AE%B6%E3%80%91%E6%B7%98%E9%87%91%E5%86%85%E5%8F%828%E6%9C%8812%E6%97%A5.doc&fn=%E3%80%90%E4%B8%8A%E8%82%A1%E4%B8%96%E5%AE%B6%E3%80%91%E6%B7%98%E9%87%91%E5%86%85%E5%8F%828%E6%9C%8812%E6%97%A5.doc&rtype=1&iv=0&dp-logid=3351107646647276221&dp-callid=0.1.1&hps=1&csl=300&csign=SBVaksljtHKaoYlqAoK7%2BvyRXE8%3D&by=themis";
//            mUrl = mList.get(groupPosition).getFormInfo().get(childPosition).getFilePath();
            mUrl = mList.get(groupPosition).getFormInfo().get(childPosition).getDownloadPath();
//            mUrl = "http://proxy.dev.zbzbx.com/chinarcb/GetReportFile?LoanApplyId=03ffb92b-487a-4101-a762-8e30af544319&PermissionsId=735241C8-C467-42C6-8AEA-460BEAE36CF7";
            mMyProgressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url1 = new URL(mUrl);
                        HttpURLConnection con = null;
                        con = (HttpURLConnection) url1.openConnection();
                        con.setConnectTimeout(100000);
                        con.connect();
                        mState = con.getResponseCode();
                        mHandler.sendEmptyMessage(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            MyToast.showToast("请上传文件");
        }
        Log.d("okhttp", mUrl);
        return false;
    }


    // 标准的写法
    private Handler mHandler = new Handler() {
        // 使用handleMessage去处理消息！！，里面的参数Message msg既是发送过来的参数~
        @Override
        public void handleMessage(android.os.Message msg) {
            mMyProgressDialog.dismiss();
            if (mState == 401) {
                MyToast.showToast("该链接已经失效");
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
                startActivity(intent);
            }
        }

        ;
    };


    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (mList.get(groupPosition).getFormInfo() == null) {
            if (mIsBank) {
                MyToast.showToast("该企业还没上传报表哦");
            } else {
                MyToast.showToast("您还没有上传财务报表");
            }
        }
        /*返回TRUE的话就代表列表永远展开，返回FALSE的话就代表是列表关闭*/
        return true;
    }
}

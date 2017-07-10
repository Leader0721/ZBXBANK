package com.zbxn.main.activity.dynamic;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pub.base.BaseActivity;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.http.uploadfile.BaseAsyncTaskFile;
import com.pub.http.uploadfile.BaseAsyncTaskInterface;
import com.pub.http.uploadfile.Result;
import com.pub.utils.ConfigUtils;
import com.pub.utils.FileAccessor;
import com.pub.utils.MyToast;
import com.pub.utils.PreferencesUtils;
import com.pub.utils.StringUtils;
import com.pub.widget.MyGridView;
import com.pub.widget.fileselector.FileSelector;
import com.pub.widget.fileselector.FileSelectorActivity;
import com.pub.widget.fileselector.config.FileConfig;
import com.pub.widget.fileselector.utils.FileUtils;
import com.pub.widget.multi_image_selector.MultiImageSelector;
import com.zbxn.R;
import com.zbxn.main.activity.contacts.ContactsChoseActivity;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.activity.steward.PhotoDetailActivity;
import com.zbxn.main.adapter.InformerAdapter;
import com.zbxn.main.adapter.PhotoListAdapter;
import com.zbxn.main.entity.AttachmentGetEntity;
import com.zbxn.main.entity.ContactsEntity;
import com.zbxn.main.entity.InformerEntity;
import com.zbxn.main.entity.PhotosEntity;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/3/21.
 */
public class PublishMessageActivity extends BaseActivity {
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_detail)
    WebView etContent;
    @BindView(R.id.mGridView_Image)
    MyGridView mGridViewImage;
    @BindView(R.id.ll_file)
    LinearLayout llFile;
    @BindView(R.id.iv_pickPhotoFile)
    ImageView ivPickPhotoFile;
    @BindView(R.id.mGridView_People)
    MyGridView mGridViewPeople;

    //0--没开始加载  1--加载中  2--加载完成
    private int mWebviewState = 0;
    private String mContentStr = "请输入消息内容";

    private ArrayList<PhotosEntity> lists = new ArrayList<PhotosEntity>();
    private ArrayList<ContactsEntity> mListContacts = new ArrayList<>();

    private FileConfig fileConfig;
    //获取图片
    private static final int REQUEST_IMAGE = 2002;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    //图片路径的集合
    private ArrayList<String> mSelectPath;
    private String ssid;
    private String mGuid = "";
    //空的加号 进行通知人的添加
    private ContactsEntity entityEmpty = null;
    final int FLAG_ADDUSER = 1000;
    private InformerAdapter informerAdapter;
    private String userIds;
    private List<InformerEntity> listInformer;
    //    下面进行文件的操作
    private ArrayList<PhotosEntity> fileList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_publishmessage);
        ButterKnife.bind(this);
        setTitle("发布消息");
        mGuid = StringUtils.getGuid();
        fileList = new ArrayList<>();
        lists = new ArrayList<>();
        mGuid = StringUtils.getGuid();
        fileConfig = new FileConfig();

        initData();
        lists.clear();
        lists = updatePhotos(lists, null);
        PhotoListAdapter adapterPhotos = new PhotoListAdapter(this, lists, R.layout.list_item_select_photos, listener, true);
        mGridViewImage.setAdapter(adapterPhotos);
        initInform();
    }


    private void initData() {
        etContent.setWebViewClient(new WebViewClient());
        WebSettings webSettings = etContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        mWebviewState = 1;
        etContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                mWebviewState = 2;
                if (url.startsWith("gethtml:")) {
                    try {
                        mContentStr = URLDecoder.decode(url.substring(8).toString(), "UTF-8");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mWebviewState = 2;
                super.onPageFinished(view, url);
            }
        });
        etContent.loadUrl("file:///android_asset/inputWebView/Demo.html");
    }


    //添加通知人
    private void initInform() {
        entityEmpty = new ContactsEntity();
        entityEmpty.setUserName("");
        entityEmpty.setId(-1);
        entityEmpty.setHeadImgUrl("");
        mListContacts = updateContacts(mListContacts, null);
        informerAdapter = new InformerAdapter(mListContacts, this);
        mGridViewPeople.setAdapter(informerAdapter);
        mGridViewPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (StringUtils.isEmpty(mListContacts)) {
                    mListContacts.add(entityEmpty);
                }
                if (position == mListContacts.size() - 1) {
                    mListContacts.remove(mListContacts.size() - 1);
                    Intent intent = new Intent(PublishMessageActivity.this, ContactsChoseActivity.class);
                    intent.putExtra("type", 1);//0-查看 1-多选 2-单选
                    intent.putExtra("list", mListContacts);
                    startActivityForResult(intent, FLAG_ADDUSER);
                }
            }
        });
    }

    @Override
    public void initRight() {
        setRight1("确定");
        setRight1Icon(R.mipmap.complete2);
        setRight1Show(true);
        setRight2Show(false);
        super.initRight();
    }

    @Override
    public void actionRight1(MenuItem menuItem) {
        if (StringUtils.isEmpty(etTitle)) {
            MyToast.showToast("请输入消息标题");
            return;
        }
        if (mListContacts.size() == 0) {
            MyToast.showToast("请至少选择一个通知人");
            return;
        }


//      这里获取通知人的ID
        if (!StringUtils.isEmpty(listInformer)) {
            Gson gson = new Gson();
            userIds = gson.toJson(listInformer);

//            userIds = JsonUtil.toJsonString(listInformer);
        } else {
            userIds = null;
        }

//      在这个部分进行文件的上传
        if (fileList.size() > 0) {
            uploadFile(PublishMessageActivity.this, fileList);
        }

        if (lists.size() >= 1) {
            uploadPicture(PublishMessageActivity.this, lists);
        }

//        获取富文本中的内容
        mWebviewState = 1;
        //获取内容
        etContent.loadUrl("javascript:getHTML()");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //0--没开始加载  1--加载中  2--加载完成
                while (mWebviewState == 1) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mWebviewState = 1;
                handler.sendEmptyMessage(1);
            }
        }).start();


    }


    /**
     * 更新照片list
     *
     * @param photoList
     * @param entity
     * @return
     */
    private ArrayList<PhotosEntity> updatePhotos(ArrayList<PhotosEntity> photoList, PhotosEntity entity) {
        if (!StringUtils.isEmpty(photoList)) {
        } else {
            photoList = new ArrayList<PhotosEntity>();
        }
        List<PhotosEntity> listPhotosTemp = new ArrayList<PhotosEntity>();
        listPhotosTemp.addAll(photoList);
        photoList.clear();
        if (null != entity) {
            listPhotosTemp.add(entity);
        }

        photoList.addAll(listPhotosTemp);
        return photoList;
    }


    private ICustomListener listener = new ICustomListener() {
        @Override
        public void onCustomListener(int obj0, Object obj1, int position) {
            switch (obj0) {
                case 0://刪除
                    lists.remove(position);
                    mSelectPath.remove(position);
                    lists = updatePhotos(lists, null);
                    PhotoListAdapter photoListAdapter = new PhotoListAdapter(PublishMessageActivity.this, lists, R.layout.list_item_select_photos, listener, true);
                    mGridViewImage.setAdapter(photoListAdapter);
                    break;
                case 1://添加
                    pickImage();
                    break;
                case 3://显示大图
                    ArrayList<String> list_Ads = new ArrayList<>();
                    for (int j = 0; j < lists.size(); j++) {
                        list_Ads.add(lists.get(j).getImgurl());
                    }
                    Intent intent = new Intent(PublishMessageActivity.this, PhotoDetailActivity.class);
                    intent.putExtra("download", false);
                    intent.putExtra("list", list_Ads);
                    intent.putExtra("position", position);
                    startActivity(intent);
                    break;
                case 4://添加
                    showDialog();
                    break;
                default:
                    break;
            }
        }
    };

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

            MultiImageSelector selector = MultiImageSelector.create(PublishMessageActivity.this);
            selector.showCamera(showCamera);
            selector.count(maxNum);
            if (isMulti) {
                selector.multi();
            } else {
                selector.single();
            }
            selector.origin(mSelectPath);
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
                            ActivityCompat.requestPermissions(PublishMessageActivity.this, new String[]{permission}, requestCode);
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
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    /**
     * 更新 通知人 list
     *
     * @param contactList
     * @param entity
     * @return
     */
    private ArrayList<ContactsEntity> updateContacts(ArrayList<ContactsEntity> contactList, ContactsEntity entity) {
        if (!StringUtils.isEmpty(contactList)) {
            mListContacts.remove(entity);
        } else {
            contactList = new ArrayList<ContactsEntity>();
        }
        List<ContactsEntity> listContactTemp = new ArrayList<ContactsEntity>();
        listContactTemp.addAll(contactList);
        contactList.clear();
        if (null != entity) {
            listContactTemp.add(entityEmpty);
        }
        contactList.addAll(listContactTemp);
        contactList.add(entityEmpty);
        return contactList;
    }


    //发布消息
    public void SendUserMessage() {
        Call call = HttpRequest.getIResource().SendUserMessage("[{\"UserId\":\"26814966-09ec-47b8-8709-cc5b6997cfd1\"}]", mContentStr, etTitle.getText().toString(), mGuid);
        callRequest(call, new HttpCallBack(AttachmentGetEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    MyToast.showToast("发布消息成功");
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

    /**
     * 上传文件
     */
    public void uploadFile(Context context, ArrayList<PhotosEntity> list) {
        Map<String, String> map = new HashMap<String, String>();
        //附件集合
        Map<String, File> mapFile = new HashMap<>();

        ssid = PreferencesUtils.getString(context, "ssid");
        map.put("tokenid", ssid);
        map.put("guId", mGuid);
        for (int i = 0; i < list.size() - 1; i++) {
            File file = new File(list.get(i).getImgurl());
            if (file == null || !file.exists()) {
                break;
            }
            File fileSmall = null;
            try {
                String filePathStr = FileAccessor.getSmallPicture(file.getPath()); // 压缩文件
                fileSmall = new File(filePathStr);
            } catch (Exception e) {
                System.out.println("上传图片错误：" + e.toString());
                e.printStackTrace();
                fileSmall = file;
            }
            mapFile.put("image" + i, fileSmall);
        }

        String server = ConfigUtils.getConfig(ConfigUtils.KEY.server);
        new BaseAsyncTaskFile(context, true, 0, server + "resources/file/post?userId=32&&userName=3423", new BaseAsyncTaskInterface() {
            @Override
            public void dataSubmit(int funId) {

            }

            @Override
            public Object dataParse(int funId, String json) throws Exception {
                return Result.parse(json);
            }

            @Override
            public void dataSuccess(int funId, Object result) {
                Result mResult = (Result) result;
                if ("0".equals(mResult.getSuccess())) {//0成功
//                    SendUserMessage();
                    MyToast.showToast("上传成功");
                } else {
                    String message = mResult.getMsg();
                    MyToast.showToast(message);
                }
            }

            @Override
            public void dataError(int funId) {
            }
        }, mapFile).execute(map);
    }


    /**
     * 上传图片
     */
    public void uploadPicture(Context context, ArrayList<PhotosEntity> list) {
        Map<String, String> map = new HashMap<String, String>();
        //附件集合
        Map<String, File> mapFile = new HashMap<>();
        ssid = PreferencesUtils.getString(context, "ssid");
        map.put("tokenid", ssid);
        map.put("guId", mGuid);
        for (int i = 0; i < list.size() - 1; i++) {
            File file = new File(list.get(i).getImgurl());
            if (file == null || !file.exists()) {
                break;
            }
            File fileSmall = null;
            try {
                String filePathStr = FileAccessor.getSmallPicture(file.getPath()); // 压缩文件
                fileSmall = new File(filePathStr);
            } catch (Exception e) {
                System.out.println("上传图片错误：" + e.toString());
                e.printStackTrace();
                fileSmall = file;
            }
            mapFile.put("image" + i, fileSmall);
        }

        String server = ConfigUtils.getConfig(ConfigUtils.KEY.server);
        new BaseAsyncTaskFile(context, true, 0, server + "resources/file/post?userId=32&&userName=3423", new BaseAsyncTaskInterface() {
            @Override
            public void dataSubmit(int funId) {

            }

            @Override
            public Object dataParse(int funId, String json) throws Exception {
                return Result.parse(json);
            }

            @Override
            public void dataSuccess(int funId, Object result) {
                Result mResult = (Result) result;
                if ("0".equals(mResult.getSuccess())) {//0成功
                } else {
                    String message = mResult.getMsg();
                    MyToast.showToast(message);
                }
            }

            @Override
            public void dataError(int funId) {
            }
        }, mapFile).execute(map);
    }


    /**
     * 回调需要接收的人员
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            if (resultCode == FLAG_ADDUSER) {
                mListContacts.add(entityEmpty);
                informerAdapter.notifyDataSetChanged();
            }
            return;
        }
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                PhotosEntity entity = null;
                lists.clear();
                for (String p : mSelectPath) {
                    sb.append(p);
                    sb.append("\n");
                    entity = new PhotosEntity();
                    entity.setAppname("");
                    entity.setId("");
                    entity.setImgurl(p);
                    entity.setImgurlNet(p);
                    lists = updatePhotos(lists, entity);
                }
                PhotoListAdapter photoListAdapter = new PhotoListAdapter(this, lists, R.layout.list_item_select_photos, listener, true);
                mGridViewImage.setVisibility(View.VISIBLE);
                mGridViewImage.setAdapter(photoListAdapter);
            }
        }
        if (requestCode == 0) {//选择文件的处理
            ArrayList<String> list = data.getStringArrayListExtra(FileSelector.RESULT);
            if (list != null || list.size() >= 1) {
                String filePath = list.get(0);
                String fileNameString = FileUtils.getFileName(filePath);

                View view = LayoutInflater.from(this).inflate(R.layout.listi_item_fileselector, null, false);
                view.setTag(list.size());
                ImageView deleteFile = (ImageView) view.findViewById(R.id.iv_delete);
                TextView fileName = (TextView) view.findViewById(R.id.tv_fileName);
                ImageView fileIcon = (ImageView) view.findViewById(R.id.iv_fileType);
                deleteFile.setVisibility(View.GONE);
//                deleteFile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (llFile.getChildCount() >= 1) {
//                            llFile.setVisibility(View.VISIBLE);
//                        } else {
//                            llFile.setVisibility(View.GONE);
//                        }
//                        fileList.remove(v.getTag());
//                        llFile.removeView((View) v.getParent());
//                    }
//                });
                fileName.setText(fileNameString);
                PhotosEntity entity = new PhotosEntity();
                entity.setImgurl(filePath);
                fileList.add(entity);

                if (!StringUtils.isEmpty(fileNameString)) {
                    if (fileNameString.endsWith("png") || fileNameString.endsWith("jpg") || fileNameString.endsWith("jpeg")) {
                        fileIcon.setImageURI(Uri.fromFile(new File(filePath)));
                    } else if (fileNameString.endsWith("doc") || fileNameString.endsWith("docx")) {
                        fileIcon.setImageResource(R.mipmap.attachment_doc);
                    } else if (fileNameString.endsWith("pdf")) {
                        fileIcon.setImageResource(R.mipmap.attachment_pdf);
                    } else if (fileNameString.endsWith("xlsx") || fileNameString.endsWith("xls")) {
                        fileIcon.setImageResource(R.mipmap.attachment_xel);
                    } else {
                        fileIcon.setImageResource(R.mipmap.attachment_other);
                    }
                }
                llFile.setVisibility(View.VISIBLE);
                llFile.addView(view);
            }
        }
        if (requestCode == FLAG_ADDUSER) {
            userIds = "";
            mListContacts = (ArrayList<ContactsEntity>) data.getExtras().getSerializable(ContactsChoseActivity.Flag_Output_Checked);
            mListContacts.add(entityEmpty);
            listInformer = new ArrayList<>();
            for (int i = 0; i < mListContacts.size(); i++) {
                InformerEntity entity = new InformerEntity();
                entity.setUserId(mListContacts.get(i).getUserId());
                listInformer.add(entity);
            }
            informerAdapter = new InformerAdapter(mListContacts, this);
            mGridViewPeople.setAdapter(informerAdapter);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        window.setContentView(R.layout.dialog_photofile);
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
                pickImage();
            }
        });
        LinearLayout llDoc = (LinearLayout) window.findViewById(R.id.ll_doc);
        llDoc.setOnClickListener(new View.OnClickListener() {//选择文档
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), FileSelectorActivity.class);
                fileConfig.startPath = Environment.getExternalStorageDirectory().getPath();
                fileConfig.rootPath = "/";
                intent.putExtra(FileConfig.FILE_CONFIG, fileConfig);
                startActivityForResult(intent, 0);
            }
        });
    }

    @OnClick(R.id.iv_pickPhotoFile)
    public void onClick() {
        showDialog();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://
//                    postUpdateSchedule();
                    SendUserMessage();

                    break;
                case 3://
                    //设置内容
//                    暂时不添加这个功能
                    break;
            }
        }
    };
}

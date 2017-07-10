package com.zbxn.main.activity.steward;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.PhotoViewPager;
import com.pub.widget.fileselector.utils.FileUtils;
import com.pub.widget.photoview.PhotoView;
import com.zbxn.R;
import com.zbxn.main.entity.AdjunctEntity;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PhotoDetailActivity extends Activity {
    @BindView(R.id.ll_downloadPic)
    LinearLayout llDownloadPic;
    @BindView(R.id.ll_deletePic)
    LinearLayout llDeletePic;
    @BindView(R.id.v_delete)
    View v_delete;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.m_layout)
    RelativeLayout mLayout;

    private RelativeLayout m_layout;
    private PhotoViewPager mViewPager;
    private ArrayList<String> list_Ads = new ArrayList<String>(); // 需要展示的图片
    private ArrayList<AdjunctEntity.Attachment> list = new ArrayList<>();
    private int position = 0;
    private ImageView imageViewResource;
    private TextView m_tip;
    private TextView m_finish;
    private String string = "";
    private boolean downloadOrNot = false;
    private int picNum;
    private MyAdapter myAdapter;
    private boolean deletePic = false;
    private int stateId;
    private boolean isFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steward_photodetail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        downloadOrNot = getIntent().getBooleanExtra("download", false);
        deletePic = getIntent().getBooleanExtra("delete", false);
        stateId = getIntent().getIntExtra("state", 0);
        isFile = getIntent().getBooleanExtra("file", false);
        if (deletePic) {
            v_delete.setVisibility(View.VISIBLE);
            llDeletePic.setVisibility(View.VISIBLE);
        } else {
            v_delete.setVisibility(View.GONE);
            llDeletePic.setVisibility(View.GONE);
        }

        if (stateId == 2 || stateId == 1) {
            v_delete.setVisibility(View.GONE);
            llDeletePic.setVisibility(View.GONE);
        }

        if (!downloadOrNot) {
            llDownloadPic.setVisibility(View.GONE);
        }
        if (!downloadOrNot && !deletePic) {
            llDownloadPic.setVisibility(View.GONE);
            llDeletePic.setVisibility(View.VISIBLE);
            tvDelete.setText("确定");
            tvDelete.setCompoundDrawables(null, null, null, null);
        }
        imageViewResource = new ImageView(this);
        list_Ads = getIntent().getStringArrayListExtra("list");
        if (!StringUtils.isEmpty(list_Ads)) {
            for (int i = 0; i < list_Ads.size(); i++) {
                AdjunctEntity.Attachment attachment = new AdjunctEntity.Attachment();
                attachment.setFileUrl(list_Ads.get(i));
                list.add(attachment);
            }
        }
        if (StringUtils.isEmpty(list)) {
            list = getIntent().getParcelableArrayListExtra("lists");
        }
        if (isFile) {
            tvName.setText(list.get(0).getFileName());
        }
        position = getIntent().getIntExtra("position", 0);
        myAdapter = new MyAdapter();
        if (StringUtils.isEmpty(list.toString())) {
            MyToast.showToast("没有获取到图片资源");
            finish();
        } else {
            m_layout = (RelativeLayout) findViewById(R.id.m_layout);
            mViewPager = (PhotoViewPager) findViewById(R.id.viewpager);
            m_tip = (TextView) findViewById(R.id.m_tip);
            m_finish = (TextView) findViewById(R.id.m_finish);
            m_tip.setText((position + 1) + "/" + String.valueOf(list.size()));
            mViewPager.setAdapter(myAdapter);
            mViewPager.setCurrentItem(position);

            m_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            mViewPager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            m_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                // 页面更变
                @Override
                public void onPageSelected(int arg0) {
                    picNum = arg0;
                    m_tip.setText(String.valueOf(arg0 + 1) + "/" + String.valueOf(list.size()));
                    if (isFile) {
                        tvName.setText(list.get(arg0).getFileName());
                    }
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            });
        }

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

    public void fileScan(String fName) {
        Uri data = Uri.parse("file:///" + fName);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
//        方式一，发送一个广播，
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + getSDPath())));
//        方式二，通过MediaScannerConnection 类
        MediaScannerConnection.scanFile(this, new String[]{getSDPath().toString()}, null, null);
    }

    /**
     * 获取sd卡的缓存路径，
     * 一般在卡中sdCard就是这个目录
     *
     * @return SDPath
     */
    public File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();// 获取根目录
            sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);// 获取根目录\
        } else {
            MyToast.showToast("手机没有SD卡");
        }
        return sdDir;
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
        File tmpFile = new File(newFile, getFileName() + ".png");
        try {
            tmpFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(tmpFile);
            bitmap1.compress(Bitmap.CompressFormat.PNG, 50, fos);
            MyToast.showToast("图片保存至Android/data/com.zbxn/cache");
            fileScan(string);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            MyToast.showToast("保存失败");
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
            MyToast.showToast("保存失败");
        }
        return tmpFile;
    }

    public File getFilePath(Context context, String filePath) {
        String state = Environment.getExternalStorageState();
        File file;
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            file = context.getExternalCacheDir();
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
        return newFile;
    }


    @OnClick({R.id.tv_download, R.id.ll_downloadPic, R.id.ll_deletePic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_download:
            case R.id.ll_downloadPic:
//                storeInSD(mViewPager.getChildAt(0).getDrawingCache());
                if (!isFile) {
                    Log.d("okhttp", picNum + "");
                    downloadFile(this, "", mViewPager.getChildAt(picNum).getDrawingCache());
                } else {
                    String fileType = FileUtils.getFileLastName(list.get(picNum).getFileUrl());
                    if (fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png")){
                        downloadFile(this, "", mViewPager.getChildAt(picNum).getDrawingCache());
                    }else {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(list.get(picNum).getDownloadPath());
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_deletePic:
                if (!downloadOrNot && !deletePic) {
                    setResult(RESULT_OK);
                    finish();
                    break;
                }
                String fileId = list.get(picNum).getFileId();
                list.remove(picNum);
                EventCustom eventCustom = new EventCustom();
                eventCustom.setTag(EventKey.IMAGEDELETEUPDATE);
                eventCustom.setObj(fileId);
                EventBus.getDefault().post(eventCustom);
                if (list.size() != 0) {
                    m_tip.setText(String.valueOf(picNum + 1) + "/" + String.valueOf(list.size()));
                }
                if (isFile) {
                    if (picNum == list.size()) {
                        if (picNum != 0) {
                            tvName.setText(list.get(picNum - 1).getFileName());
                        }
                    } else {
                        tvName.setText(list.get(picNum).getFileName());
                    }
                }
                myAdapter.notifyDataSetChanged();
                break;
        }
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            PhotoView imageView = new PhotoView(PhotoDetailActivity.this);
            imageView.setMaximumScale(3.0f);
            imageView.setMinimumScale(1.0f);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setAdjustViewBounds(true);
            imageViewResource.setImageDrawable(imageView.getDrawable());

            // 设置图片
            if (list.get(position).getFileUrl().contains("http")) {
                if (!isFile) {
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showStubImage(R.mipmap.picdown_fail)          // 设置图片下载期间显示的图片
                            .showImageForEmptyUri(R.mipmap.picdown_fail)  // 设置图片Uri为空或是错误的时候显示的图片
                            .showImageOnFail(R.mipmap.picdown_fail)       // 设置图片加载或解码过程中发生错误显示的图片
                            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                            .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                            .imageScaleType(ImageScaleType.NONE)
                            //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                            .build();                                   // 创建配置过得DisplayImageOption对象
                    ImageLoader.getInstance().displayImage(list.get(position).getFileUrl(), imageView, options);
                } else {
                    setImage(imageView, position);
                }
            } else {
                try {
//                    标红的代码如不设置，则会使用默认的设置，为ImageScaleType.IN_SAMPLE_POWER_OF_2
//                    imageScaleType(ImageScaleType imageScaleType)
//                    imageScaleType:
//                    EXACTLY :图像将完全按比例缩小的目标大小
//                    EXACTLY_STRETCHED:图片会缩放到目标大小完全
//                    IN_SAMPLE_INT:图像将被二次采样的整数倍
//                    IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
//                    NONE:图片不会调整
//                    MyToast.showToast(list.get(position).getFileUrl() + "");
                    if (!isFile) {
                        DisplayImageOptions options = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.picdown_fail)          // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.picdown_fail)  // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.picdown_fail)       // 设置图片加载或解码过程中发生错误显示的图片
                                //禁用缓存防止拍照预览时只显示第一次拍的照片
                                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(false)// 设置下载的图片是否缓存在SD卡中
                                .imageScaleType(ImageScaleType.NONE)
                                //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                                .build();                                   // 创建配置过得DisplayImageOption对象
                        ImageLoader.getInstance().displayImage("file:///" + list.get(position).getFileUrl(), imageView, options);
                    } else {
                        setImage(imageView, position);
                    }
                } catch (Exception e) {
                }
            }
            container.addView(imageView, LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            return imageView;
        }

        public void setImage(ImageView imageView, int position) {
            String fileType = FileUtils.getFileLastName(list.get(position).getFileUrl());
            if (fileType.equals("xlsx") || fileType.equals("xls") || fileType.equals("xlsm") || fileType.equals("xltx")
                    || fileType.equals("xltm") || fileType.equals("xlsb") || fileType.equals("xlam")) {
                imageView.setImageResource(R.mipmap.attachment_xel);
            } else if (fileType.equals("doc") || fileType.equals("docx") || fileType.equals("docm") || fileType.equals("dotx")
                    || fileType.equals("dotm")) {
                imageView.setImageResource(R.mipmap.attachment_doc);
            } else if (fileType.equals("pdf")) {
                imageView.setImageResource(R.mipmap.attachment_pdf);
            } else if (fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png")) {
                tvName.setVisibility(View.GONE);
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showStubImage(R.mipmap.picdown_fail)          // 设置图片下载期间显示的图片
                        .showImageForEmptyUri(R.mipmap.picdown_fail)  // 设置图片Uri为空或是错误的时候显示的图片
                        .showImageOnFail(R.mipmap.picdown_fail)       // 设置图片加载或解码过程中发生错误显示的图片
                        .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                        .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                        .imageScaleType(ImageScaleType.NONE)
                        //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                        .build();                                   // 创建配置过得DisplayImageOption对象
                ImageLoader.getInstance().displayImage(list.get(position).getFileUrl(), imageView, options);
            } else {
                imageView.setImageResource(R.mipmap.attachment_other);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((PhotoViewPager) container).removeView((View) object);
        }

        private int mChildCount;

        //使PagerAdapter可以刷新
        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount >= 0) {
                mChildCount--;
                if (mChildCount == -1 && getCount() == 0) {
                    finish();
                }
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
    }
}


//    private void storeInSD(Bitmap bitmap1) {
//        File file = new File(getSDPath());
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        string = getFileName();
//        File imageFile = new File(file, string + ".png");
//        try {
//            imageFile.createNewFile();
//            FileOutputStream fos = new FileOutputStream(imageFile);
//            bitmap1.compress(Bitmap.CompressFormat.PNG, 50, fos);
//            MyToast.showToast("保存成功");
////            notifyFileSystemChanged(getSDPath());
//            fileScan(string);
//            fos.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            MyToast.showToast("保存失败");
//        } catch (IOException e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//            MyToast.showToast("保存失败");
//        }
//    }
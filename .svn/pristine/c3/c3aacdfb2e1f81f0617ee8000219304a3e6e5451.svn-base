package com.zbxn.main.activity.memberCenter;

import android.os.Bundle;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pub.base.BaseActivity;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.zbxn.R;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ClipImageViewActivity extends BaseActivity {

    @BindView(R.id.cropImageView)
    CropImageView cropImageView;
    private String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membercenter_clipimageview);
        ButterKnife.bind(this);
        setTitle("头像设置");
        cropImageView.setHandleShowMode(CropImageView.ShowMode.SHOW_ALWAYS);
//        cropImageView.setGuideShowMode(CropImageView.ShowMode.SHOW_ALWAYS);   如果设置了这个属性，虚线就会一直显示
        cropImageView.setmIsScaling(true);


        cropImageView.setCropMode(CropImageView.CropMode.RATIO_1_1);
        path = getIntent().getStringExtra("path");
//        cropImageView.setImageBitmap();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.NONE)
                //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                .build();                                   // 创建配置过得DisplayImageOption对象
        ImageLoader.getInstance().displayImage("file:///" + path, cropImageView, options);
    }

    @Override
    public void initRight() {
        super.initRight();
        setRight1Show(true);
        setRight1Icon(R.mipmap.complete2);
    }

    @Override
    public void actionRight1(MenuItem menuItem) {
        super.actionRight1(menuItem);
        AppController.cropped = cropImageView.getCroppedBitmap();
        EventCustom eventCustom = new EventCustom();
        eventCustom.setTag(EventKey.ClipImage);
        EventBus.getDefault().post(eventCustom);
        finish();
    }
}

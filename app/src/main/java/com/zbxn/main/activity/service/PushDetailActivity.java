package com.zbxn.main.activity.service;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.zbxn.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推送内容详情
 *
 * @author: ysj
 * @date: 2016-10-11 14:08
 */
public class PushDetailActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;

    private String mContent = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushdetail);
        ButterKnife.bind(this);

        mContent = getIntent().getStringExtra("Content");

        setTitle("长按复制内容");

        tvContent.setText(mContent);
    }
}

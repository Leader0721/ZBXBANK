package com.zbxn.main.activity.steward;

import android.content.Intent;
import android.os.Bundle;

import com.pub.base.BaseActivity;
import com.zbxn.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/13.
 */
public class StewardActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steward);
        ButterKnife.bind(this);
    }

    @Override
    public void initRight() {
        super.initRight();
        setTitle("信贷管理");
    }

    @OnClick(R.id.ll_customList)
    public void onClick() {
        startActivity(new Intent(this, CustomListActivity.class));
    }
}

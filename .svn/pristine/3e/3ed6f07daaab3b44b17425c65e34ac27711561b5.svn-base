package com.zbxn.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public class FinalFragmentAdapter extends FragmentPagerAdapter {
    //声明一个全局变量来接收
    private List<Fragment> mdata;

    public FinalFragmentAdapter(FragmentManager fm, List<Fragment> mdata) {
        super(fm);
        this.mdata = mdata;
    }

    @Override
    public Fragment getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public int getCount() {
        return mdata != null ? mdata.size() : 0;
    }
}

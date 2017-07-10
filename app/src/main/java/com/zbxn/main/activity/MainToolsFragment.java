package com.zbxn.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pub.base.BaseFragment;
import com.zbxn.R;

public class MainToolsFragment extends BaseFragment {

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_main_tools, container, false);

    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {

    }

}

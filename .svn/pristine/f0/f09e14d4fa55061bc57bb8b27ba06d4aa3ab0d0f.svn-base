package com.zbxn.main.activity.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.pub.base.BaseApp;
import com.pub.base.BaseFragment;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.entity.UserEntity;
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.umeng.analytics.MobclickAgent;
import com.zbxn.R;
import com.zbxn.main.adapter.DynamicAdapter;
import com.zbxn.main.entity.DynamicEntity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 动态fragment
 * 应用消息
 *
 * @author: ysj
 * @date: 2017-03-09 16:03
 */
public class DynamicFragment extends BaseFragment {

    @BindView(R.id.mListView)
    PullRefreshListView mListView;
    private List<DynamicEntity> mList;
    private DynamicAdapter mAdapter;

    public void setFragmentTitle(String title) {
        mTitle = title;
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
    }

    //友盟统计
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    private void initData() {
        mList.add(new DynamicEntity("", "系统", 0));
//        mList.add(new DynamicEntity("", "消息", 1));
        mList.add(new DynamicEntity("", "贷款申请", 12));//这俩随意写的
//        mList.add(new DynamicEntity("", "信贷管理", 13));
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mList = new ArrayList<>();
        mAdapter = new DynamicAdapter(getContext(), mList);
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnabled(false);
        mListView.setDividerHide();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (mList.get(position).getType()) {
                    case 0:
                        intent = new Intent(getContext(), MessageListActivity.class);
                        intent.putExtra("type", mList.get(position).getType());
                        startActivity(intent);
                        break;
                    case 12:
                    case 13:
                        intent = new Intent(getContext(), BankMessageActivity.class);
                        intent.putExtra("type", mList.get(position).getType());
                        startActivity(intent);
                        break;
                }

            }
        });
    }

    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (EventKey.PERMISSIONS.equals(eventCustom.getTag())) {
            if (null != BaseApp.mUserEntity) {
                for (int i = 0; i < BaseApp.mUserEntity.getPermission().size(); i++) {
                    UserEntity.PermissionBean permissionBean = BaseApp.mUserEntity.getPermission().get(i);
                    for (int j = 0; j < permissionBean.getPermissoin().size(); j++) {
                        if ("03".equals(permissionBean.getPermissoin().get(j).getCode())) {
                            addItem();
                            return;
                        }
                    }
                }
            }
        }
    }

    private void addItem() {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getType() == 13) {
                return;
            }
        }
        mList.add(new DynamicEntity("", "信贷管理", 13));
        mAdapter.notifyDataSetChanged();
    }
}

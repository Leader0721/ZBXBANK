package com.zbxn.main.activity.memberCenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.zbxn.R;
import com.zbxn.main.adapter.MemberCenterCompanyAdapter;
import com.zbxn.main.entity.CompanyEntity;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class CompanyActivity extends BaseActivity {

    @BindView(R.id.mListView)
    PullRefreshListView mListView;
    private List<CompanyEntity> mList;
    private MemberCenterCompanyAdapter adapter;
    private String userId;
    private int FLAG_CREATECOMPANY = 1000;
    private int FLAG_EXITCOMPANY = 1001;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membercenter_company);
        ButterKnife.bind(this);
        initView();
        GetCompanyListByUID();
    }

    private void initView() {
        mList = new ArrayList<>();
        adapter = new MemberCenterCompanyAdapter(this, mList);
        mListView.setAdapter(adapter);
        mListView.setImageEmptyLoading("加载中...");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                Intent intent = new Intent(BaseApp.getContext(), CompanyDetailActivity.class);
                intent.putExtra("id", mList.get(position).getID());
                startActivityForResult(intent,FLAG_EXITCOMPANY);
            }
        });
        mListView.setPullLoadEnabled(false);
        mListView.setOnPullListener(new PullRefreshListView.OnPullListener() {
            @Override
            public void onRefresh() {
                GetCompanyListByUID();
            }

            @Override
            public void onLoad() {
                GetCompanyListByUID();
            }
        });
    }

    @Override
    public void initRight() {
        super.initRight();
        setTitle("我的企业");
        setRight1("添加企业");
        setRight1Icon(R.mipmap.menu_creat_blog);
        setRight1Show(true);
    }

    @Override
    public void actionRight1(MenuItem menuItem) {
        super.actionRight1(menuItem);
        startActivityForResult(new Intent(CompanyActivity.this, CompanyCreateActivity.class), FLAG_CREATECOMPANY);
    }

    /**
     * 获取公司列表
     */
    public void GetCompanyListByUID() {
        //请求网络
        Call call = HttpRequest.getIResource().GetCompanyListByUserId();
        callRequest(call, new HttpCallBack(CompanyEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                mListView.onComplete();
                if ("1".equals(mResult.getSuccess())) {//0成功
                    List<CompanyEntity> childList = mResult.getDataList();
                    if (childList == null) {
                        childList = new ArrayList<>();
                    }
                    mList.clear();
                    mList.addAll(childList);
                    adapter.notifyDataSetChanged();
                    EventCustom eventCustom = new EventCustom();
                    eventCustom.setTag(EventKey.COMPANYREFRESH);
                    EventBus.getDefault().post(eventCustom);
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
                mListView.setImageEmpty(R.mipmap.loading_shuju,"暂时没有数据哦");
            }

            @Override
            public void onFailure(String string) {
                mListView.onComplete();
                MyToast.showToast(R.string.NETWORKERROR);
                mListView.setImageEmpty(R.mipmap.loading_shuju,"暂时没有数据哦");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FLAG_CREATECOMPANY) {
                GetCompanyListByUID();
            }
            if (requestCode==FLAG_EXITCOMPANY){
                mList.remove(mPosition);
                adapter.notifyDataSetChanged();
            }
        }

    }
}

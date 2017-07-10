package com.zbxn.main.activity.credit.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pub.base.BaseFragment;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.zbxn.R;
import com.zbxn.main.adapter.CreditApplyAdapter;
import com.zbxn.main.entity.CreditApplyEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * @author: ysj
 * @date: 2017-04-05 17:05
 */
public class CreditApplyFragment extends BaseFragment {

    @BindView(R.id.mListView)
    PullRefreshListView mListView;
    private String searchStr;

    private List<CreditApplyEntity> mList;
    private CreditApplyAdapter mAdapter;

    private int mPage = 1;
    private int mPageSize = 10;

    //记录状态
    private int state = -1;
    //记录银行id
    private String bankId = "";

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_credit_apply, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {
        initData();
        initView();
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new CreditApplyAdapter(getContext(), mList, 1);
        mListView.setAdapter(mAdapter);
        if (getArguments() != null) {
            bankId = getArguments().getString("bankId");
            state = getArguments().getInt("state", -1);
        }
    }

    private void initView() {
        mListView.setOnPullListener(new PullRefreshListView.OnPullListener() {
            @Override
            public void onRefresh() {
                setRefresh();
            }

            @Override
            public void onLoad() {
                getMyApplyList();
            }
        });
    }

    public void setSearch(String search) {
        searchStr = search;
        mListView.startFirst();
        setRefresh();
    }

    public void setRefresh() {
        mPage = 1;
        getMyApplyList();
    }

    public void getMyApplyList() {
        String stateStr;
        if (state == -1) {
            stateStr = "";
        } else {
            stateStr = state + "";
        }
        Call call = HttpRequest.getIResource().getMyApplyList(bankId, stateStr, searchStr, mPage + "", mPageSize + "");
        callRequest(call, new HttpCallBack(CreditApplyEntity.class, getContext(), false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    List<CreditApplyEntity> list = mResult.getDataList();
                    if (mPage == 1) {
                        mList.clear();
                    }
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    mPage++;
                    setMore(list);
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
                mListView.onRefreshFinish();
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }

    /**
     * 显示加载更多
     *
     * @param mResult
     */
    private void setMore(List mResult) {
        if (mResult == null) {
            mListView.setHasMoreData(true);
            return;
        }
        int pageTotal = mResult.size();
        if (pageTotal >= mPageSize) {
            mListView.setHasMoreData(true);
            mListView.setPullLoadEnabled(true);
        } else {
            mListView.setHasMoreData(false);
            mListView.setPullLoadEnabled(false);
        }
    }

}

package com.zbxn.main.activity.credit.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.pub.base.BaseFragment;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.zbxn.R;
import com.zbxn.main.activity.credit.CreditProgressActivity;
import com.zbxn.main.adapter.CreditApplyAdapter;
import com.zbxn.main.entity.CreditApplyEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * @author: ysj
 * @date: 2017-04-10 15:40
 */
public class CreditDraftFragment extends BaseFragment {
    @BindView(R.id.mListView)
    PullRefreshListView mListView;

    private int mPage = 1;
    private int mPageSize = 10;

    private List<CreditApplyEntity> mList;
    private CreditApplyAdapter mAdapter;

    private String searchContext;
    private String bankId;

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_credit_draft, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {
        initData();
        initView();
    }

    public void setSearch(String search) {
        searchContext = search;
        mListView.startFirst();
        setRefresh();
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new CreditApplyAdapter(getContext(), mList, 2);
        mListView.setAdapter(mAdapter);
        if (getArguments() != null) {
            bankId = getArguments().getString("bankId");
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
                getDraftList();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CreditProgressActivity.class);
                intent.putExtra("From", "3");//1新建  2查看申请  3草稿箱
                intent.putExtra("LoanApplyId", mList.get(position).getLoanApplyId());
                intent.putExtra("VersionId", mList.get(position).getVersionId());
                startActivity(intent);
            }
        });
    }

    public void setRefresh() {
        mPage = 1;
        getDraftList();
    }

    public void getDraftList() {
        Call call = HttpRequest.getIResource().getDraftList(mPage + "", mPageSize + "", searchContext, bankId);
        callRequest(call, new HttpCallBack(CreditApplyEntity.class, getContext(), false) {
            @Override
            public void onSuccess(ResultData mResult) {
                mListView.onRefreshFinish();
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
            }

            @Override
            public void onFailure(String string) {
                mListView.onRefreshFinish();
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

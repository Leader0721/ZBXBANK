package com.zbxn.main.activity.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.pub.base.BaseActivity;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.zbxn.R;
import com.zbxn.main.activity.credit.CreditProgressActivity;
import com.zbxn.main.activity.steward.CustomInfoActivity;
import com.zbxn.main.activity.steward.FinaExcelActivity;
import com.zbxn.main.adapter.BankMessageAdapter;
import com.zbxn.main.entity.BankMessageEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * @author: ysj
 * @date: 2017-04-16 14:46
 */
public class BankMessageActivity extends BaseActivity {

    @BindView(R.id.mListView)
    PullRefreshListView mListView;

    private BankMessageAdapter mAdapter;
    private List<BankMessageEntity> mList;

    private int mPage = 1;
    private int mPageSize = 10;
    private int type;
    private int iconType;
    private String creatTime;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_message);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        creatTime = sdf.format(new Date());
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 12:
                setTitle("贷款申请");
                iconType = 0;
                break;
            case 13:
                setTitle("信贷管理");
                iconType = 1;
                break;
        }
        mList = new ArrayList<>();
        mAdapter = new BankMessageAdapter(this, mList, iconType);
        mListView.setAdapter(mAdapter);
    }

    private void initView() {
        mListView.setOnPullListener(new PullRefreshListView.OnPullListener() {
            @Override
            public void onRefresh() {
                setRefresh();
            }

            @Override
            public void onLoad() {
                getMessageList();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (mList.get(position).getMessageType()) {
                    case 5://新的单款申请消息提示
                        intent = new Intent(BankMessageActivity.this, CustomInfoActivity.class);
                        intent.putExtra("LoanApplyId", mList.get(position).getLoanApplyId());
                        intent.putExtra("VersionId", mList.get(position).getVersionId());
                        intent.putExtra("Phone", mList.get(position).getContacTel());
                        startActivity(intent);
                        break;
                    case 6://报表收取提醒
                        intent = new Intent(BankMessageActivity.this, FinaExcelActivity.class);
                        intent.putExtra("bank", true);
                        intent.putExtra("LoanApplyId", mList.get(position).getLoanApplyId());
                        intent.putExtra("Phone", mList.get(position).getContacTel());
                        startActivity(intent);
                        break;
                    case 7://贷款申请批复
                        intent = new Intent(BankMessageActivity.this, CreditProgressActivity.class);
                        intent.putExtra("From", "2");
                        intent.putExtra("LoanApplyId", mList.get(position).getLoanApplyId());
                        intent.putExtra("VersionId", mList.get(position).getVersionId());
                        intent.putExtra("StatusCode", mList.get(position).getStatusId());
                        startActivity(intent);
                        break;
                    case 8://提交报表提醒
                        intent = new Intent(BankMessageActivity.this, FinaExcelActivity.class);
                        intent.putExtra("bank", false);
                        intent.putExtra("LoanApplyId", mList.get(position).getLoanApplyId());
                        intent.putExtra("Phone", mList.get(position).getContacTel());
                        startActivity(intent);
                        break;
                }
            }
        });

        mListView.startFirst();
        setRefresh();
    }

    private void setRefresh() {
        mPage = 1;
        creatTime = sdf.format(new Date());
        getMessageList();
    }

    /**
     * 获取消息列表
     */
    private void getMessageList() {
        Call call = null;
        if (type == 12) {//贷款申请
            call = HttpRequest.getIResource().GetMessageListById(1,mPage, mPageSize,creatTime);
        } else if (type == 13) {//信贷管理
            call = HttpRequest.getIResource().GetMessageListById(2,mPage, mPageSize,creatTime);
        } else {
            return;
        }
        callRequest(call, new HttpCallBack(BankMessageEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    List<BankMessageEntity> list = mResult.getDataList();
                    if (list.size()>0) {
                        creatTime = list.get(list.size()-1).getCreationDateStr();
                    }
                    if (mPage == 1) {
                        mList.clear();
                    }
                    mPage++;
                    mList.addAll(list);
                    setMore(list);
                    mAdapter.notifyDataSetChanged();
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
                mListView.onRefreshFinish();
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
                mListView.onRefreshFinish();
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

package com.zbxn.main.activity.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pub.base.BaseActivity;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.JsonUtil;
import com.pub.utils.MyToast;
import com.pub.utils.PreferencesUtils;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.adapter.BankListAdapter;
import com.zbxn.main.entity.BankListEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 选择银行
 *
 * @author: ysj
 * @date: 2017-03-21 14:00
 */
public class BankListActivity extends BaseActivity {

    @BindView(R.id.mListView)
    ListView mListView;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;

    private List<BankListEntity> mList;
    private BankListAdapter mAdapter;
    private String selectId;
    private String bankId;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mList = new ArrayList<>();
        selectId = getIntent().getStringExtra("selectId");
        bankId = getIntent().getStringExtra("bankId");
        type = getIntent().getIntExtra("type", 1);
        if (type == 1) {//获取银行列表
            setTitle("选择银行");
            String bankListStr = PreferencesUtils.getString(this, CreditActivity.BANKLISTSTR);
            if (!StringUtils.isEmpty(bankListStr)) {
                mList = JsonUtil.fromJsonList(bankListStr, BankListEntity.class);
                if (mList.size() != 0) {
                    llNodata.setVisibility(View.GONE);
                }
            }
        } else {//获取客户经理
            setTitle("选择业务人员");
            mList = new ArrayList<>();
            getAccountManagerList();
        }

    }

    private void initView() {
        mAdapter = new BankListAdapter(this, mList, selectId, type);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imgSelect = ((ImageView) view.findViewById(R.id.img_select));
                imgSelect.setSelected(true);
                mAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("selectId", mList.get(position).getBankId());
                intent.putExtra("selectPeopleId", mList.get(position).getUserId());
                intent.putExtra("name", mList.get(position).getBankName());
                intent.putExtra("peopleName", mList.get(position).getUserName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * 获取客户经理
     */
    public void getAccountManagerList() {
        Call call = HttpRequest.getIResource().getAccountManagerList(bankId);
        callRequest(call, new HttpCallBack(BankListEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    List<BankListEntity> list = mResult.getDataList();
                    mList.addAll(list);
                    if (mList.size() != 0) {
                        llNodata.setVisibility(View.GONE);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }

}

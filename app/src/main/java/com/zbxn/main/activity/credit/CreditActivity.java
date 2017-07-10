package com.zbxn.main.activity.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pub.base.BaseActivity;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.JsonUtil;
import com.pub.utils.PreferencesUtils;
import com.zbxn.R;
import com.zbxn.main.adapter.CreditAdapter;
import com.zbxn.main.entity.BankListEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 农信贷主页
 *
 * @author: ysj
 * @date: 2017-03-12 13:25
 */
public class CreditActivity extends BaseActivity {

    public static final String BANKLISTSTR = "bankList";

    @BindView(R.id.mListView)
    ListView mListView;

    private List<String> mList;
    private CreditAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_credit);
        ButterKnife.bind(this);
        setTitle("贷款申请");

        initData();
        initView();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add("在线申请");
        mList.add("查看申请");
        mList.add("草稿箱");
        mList.add("财务报表");
        getBankList();
    }


    private void initView() {
        mAdapter = new CreditAdapter(this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://在线申请
                        startActivity(new Intent(CreditActivity.this, CreditApplySelectActivity.class));
//                        startActivity(new Intent(CreditActivity.this, WeexActivity.class));
                        break;
                    case 1://查看申请
                        startActivity(new Intent(CreditActivity.this, CreditApplyActivity.class));
                        break;
                    case 2://草稿箱
                        startActivity(new Intent(CreditActivity.this, CreditDraftActivity.class));
                        break;
                    case 3://财务报表
                        Intent intent = new Intent(CreditActivity.this, CreditExcelUpActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;

                }
            }
        });
    }

    /**
     * 获取银行列表
     */
    private void getBankList() {
        Call call = HttpRequest.getIResource().getBankList();
        callRequest(call, new HttpCallBack(BankListEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    List<BankListEntity> list = mResult.getDataList();
                    String bankStr = JsonUtil.toJsonString(list);
                    PreferencesUtils.putString(CreditActivity.this, BANKLISTSTR, bankStr);
                } else {
                    Log.d("fail", "getBankList:" + mResult.getMsg() + "");
                }
            }

            @Override
            public void onFailure(String string) {
                Log.d("fail", "getBankList:" + "网络出错啦");
            }
        });
    }
}

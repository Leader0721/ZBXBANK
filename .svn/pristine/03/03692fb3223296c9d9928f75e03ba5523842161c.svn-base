package com.zbxn.main.activity.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.pub.common.ToolbarParams;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.KeyBoard;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.steward.CustomInfoActivity;
import com.zbxn.main.activity.steward.FinaExcelActivity;
import com.zbxn.main.adapter.CreditApplyAdapter;
import com.zbxn.main.entity.CreditApplyEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/4/12.
 */

public class CreditExcelSearchActivity extends BaseActivity {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.mListView)
    ListView mListView;
    @BindView(R.id.ll_nodata)
    LinearLayout ll_nodata;
    private List<CreditApplyEntity> mList;
    private CreditApplyAdapter mAdapter;
    //记录状态
    private int state = -1;
    private int mPage = 1;
    private int mPageSize = 10;
    private String bankId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steward_customsearch);
        ButterKnife.bind(this);
        bankId = getIntent().getStringExtra("bankId");
        mList = new ArrayList<>();
        //显示键盘
        KeyBoard.openKeybord(etSearch,this);
        tvCancel.setVisibility(View.VISIBLE);
        mListView.setEmptyView(ll_nodata);
        initView();
    }

    private void initView() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                imgDelete.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imgDelete.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                imgDelete.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!StringUtils.isEmpty(etSearch)) {
                        getApplyListForReport(etSearch.getText().toString());
                        KeyBoard.closeKeybord(etSearch,getApplicationContext());
                    }
                }
                return true;
            }
        });

        mAdapter = new CreditApplyAdapter(this, mList, 1);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CreditExcelSearchActivity.this, FinaExcelActivity.class);
                intent.putExtra("bank",false);
                intent.putExtra("LoanApplyId", mList.get(position).getLoanApplyId());
                startActivity(intent);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CreditExcelSearchActivity.this, CustomInfoActivity.class));
            }
        });
    }


    @Override
    public boolean onToolbarConfiguration(Toolbar toolbar, ToolbarParams params) {
        toolbar.setNavigationIcon(null);
        getLayoutInflater().inflate(R.layout.view_search, toolbar);
        return super.onToolbarConfiguration(toolbar, params);
    }

    @OnClick({R.id.tv_cancel, R.id.img_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.img_delete:
                etSearch.setText("");
                break;
        }
    }

    public void getApplyListForReport(String string) {
        Call call = HttpRequest.getIResource().getApplyListForReport(string,bankId,mPage + "", mPageSize + "");
        callRequest(call, new HttpCallBack(CreditApplyEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    List<CreditApplyEntity> list = mResult.getDataList();
                    if (list.size() == 0){
                        MyToast.showToast("没有搜索到相应的数据");
                        mList.clear();
                        mList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }else {
                        mList.clear();
                        mList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }

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

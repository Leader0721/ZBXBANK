package com.zbxn.main.activity.steward;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.pub.common.ToolbarParams;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.KeyBoard;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.zbxn.R;
import com.zbxn.main.adapter.CustomListAdapter;
import com.zbxn.main.entity.CustomListEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/3/13.
 */
public class CustomSearchActivity extends BaseActivity {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.mListView)
    PullRefreshListView mListView;
    private List<CustomListEntity> mList;
    private CustomListAdapter mAdapter;
    //记录状态
    private int mPage = 1;
    private int mPageSize = 10;
    private int state = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steward_customsearch);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        etSearch.setFocusable(true);
        state = getIntent().getIntExtra("state", -1);
        //显示键盘
        KeyBoard.openKeybord(etSearch, this);
        tvCancel.setVisibility(View.VISIBLE);
//        mListView.setEmptyView(ll_nodata);
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
                        setRefresh();
                        KeyBoard.closeKeybord(etSearch, getApplicationContext());
                    }
                }
                return true;
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CustomSearchActivity.this, CustomInfoActivity.class);
                intent.putExtra("Phone", StringUtils.isEmpty(mList.get(position).getContacTel()) ? "" : mList.get(position).getContacTel());
                intent.putExtra("LoanApplyId", mList.get(position).getLoanApplyId());
                intent.putExtra("VersionId", mList.get(position).getVersionId());

                startActivity(intent);
            }
        });
        mAdapter = new CustomListAdapter(CustomSearchActivity.this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnPullListener(new PullRefreshListView.OnPullListener() {
            @Override
            public void onRefresh() {
                setRefresh();
            }

            @Override
            public void onLoad() {
                getMyApplyList(etSearch.getText().toString());
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

    public void setRefresh() {
        mPage = 1;
        getMyApplyList(etSearch.getText().toString());
    }

    public void getMyApplyList(String string) {
        String stateString = state + "";
        String stateStr = "";
        if (stateString.equals("-1")) {
            stateStr = "";
        } else {
            stateStr = state + "";
        }
        Call call = HttpRequest.getIResource().getApplyByAccountManagerId(stateStr, string, mPage + "", mPageSize + "");
        callRequest(call, new HttpCallBack(CustomListEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                mListView.onRefreshFinish();
                if (mResult.getSuccess().equals("1")) {
                    List<CustomListEntity> list = mResult.getDataList();
                    if (mPage == 1) {
                        mList.clear();
                    }
                    setMore(list);
                    mList.addAll(list);
                    mPage++;
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

    public void openInputMethod(final EditText editText) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        }, 200);
    }

    public void closeInputMethod() {
        try {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        } finally {
        }
    }

}

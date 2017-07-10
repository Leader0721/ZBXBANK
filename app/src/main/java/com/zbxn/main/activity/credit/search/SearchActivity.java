package com.zbxn.main.activity.credit.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.pub.common.ToolbarParams;
import com.pub.utils.StringUtils;
import com.zbxn.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: ysj
 * @date: 2017-04-05 16:16
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.mFrameLayout)
    FrameLayout mFrameLayout;

    private FragmentTransaction fragmentTransaction;
    private CreditApplyFragment creditApplyFragment;
    private CreditDraftFragment creditDraftFragment;

    private String activityName;
    private String bankId;
    private int state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        tvCancel.setVisibility(View.VISIBLE);
        activityName = getIntent().getStringExtra("name");
        bankId = getIntent().getStringExtra("bankId");
        state = getIntent().getIntExtra("state", -1);
        initView();
    }

    private void initView() {
        if (!StringUtils.isEmpty(activityName)) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (activityName) {
                case "CreditApplyActivity":
                    creditApplyFragment = new CreditApplyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("bankId", bankId);
                    bundle.putInt("state", state);
                    creditApplyFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.mFrameLayout, creditApplyFragment);
                    break;
                case "CreditDraftActivity":
                    creditDraftFragment = new CreditDraftFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("bankId", bankId);
                    creditDraftFragment.setArguments(bundle2);
                    fragmentTransaction.replace(R.id.mFrameLayout, creditDraftFragment);
                    break;
                default:
                    break;
            }
            fragmentTransaction.commit();
        }
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
                    if (creditApplyFragment != null) {
                        creditApplyFragment.setSearch(StringUtils.getEditText(etSearch));
                    }
                    if (creditDraftFragment != null) {
                        creditDraftFragment.setSearch(StringUtils.getEditText(etSearch));
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
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
}

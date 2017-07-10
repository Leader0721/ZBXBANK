package com.zbxn.main.activity.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.entity.UserEntity;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.MyListView;
import com.zbxn.R;
import com.zbxn.main.activity.memberCenter.CompanyCreateActivity;
import com.zbxn.main.adapter.CreditApplySelectAdapter;
import com.zbxn.main.entity.CompanyEntity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;


/**
 * 选择企业
 *
 * @author: ysj
 * @date: 2017-03-14 09:47
 */
public class CreditApplySelectActivity extends BaseActivity {

    @BindView(R.id.mListView)
    MyListView mListView;
    @BindView(R.id.tv_apply)
    TextView tvApply;
    @BindView(R.id.mScrollView)
    ScrollView mScrollView;
    @BindView(R.id.tv_creat_company_top)
    TextView tvCreatCompanyTop;
    @BindView(R.id.ll_creat_top)
    LinearLayout llCreatTop;

    private List<CompanyEntity> mList;
    private CreditApplySelectAdapter mAdapter;
    private String companyId;
    private String companyName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_apply_select);
        ButterKnife.bind(this);
        setTitle("申请贷款");
        initView();
        GetCompanyListByUID();
    }

    private void initView() {
        mList = new ArrayList<>();
        mScrollView.smoothScrollTo(0, 0);
        mAdapter = new CreditApplySelectAdapter(this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < parent.getCount(); i++) {
                    parent.getChildAt(i).findViewById(R.id.img_select).setSelected(false);
                }
                ImageView imgSelect = ((ImageView) view.findViewById(R.id.img_select));
                imgSelect.setSelected(true);

                companyId = mList.get(position).getID();
                companyName = mList.get(position).getCompanyName();
            }
        });
    }


    /**
     * 获取公司列表
     */
    public void GetCompanyListByUID() {
        //请求网络
        Call call = HttpRequest.getIResource().GetLoanCompanyListByUid();
        callRequest(call, new HttpCallBack(CompanyEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//0成功
                    List<CompanyEntity> childList = mResult.getDataList();
                    if (childList == null) {
                        childList = new ArrayList<>();
                    }
                    mList.clear();
                    mList.addAll(childList);
                    mAdapter.notifyDataSetChanged();
                    if (mList.size() == 0) {
                        llCreatTop.setVisibility(View.VISIBLE);
                    } else {
                        llCreatTop.setVisibility(View.GONE);
                        companyId = mList.get(0).getID();
                        companyName = mList.get(0).getCompanyName();
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


    @OnClick({R.id.tv_apply, R.id.tv_creat_company_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_apply://申请企业贷款
                if (StringUtils.isEmpty(companyId)) {
                    MyToast.showToast("请您先选择贷款的企业");
                } else {
                    if (null == BaseApp.mUserEntity) {
                        MyToast.showToast("暂未获取到权限");
                        return;
                    }
                    for (int i = 0; i < BaseApp.mUserEntity.getPermission().size(); i++) {
                        UserEntity.PermissionBean permissionBean = BaseApp.mUserEntity.getPermission().get(i);
                        if (companyId.equals(permissionBean.getCompanyId())) {
                            for (int j = 0; j < permissionBean.getPermissoin().size(); j++) {
                                if ("02".equals(permissionBean.getPermissoin().get(j).getCode())) {
                                    Intent intent = new Intent(CreditApplySelectActivity.this, CreditBankSelectActivity.class);
                                    intent.putExtra("companyId", companyId);
                                    intent.putExtra("companyName", companyName);
                                    startActivity(intent);
                                    return;
                                }
                            }
                        }
                    }
                    MyToast.showToast("暂无权限");
                }
                break;
            case R.id.tv_creat_company_top:
                startActivityForResult(new Intent(CreditApplySelectActivity.this, CompanyCreateActivity.class), 1000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                GetCompanyListByUID();
            }
        }
    }

    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (EventKey.CLEARBANKACTIVITY.equals(eventCustom.getTag())) {
            finish();
        }
    }
}

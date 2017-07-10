package com.zbxn.main.activity.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.entity.BankSelectEntity;
import com.zbxn.main.entity.ContactsEntity;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * 选择银行及业务员
 *
 * @author: ysj
 * @date: 2017-03-21 11:46
 */
public class CreditBankSelectActivity extends BaseActivity {

    public static final int Flag_CallBack_SelectBank = 10001;
    public static final int Flag_CallBack_SelectPeople = 10002;

    @BindView(R.id.tv_bank)
    TextView tvBank;
    @BindView(R.id.tv_name)
    TextView tvName;

    private String bankId;
    private String peopleId;
    private String companyId = "";//企业id
    private String companyName = "";
    private String selectPeopleId;

    private ArrayList<ContactsEntity> mListContacts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_bank_select);
        ButterKnife.bind(this);
        setTitle("银行机构");
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
        companyId = getIntent().getStringExtra("companyId");
        companyName = getIntent().getStringExtra("companyName");
    }

    @Override
    public void initRight() {
        super.initRight();
        setRight1Icon(0);
        setRight1("提交");
        setRight1Show(true);
    }

    @Override
    public void actionRight1(MenuItem menuItem) {
        super.actionRight1(menuItem);
        if (StringUtils.isEmpty(tvBank)) {
            MyToast.showToast("请选择银行");
            return;
        }
        if (StringUtils.isEmpty(tvName)) {
            MyToast.showToast("请选择办理的业务人员");
            return;
        }
        postBankSelect();
    }

    @OnClick({R.id.ll_bank, R.id.ll_name})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_bank://选择银行
                intent = new Intent(this, BankListActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("selectId", bankId);
                startActivityForResult(intent, Flag_CallBack_SelectBank);
                break;
            case R.id.ll_name://选择业务人员
                if (StringUtils.isEmpty(tvBank)) {
                    MyToast.showToast("请先选择银行");
                    return;
                }
                intent = new Intent(this, BankListActivity.class);
                intent.putExtra("type", 2);
                intent.putExtra("bankId", bankId);
                intent.putExtra("selectId", peopleId);
                startActivityForResult(intent, Flag_CallBack_SelectPeople);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == Flag_CallBack_SelectBank) {//选择银行
            if (data != null) {
                if (data.getStringExtra("selectId") != bankId) {
                    tvName.setText("");
                    peopleId = "";
                }
                bankId = data.getStringExtra("selectId");
                tvBank.setText(data.getStringExtra("name"));
            }
        }
        if (requestCode == Flag_CallBack_SelectPeople) {//选择业务人员
            peopleId = data.getStringExtra("selectPeopleId");
            tvName.setText(data.getStringExtra("peopleName"));
        }
    }

    /**
     * 提交选择的银行，业务员
     */
    public void postBankSelect() {
        Call call = HttpRequest.getIResource().postBankSelect(companyId, bankId, peopleId + "", companyName);
        callRequest(call, new HttpCallBack(BankSelectEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    BankSelectEntity entity = (BankSelectEntity) mResult.getData();
                    Intent intent = new Intent(CreditBankSelectActivity.this, CreditProgressActivity.class);
                    intent.putExtra("From", "1");//1新建  2查看申请  3草稿箱
                    intent.putExtra("LoanApplyId", entity.getLoanApplyId());
                    intent.putExtra("VersionId", entity.getVersionId());
                    startActivity(intent);

                    EventCustom eventCustom = new EventCustom();
                    eventCustom.setTag(EventKey.CLEARBANKACTIVITY);
                    EventBus.getDefault().post(eventCustom);
                    finish();
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

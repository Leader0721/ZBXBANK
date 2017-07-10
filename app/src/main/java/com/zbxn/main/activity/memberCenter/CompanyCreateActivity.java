package com.zbxn.main.activity.memberCenter;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.pub.base.BaseActivity;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.utils.ValidationUtil;
import com.zbxn.R;
import com.zbxn.main.entity.CompanyEntity;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class CompanyCreateActivity extends BaseActivity {

    @BindView(R.id.et_Name)
    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_create);
        ButterKnife.bind(this);
        setTitle("创建公司");
//
//        //显示键盘
//        KeyBoard.openKeybord(etName,this);

        //禁止输入特殊字符及空格
        ValidationUtil.setEditTextInhibitInputSpeChat(etName);
    }

    @Override
    public void initRight() {
        super.initRight();
        setRight1Icon(R.mipmap.complete2);
        setRight1Show(true);
    }

    @Override
    public void actionRight1(MenuItem menuItem) {
        super.actionRight1(menuItem);
        if (validate()) {
            AddCompany();
        }
    }

    private boolean validate() {
        if (StringUtils.isEmpty(etName)) {
            MyToast.showToast("请填写公司名");
            return false;
        }
//        if (!ValidationUtil.isChineseCharacters(StringUtils.getEditText(etName))){
//            MyToast.showToast("请输入汉字");
//            return false;
//        }
        return true;
    }

    /**
     * 创建公司
     */
    public void AddCompany() {
        //请求网络
        String companyName = StringUtils.getEditText(etName);
        if (ContainsEmojiEditText.containsEmoji(companyName)) {
            MyToast.showToast("公司名称不能包含表情");
            return;
        }
        Call call = HttpRequest.getIResource().AddCompany(companyName);
        callRequest(call, new HttpCallBack(CompanyEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//1成功
                    setResult(RESULT_OK);
                    EventCustom eventCustom = new EventCustom();
                    eventCustom.setTag(EventKey.COMPANYREFRESH);
                    EventBus.getDefault().post(eventCustom);
                    finish();
                    MyToast.showToast("添加成功");
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

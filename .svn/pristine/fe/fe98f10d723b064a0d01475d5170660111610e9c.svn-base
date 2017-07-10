package com.zbxn.main.activity.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pub.base.BaseActivity;
import com.pub.common.ToolbarParams;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.adapter.ContactsPeopleAddAdapter;
import com.zbxn.main.entity.ContactsEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * 搜索未加人员  添加好友
 *
 * @author Wuzy
 * @time 2016/8/11
 */
public class ContactsSearchAddActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.mListView)
    ListView mListView;

    private List<ContactsEntity> mList;

    private ContactsPeopleAddAdapter mAdapter;

    //0-查看 1-多选 2-单选
    private int mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_search_add);
        ButterKnife.bind(this);
        mType = getIntent().getIntExtra("type", 0);

        mList = new ArrayList<>();

        initView();

        etSearch.setHint("手机号/账号");
        tvCancel.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onToolbarConfiguration(Toolbar toolbar, ToolbarParams params) {
        toolbar.setNavigationIcon(null);
        getLayoutInflater().inflate(R.layout.view_search, toolbar);
        return super.onToolbarConfiguration(toolbar, params);
    }

    @OnClick({R.id.img_delete, R.id.tv_cancel, R.id.et_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_delete:
                etSearch.setText("");
                break;
            case R.id.tv_cancel://关闭页面
                finish();
                break;
            case R.id.et_search:
                search();
                break;
        }
    }

    public void search() {
        String content = StringUtils.getEditText(etSearch);
        ContactsEntity entity = new ContactsEntity();
        entity.setUserName("吴");
        mList.add(entity);
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mAdapter = new ContactsPeopleAddAdapter(this, mList, new ICustomListener() {
            @Override
            public void onCustomListener(int obj0, Object obj1, int position) {
                ContactsEntity entity = (ContactsEntity) obj1;
                switch (obj0) {
                    case 0:
                        AddFriendRequest(ContactsSearchAddActivity.this, entity.getUserId(), true);
                        break;
                }
            }
        });
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mType == 0) {
                    //判断当前点击的用户是否为登录用户
                    ContactsEntity contacts = (ContactsEntity) mListView.getAdapter().getItem(position);
                    Intent intent = new Intent(ContactsSearchAddActivity.this, ContactsDetailActivity.class);
                    intent.putExtra(ContactsDetailActivity.Flag_Input_Contactor, contacts);
                    startActivity(intent);
                    return;
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = (s == null || s.length() == 0);
                imgDelete.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, android.view.KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    String content = etSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        MyToast.showToast("请输入搜索内容");
                    } else {
                        search();
                    }
                }
                return false;
            }
        });
    }

    /**
     * 请求添加好友
     */
    public void AddFriendRequest(Context context, String toUserId, boolean isShowProgress) {
        Call call = HttpRequest.getIResource().AddFriendRequest(toUserId, "1");
        callRequest(call, new HttpCallBack(String.class, context, isShowProgress) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {//0成功
                    MyToast.showToast("好友请求已发送，等待对方同意");
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

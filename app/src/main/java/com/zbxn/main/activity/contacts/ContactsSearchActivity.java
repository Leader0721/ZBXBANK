package com.zbxn.main.activity.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.common.ToolbarParams;
import com.pub.utils.MyToast;
import com.pub.utils.SpaceItemDecoration;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.adapter.ContactsPeopleAdapter;
import com.zbxn.main.adapter.ContactsSelectPeopleAdapter;
import com.zbxn.main.entity.ContactsEntity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索通讯录
 *
 * @author Wuzy
 * @time 2016/8/11
 */
public class ContactsSearchActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.mListView)
    ListView mListView;

    @BindColor(R.color.tvc6)
    int mColorDepartment;
    //    @BindView(R.id.mTextSelect)
//    TextView mTextSelect;
//    @BindView(R.id.mCheck)
//    TextView mCheck;
    @BindView(R.id.mTextOk)
    TextView mTextOk;
    @BindView(R.id.mLayoutBottom)
    LinearLayout mLayoutBottom;
    @BindView(R.id.rv_horizontal)
    RecyclerView rvHorizontal;

    private ContactsSelectPeopleAdapter contactsSelectPeopleAdapter;
    private List<ContactsEntity> mCheckedList;

    private SpannableStringBuilder mStringBuilder = new SpannableStringBuilder();

    private ContactsPeopleAdapter mAdapter;

    //0-查看 1-多选 2-单选
    private int mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_search);
        ButterKnife.bind(this);
        mType = getIntent().getIntExtra("type", 0);

        mCheckedList = new ArrayList<>();

        initView();

        if (mType == 0 || mType == 2) {
            mLayoutBottom.setVisibility(View.GONE);
        }

        //设置选中数据
        setData();

        etSearch.setHint(R.string.contacts_search_hint);
        tvCancel.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onToolbarConfiguration(Toolbar toolbar, ToolbarParams params) {
        toolbar.setNavigationIcon(null);
        getLayoutInflater().inflate(R.layout.view_search, toolbar);
        return super.onToolbarConfiguration(toolbar, params);
    }

    @OnClick({R.id.tv_cancel, R.id.et_search, R.id.mTextOk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel://关闭页面
                finish();
                break;
            case R.id.img_delete:
                etSearch.setText("");
                break;
            case R.id.mTextOk:
                //关闭选择人
                EventCustom eventCustom1 = new EventCustom();
                eventCustom1.setTag(EventKey.CLOSECONTACTSSELECT);
                eventCustom1.setObj("");
                EventBus.getDefault().post(eventCustom1);
                finish();
                break;
        }
    }

    public void search() {
        String content = StringUtils.getEditText(etSearch);
        try {
            content = "%" + content + "%";

            List<ContactsEntity> mList = BaseApp.DBLoader.findAll(
                    Selector.from(ContactsEntity.class)
//                            .where("isDepartment", "=", null)
                            .where("userName", "like", content)
                            .or("telephone", "like", content)
//                            .or("loginname", "like", content)
                            .or("departmentName", "like", content)
                            .and("isDepartment", "=", null)
                            .orderBy("captialChar", true));
            chechIsSelect(mList);
            if (StringUtils.isEmpty(mList)) {
                mList = new ArrayList<>();
                HashMap<String, Integer> alphaIndexer = new HashMap<String, Integer>();
                String[] sections = new String[mList.size()];
                mAdapter = new ContactsPeopleAdapter(this, mList, null, alphaIndexer, sections, false, mType);
                mListView.setAdapter(mAdapter);
                MyToast.showToast("没找到对应的记录");
            } else {
                HashMap<String, Integer> alphaIndexer = new HashMap<String, Integer>();
                String[] sections = new String[mList.size()];
                mAdapter = new ContactsPeopleAdapter(this, mList, null, alphaIndexer, sections, false, mType);
                mListView.setAdapter(mAdapter);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mType == 0) {
                    //判断当前点击的用户是否为登录用户
                    ContactsEntity contacts = (ContactsEntity) mListView.getAdapter().getItem(position);
                    Intent intent = new Intent(ContactsSearchActivity.this, ContactsDetailActivity.class);
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

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHorizontal.setLayoutManager(linearLayoutManager);
        rvHorizontal.addItemDecoration(new SpaceItemDecoration(5));
        //设置适配器
        contactsSelectPeopleAdapter = new ContactsSelectPeopleAdapter(this, mCheckedList);
        rvHorizontal.setAdapter(contactsSelectPeopleAdapter);
        contactsSelectPeopleAdapter.setOnItemClickListener(new ICustomListener() {
            @Override
            public void onCustomListener(int obj0, Object obj1, int position) {
                ContactsEntity itemInfo = (ContactsEntity) obj1;
                itemInfo.setSelected(false);
                ContactsChoseActivity.mHashMap.remove(itemInfo.getUserId());
                mAdapter.notifyDataSetChanged();
                //通知更新选择人
                EventCustom eventCustom = new EventCustom();
                eventCustom.setTag(EventKey.UPDATECONTACTSSELECT);
                eventCustom.setObj("");
                EventBus.getDefault().post(eventCustom);
            }
        });
    }

    /**
     * 更新是否选中
     *
     * @param lists
     */
    private void chechIsSelect(final List<ContactsEntity> lists) {
        List<ContactsEntity> listTemp = new ArrayList<>();
        listTemp.addAll(lists);
        lists.clear();
        for (int i = 0; i < listTemp.size(); i++) {
            if (ContactsChoseActivity.mHashMap.containsKey(listTemp.get(i).getUserId())) {
                listTemp.get(i).setSelected(true);
            } else {
                listTemp.get(i).setSelected(false);
            }
        }
        lists.addAll(listTemp);
    }

    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (EventKey.UPDATECONTACTSSELECT.equals(eventCustom.getTag())) {
            //设置选中数据
            setData();
        }
    }

    /**
     * 设置选中数据
     */
    private void setData() {
        mCheckedList.clear();
        ContactsEntity contacts = null;
        String select = "";
        int count = 0;
        //取得map中所有的key和value
        for (Map.Entry<String, ContactsEntity> entry : ContactsChoseActivity.mHashMap.entrySet()) {
            contacts = entry.getValue();
            String value = contacts.getUserName();
            mCheckedList.add(contacts);
            count++;
            if (count <= 2) {
                select += (value + ",");
            }
        }
        if (select.length() > 0) {
            select = select.substring(0, select.length() - 1);
        }
        if (count > 2) {
            select = select + "等" + count + "人";
        } else if (count == 0) {
            select = select + "0人";
        }
        mTextOk.setText("确定(" + count + ")");
        //刷新选中的人员
        contactsSelectPeopleAdapter.notifyDataSetChanged();
//        mTextSelect.setText("已选择:" + select);
    }

    @OnClick(R.id.img_delete)
    public void onClick() {
        etSearch.setText("");
    }
}

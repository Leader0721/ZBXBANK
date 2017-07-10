package com.zbxn.main.activity.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.common.ToolbarParams;
import com.pub.utils.SpaceItemDecoration;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.adapter.ContactsSelectPeopleAdapter;
import com.zbxn.main.entity.ContactsEntity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选人
 *
 * @author Wuzy
 * @time 2016/8/18
 */
public class ContactsChoseActivity extends BaseActivity {
    public static final String Flag_Output_Checked = "contacts";


    @BindView(R.id.mTextOk)
    TextView mTextOk;
    @BindView(R.id.mLayoutBottom)
    LinearLayout mLayoutBottom;
    @BindView(R.id.rv_horizontal)
    RecyclerView rvHorizontal;

    private ContactsSelectPeopleAdapter contactsSelectPeopleAdapter;

    private List<ContactsEntity> mCheckedList;

    private List<ContactsEntity> listAll;//全部

    //0-查看 1-多选 2-单选
    private int mType;

    public static HashMap<String, ContactsEntity> mHashMap = new HashMap<String, ContactsEntity>();

    @Override
    public boolean onToolbarConfiguration(Toolbar toolbar, ToolbarParams params) {
        toolbar.setTitle("选择");
        return super.onToolbarConfiguration(toolbar, params);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_chose);
        ButterKnife.bind(this);
        mType = getIntent().getIntExtra("type", 0);
        mCheckedList = (List<ContactsEntity>) getIntent().getSerializableExtra("list");

        try {
            listAll = BaseApp.DBLoader.findAll(Selector.from(ContactsEntity.class).where("isDepartment", "=", null).orderBy("captialChar"));//
        } catch (Exception e) {
            listAll = new ArrayList<>();
            e.printStackTrace();
        }

        mHashMap.clear();
        if (StringUtils.isEmpty(mCheckedList)) {
            mCheckedList = new ArrayList<>();
        } else {
            for (int i = 0; i < mCheckedList.size(); i++) {
                mHashMap.put(mCheckedList.get(i).getUserId(), mCheckedList.get(i));
            }

            //通知更新选择人
            EventCustom eventCustom = new EventCustom();
            eventCustom.setTag(EventKey.UPDATECONTACTSSELECT);
            eventCustom.setObj("");
            EventBus.getDefault().post(eventCustom);
        }
        initView();

        if (mType == 2) {
            mLayoutBottom.setVisibility(View.GONE);
        }
        //隐藏阴影
        getToolbarHelper().setShadowEnable(false);
    }

    @Override
    public void initRight() {
        setRight1Icon(R.mipmap.temp132);
        setRight1Show(true);
        super.initRight();
    }

    @Override
    public void actionRight1(MenuItem menuItem) {
        Intent intent = new Intent(this, ContactsSearchActivity.class);
        intent.putExtra("type", mType);
        startActivityForResult(intent, 1000);
        super.actionRight1(menuItem);
    }

    //    @OnClick({mCheck, R.id.mTextOk})
    @OnClick({R.id.mTextOk})
    public void onClick(View view) {
        switch (view.getId()) {
            /*case mCheck:
                if (mCheck.isSelected()) {
                    mCheck.setSelected(false);
                    mHashMap.clear();
                } else {
                    mCheck.setSelected(true);
                    mHashMap.clear();
                    for (int i = 0; i < listAll.size(); i++) {
                        mHashMap.put(listAll.get(i).getId() + "", listAll.get(i));
                    }
                }

                //通知更新选择人
                EventCustom eventCustom = new EventCustom();
                eventCustom.setTag(EventKey.UPDATECONTACTSSELECT);
                eventCustom.setObj("");
                EventBus.getDefault().post(eventCustom);
                break;*/
            case R.id.mTextOk:
                if (StringUtils.isEmpty(mCheckedList)) {
                    finish();
                } else {
                    Intent data = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Flag_Output_Checked, (ArrayList) mCheckedList);
                    data.putExtras(bundle);
                    setResult(RESULT_OK, data);
                    finish();
                }
                break;
        }
    }

    private void initView() {
        ContactsMoreFragment fragment = new ContactsMoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", mType);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mFragment, fragment);
        transaction.commit();

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
                //通知更新选择人
                EventCustom eventCustom = new EventCustom();
                eventCustom.setTag(EventKey.UPDATECONTACTSSELECT);
                eventCustom.setObj("");
                EventBus.getDefault().post(eventCustom);
            }
        });
    }

    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (EventKey.UPDATECONTACTSSELECT.equals(eventCustom.getTag())) {
            mCheckedList.clear();
            ContactsEntity contacts = null;
            String select = "";
            int count = 0;
            //取得map中所有的key和value
            for (Map.Entry<String, ContactsEntity> entry : mHashMap.entrySet()) {
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

            //mTextSelect.setText("已选择:" + select);
            //设置全选按钮状态
            /*if (count >= listAll.size()) {
                mCheck.setSelected(true);
            } else {
                mCheck.setSelected(false);
            }*/
        } else if (EventKey.UPDATECONTACTSSELECTSINGLE.equals(eventCustom.getTag())) {
            mCheckedList.clear();
            ContactsEntity contacts = null;
            String select = "";
            int count = 0;
            //取得map中所有的key和value
            for (Map.Entry<String, ContactsEntity> entry : mHashMap.entrySet()) {
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
//            mTextSelect.setText("已选择:" + select);
            if (StringUtils.isEmpty(mCheckedList)) {
                finish();
            } else {
                Intent data = new Intent();
                data.putExtra("id", mCheckedList.get(0).getUserId());
                data.putExtra("name", mCheckedList.get(0).getUserName());
                data.putExtra("headUrl", mCheckedList.get(0).getHeadImgUrl());
                data.putExtra("deparmentName", mCheckedList.get(0).getDepartmentName());
                setResult(RESULT_OK, data);
                finish();
            }
        } else if (EventKey.CLOSECONTACTSSELECT.equals(eventCustom.getTag())) {
            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Flag_Output_Checked, (ArrayList) mCheckedList);
            data.putExtras(bundle);
            setResult(RESULT_OK, data);
            finish();
        }
    }

}

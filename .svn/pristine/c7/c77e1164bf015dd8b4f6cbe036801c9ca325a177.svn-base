package com.zbxn.main.activity.contacts;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.SpaceItemDecoration;
import com.pub.utils.StringUtils;
import com.pub.widget.MyListView;
import com.pub.widget.ProgressLayout;
import com.pub.widget.dbutils.DBUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.adapter.ContactsDepartmentOnlyAdapter;
import com.zbxn.main.adapter.ContactsPeopleAdapter;
import com.zbxn.main.adapter.ContactsSelectPeopleAdapter;
import com.zbxn.main.entity.ContactsDepartmentEntity;
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
import retrofit2.Call;

import static com.zbxn.main.activity.contacts.ContactsChoseActivity.mHashMap;

/**
 * 项目名称：通讯录 按部门
 * 创建人：Wuzy
 * 创建时间：2016/11/1 8:52
 */
public class ContactsDepartmentActivity extends BaseActivity {
    @BindView(R.id.mLayout)
    LinearLayout mLayout;
    @BindView(R.id.mListViewDepartment)
    MyListView mListViewDepartment;
    @BindView(R.id.mListViewPeople)
    MyListView mListViewPeople;
    //    @BindView(R.id.mCheck)
//    TextView mCheck;
//    @BindView(R.id.mTextSelect)
//    TextView mTextSelect;
    @BindView(R.id.mTextOk)
    TextView mTextOk;
    @BindView(R.id.rv_horizontal)
    RecyclerView rvHorizontal;
    @BindView(R.id.mLayoutBottom)
    LinearLayout mLayoutBottom;
    @BindView(R.id.layout_progress)
    ProgressLayout layoutProgress;

    private ContactsPeopleAdapter contactsPeopleAdapter;
    private List<ContactsDepartmentEntity> listDepartment = new ArrayList<>();
    private List<ContactsEntity> listContacts = new ArrayList<>();

    private List<ContactsEntity> mCheckedList;
    private ContactsSelectPeopleAdapter contactsSelectPeopleAdapter;

    private LayoutInflater mInflater;

    //0-查看 1-多选 2-单选
    private int mType;

    private String mId;

    private List<View> mListV = new ArrayList<>();

    private DBUtils<ContactsDepartmentEntity> mDBUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_department);
        ButterKnife.bind(this);
        mType = getIntent().getIntExtra("type", 0);
        mId = getIntent().getStringExtra("id");
        listContacts = (List<ContactsEntity>) getIntent().getSerializableExtra("peopleList");

        mCheckedList = new ArrayList<>();

        mInflater = LayoutInflater.from(this);

        //设置标题
        setTitle("按部门");

        initView();

//        addView("全公司", null);
//        getDepartment(null);

        if (mType == 0 || mType == 2) {
            mLayoutBottom.setVisibility(View.GONE);
        }

        GetDepartmentListByCompanyID();
    }

    private void initView() {
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
                contactsPeopleAdapter.notifyDataSetChanged();
                //通知更新选择人
                EventCustom eventCustom = new EventCustom();
                eventCustom.setTag(EventKey.UPDATECONTACTSSELECT);
                eventCustom.setObj("");
                EventBus.getDefault().post(eventCustom);
            }
        });
    }

    private void addView(String name, String parentId) {
        View view = mInflater.inflate(R.layout.view_contacts_organize_text, mLayout, false);
        view.setTag(parentId);
        TextView textView = (TextView) view.findViewById(R.id.mText);
        textView.setText(name);
        textView.setTag(parentId);
//        Drawable drawable = getResources().getDrawable(R.drawable.drawable);
//        /// 这一步必须要做,否则不会显示.
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if ("null".equals(parentId + "")) {
            textView.setCompoundDrawables(null, null, null, null);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDepartment(v.getTag() + "");
            }
        });
        mListV.add(view);
        refresh();
    }

    /**
     * 刷新顶部视图
     */
    private void refresh() {
        mLayout.removeAllViews();
        Drawable drawable = getResources().getDrawable(R.mipmap.rignt_arrow);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        for (int i = 0; i < mListV.size(); i++) {
            if (mListV.size() - 1 == i) {
                ((TextView) mListV.get(i).findViewById(R.id.mText)).setCompoundDrawables(null, null, null, null);
                ((TextView) mListV.get(i).findViewById(R.id.mText)).setTextColor(getResources().getColor(R.color.tvc9));
            } else {
                ((TextView) mListV.get(i).findViewById(R.id.mText)).setCompoundDrawables(null, null, drawable, null);
                ((TextView) mListV.get(i).findViewById(R.id.mText)).setTextColor(getResources().getColor(R.color.orange));
            }
            mLayout.addView(mListV.get(i));
        }
    }

    //获取数据
    private void GetDepartmentListByCompanyID() {
        Call call = HttpRequest.getIResource().GetDepartmentListByCompanyID(mId);
        callRequest(call, new HttpCallBack(ContactsDepartmentEntity.class, this, true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    listDepartment = mResult.getDataList();
                    if (StringUtils.isEmpty(listDepartment)) {
                        layoutProgress.showEmpty();
                    } else {
                        layoutProgress.showContent();

                        if (mDBUtils == null) {
                            mDBUtils = new DBUtils<>(ContactsDepartmentEntity.class);
                        }
                        mDBUtils.delete(WhereBuilder.b("CompanyID", "=", mId));
                        ContactsDepartmentEntity[] array = new ContactsDepartmentEntity[listDepartment.size()];
                        listDepartment.toArray(array);
                        mDBUtils.add(array);

                        addView("全公司", null);
                        getDepartment(null);

                        //设置选中数据
                        setData();
                    }

                } else {
                    MyToast.showToast(mResult.getMsg());
                    layoutProgress.showError(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GetDepartmentListByCompanyID();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
                layoutProgress.showError(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetDepartmentListByCompanyID();
                    }
                });
            }
        });
    }

    private void getDepartment(String parentId) {
        listDepartment.clear();
        listContacts.clear();
        try {
            listDepartment = BaseApp.DBLoader.findAll(Selector.from(ContactsDepartmentEntity.class));
            if (StringUtils.isEmpty(parentId) || "null".equals(parentId)) {
                parentId = null;//部门结构根据null
            }
            listDepartment = BaseApp.DBLoader.findAll(Selector.from(ContactsDepartmentEntity.class)
                    .where("ParentID", "=", parentId).and("CompanyID", "=", mId));
            ContactsDepartmentOnlyAdapter adapter = new ContactsDepartmentOnlyAdapter(this, listDepartment, listener);
            mListViewDepartment.setAdapter(adapter);
            mListViewDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    addView(listDepartment.get(position).getName(), listDepartment.get(position).getID());
                    getDepartment(listDepartment.get(position).getID());
                }
            });

            if (StringUtils.isEmpty(parentId) || "null".equals(parentId)) {
                parentId = "00000000-0000-0000-0000-000000000000";//人员属于哪个部门根据"00000000-0000-0000-0000-000000000000"
            }
            listContacts = BaseApp.DBLoader.findAll(Selector.from(ContactsEntity.class)
                    .where("DepartmentId", "=", parentId).and("CompanyID", "=", mId));
            chechIsSelect(listContacts);
            HashMap<String, Integer> alphaIndexer = new HashMap<String, Integer>();
            String[] sections = new String[listContacts.size()];
            contactsPeopleAdapter = new ContactsPeopleAdapter(this, listContacts, listener, alphaIndexer, sections, false, mType);
            mListViewPeople.setAdapter(contactsPeopleAdapter);
            mListViewPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ContactsEntity contacts = listContacts.get(position);
                    Intent intent = new Intent(ContactsDepartmentActivity.this, ContactsDetailActivity.class);
                    intent.putExtra(ContactsDetailActivity.Flag_Input_Contactor, contacts);
                    startActivity(intent);
                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }
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
            if (mHashMap.containsKey(listTemp.get(i).getUserId())) {
                listTemp.get(i).setSelected(true);
            } else {
                listTemp.get(i).setSelected(false);
            }
        }
        lists.addAll(listTemp);
    }

    //    @OnClick({R.id.mCheck, R.id.mTextOk})
    @OnClick({R.id.mTextOk})
    public void onClick(View view) {
        switch (view.getId()) {
            /*case R.id.mCheck:
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
                eventCustom.setTag(KeyEvent.UPDATECONTACTSSELECT);
                eventCustom.setObj("");
                EventBus.getDefault().post(eventCustom);
                break;*/
            case R.id.mTextOk:
                //关闭选择人
                EventCustom eventCustom = new EventCustom();
                eventCustom.setTag(EventKey.CLOSECONTACTSSELECT);
                eventCustom.setObj("");
                EventBus.getDefault().post(eventCustom);
                finish();
                break;
        }
    }

    private ICustomListener listener = new ICustomListener() {
        @Override
        public void onCustomListener(int obj0, Object obj1, int position) {
            ContactsEntity result = (ContactsEntity) obj1;
            switch (obj0) {
                case 0:
                    break;

                default:
                    break;
            }
        }
    };

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
//        mTextSelect.setText("已选择:" + select);

        mTextOk.setText("确定(" + count + ")");
        //刷新选中的人员
        contactsSelectPeopleAdapter.notifyDataSetChanged();
    }

    /**
     * 顶部点击部门，重新刷新部门
     *
     * @param parentId
     */
    private void resetDepartment(String parentId) {
        getDepartment(parentId);
        int index = -1;
        for (int i = 0; i < mListV.size(); i++) {
            if ((parentId + "").equals(mListV.get(i).getTag() + "")) {
                index = i;
            }
        }
        //解决每次产生的刷新产生null
        mListV = mListV.subList(0, index + 1);
        //mListV = mListV.subList(0, index );
        refresh();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mListV.size() <= 1) {//顶层直接退出
                return super.onKeyDown(keyCode, event);
            } else {
                if (mListV.size() >= 2) {
                    TextView textView = (TextView) mListV.get(mListV.size() - 2).findViewById(R.id.mText);
                    resetDepartment(textView.getTag() + "");

                    return false;
                } else {
                    return super.onKeyDown(keyCode, event);
                }
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}


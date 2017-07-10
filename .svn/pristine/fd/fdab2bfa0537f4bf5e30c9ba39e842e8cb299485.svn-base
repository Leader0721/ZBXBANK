package com.zbxn.main.activity.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pub.base.BaseActivity;
import com.zbxn.R;
import com.zbxn.main.adapter.ContactsGroupAdapter;
import com.zbxn.main.entity.ContactsGroupEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 按群组
 *
 * @author Wuzy
 * @time 2016/3/13
 */
public class ContactsGroupActivity extends BaseActivity {

    @BindView(R.id.mListView)
    ListView mListView;

    private ArrayList<ContactsGroupEntity> mList = new ArrayList<>();
    private ContactsGroupAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        initView();
    }

    protected void initView() {
        mList = new ArrayList<>();
        ContactsGroupEntity entity = new ContactsGroupEntity();
        entity.setCompanyName("群1");
        mList.add(entity);
        entity = new ContactsGroupEntity();
        entity.setCompanyName("群2");
        mList.add(entity);

        mAdapter = new ContactsGroupAdapter(this, mList, null);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

}

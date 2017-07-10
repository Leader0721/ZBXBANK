package com.zbxn.main.activity.contacts;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.pub.base.BaseActivity;
import com.pub.base.BaseApp;
import com.pub.common.ToolbarParams;
import com.pub.dialog.ZBXAlertDialog;
import com.pub.dialog.ZBXAlertListener;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.utils.ValidationUtil;
import com.pub.widget.MyLetterListView;
import com.pub.widget.MyProgressDialog;
import com.pub.widget.dbutils.DBUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.adapter.ContactsInviteAdapter;
import com.zbxn.main.entity.ContactsInviteEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/26.
 */

public class ContactsInviteActivity extends BaseActivity {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.mListView)
    ListView mListView;
    @BindView(R.id.mMyLetterListView)
    MyLetterListView mMyLetterListView;
    private ZBXAlertDialog dialog;
    private ContactsInviteAdapter mAdapter;
    //    private BrandListAdapter adapter;
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;// 存放存在的汉语拼音首字母
    private Handler handler;
    private OverlayThread overlayThread;

    private View mLayoutOverlay;
    private TextView mOverlay;

    //    读取手机通讯录
    private DBUtils<ContactsInviteEntity> mDBUtils;
    private List<ContactsInviteEntity> contactList;
    String[] selectCol = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts._ID
    };
    public static final int COL_NAME = 0;
    public static final int COL_HAS_PHONE = 1;
    String[] selPhoneCols = new String[]{
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.TYPE
    };
    public static final int COL_PHONE_NUMBER = 0;

    private Dialog progressDialog;// 自定义加载弹框

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactsinvite);
        ButterKnife.bind(this);
        etSearch.setHint("姓名/手机号");
        tvCancel.setVisibility(View.VISIBLE);
        if (mDBUtils == null) {
            mDBUtils = new DBUtils<>(ContactsInviteEntity.class);
        }
        if (progressDialog == null) {
            progressDialog = new MyProgressDialog(this, true);
        }
        progressDialog.show();
//
        contactList = new ArrayList<>();
        try {
            contactList = BaseApp.DBLoader.findAll(Selector.from(ContactsInviteEntity.class));//
        } catch (DbException e) {
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(contactList)){
            new Thread() {
                public void run() {
                    if (ContextCompat.checkSelfPermission(ContactsInviteActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ContactsInviteActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 3);
                    } else if (ContextCompat.checkSelfPermission(ContactsInviteActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ContactsInviteActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 4);
                    } else if (ContextCompat.checkSelfPermission(ContactsInviteActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(ContactsInviteActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                        getContactList();
                    }
                }
            }.start();
        }else {
            initView();
        }


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


    private void setData() {
        //JDK7中的Collections.Sort方法实现中，如果两个值是相等的，那么compare方法需要返回0，否则可能会在排序时抛错，而JDK6是没有这个限制的
        Collections.sort(contactList, new Comparator<ContactsInviteEntity>() {
            @Override
            public int compare(ContactsInviteEntity a, ContactsInviteEntity b) {
                String idA = a.getCaptialCharStr();
                String idB = b.getCaptialCharStr();
                if (idA.compareTo(idB) == 0) {
                    return 0;
                } else if (idA.compareTo(idB) > 0)
                    return 1;
                else
                    return -1;
            }
        });
        if (StringUtils.isEmpty(contactList)) {
            return;
        }

        mMyLetterListView.setWord(sortArr(contactList));
//        mMyLetterListView.invalidate();


        mMyLetterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        initOverlay();

        alphaIndexer = new HashMap<String, Integer>();
        sections = new String[contactList.size()];
        mAdapter = new ContactsInviteAdapter(this, contactList, listener, alphaIndexer, sections, true);
        mListView.setAdapter(mAdapter);
    }


    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mLayoutOverlay = (View) inflater.inflate(R.layout.view_letterlist_overlay, null);
        mOverlay = (TextView) mLayoutOverlay.findViewById(R.id.mText);
        mLayoutOverlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(mLayoutOverlay, lp);
    }


    private class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                mListView.setSelection(position);
                mOverlay.setText(sections[position]);
                mLayoutOverlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1000);
            }
        }
    }

    //数组去重
    private String[] sortArr(List<ContactsInviteEntity> listContacts) {
        List<String> listTemp = new ArrayList<>();
        for (int i = 0; i < listContacts.size(); i++) {
            ContactsInviteEntity entity = listContacts.get(i);
            entity.setCaptialCharStr(StringUtils.getPinYinHeadChar(entity.getContactname()));
            if (Character.isLetter(entity.getCaptialCharStr().charAt(0))) {
                listTemp.add(entity.getCaptialCharStr());
            }
        }
        if (StringUtils.isEmpty(listTemp)) {
            return new String[0];
        }
        String[] array = new String[listTemp.size()];
        listTemp.toArray(array);
        Arrays.sort(array);
        List<String> list = new ArrayList<>();
        list.add(array[0]);
        for (int i = 1; i < array.length; i++) {
            if (!array[i].equals(list.get(list.size() - 1))) {
                list.add(array[i]);
            }
        }
        String[] arr = new String[list.size()];
        list.toArray(arr);
        return arr;
    }

    private ICustomListener listener = new ICustomListener() {
        @Override
        public void onCustomListener(int obj0, Object obj1, final int position) {
            switch (obj0) {
                case 0:
                    final String phone = contactList.get(position).getContactnumber();
                    if (!phone.contains("-") && !phone.startsWith("+86")) {
                        final String phone1 = phone.replaceAll(" ", "");
                        if (ValidationUtil.isMobile(phone1)) {
                            dialog = new ZBXAlertDialog(ContactsInviteActivity.this, new ZBXAlertListener() {
                                @Override
                                public void onDialogOk(Dialog dlg) {
                                    Invite(ContactsInviteActivity.this, phone1);
//                                contactList.get(position).setInviteOrNot(true);
//                                mAdapter.notifyDataSetChanged();
                                    dlg.dismiss();
                                }

                                @Override
                                public void onDialogCancel(Dialog dlg) {
                                    dlg.dismiss();
                                }
                            }, "", "确定要邀请该联系人吗？");
                            dialog.show();
                        } else {
                            MyToast.showToast("被邀请人的电话格式不正确");
                        }
                    } else if (phone.startsWith("+86")) {
                        String phone1 = phone.replace("+86", "");
                        final String phone2 = phone1.replaceAll(" ", "");
                        if (ValidationUtil.isMobile(phone2)) {
                            dialog = new ZBXAlertDialog(ContactsInviteActivity.this, new ZBXAlertListener() {
                                @Override
                                public void onDialogOk(Dialog dlg) {
                                    Invite(ContactsInviteActivity.this, phone2);
//                                contactList.get(position).setInviteOrNot(true);
//                                mAdapter.notifyDataSetChanged();
                                    dlg.dismiss();
                                }

                                @Override
                                public void onDialogCancel(Dialog dlg) {
                                    dlg.dismiss();
                                }
                            }, "", "确定要邀请该联系人吗？");
                            dialog.show();
                        } else {
                            MyToast.showToast("被邀请人的手机号码格式不正确");
                        }
                    } else {
                        final String phone1 = phone.replaceAll("-", "");
                        if (ValidationUtil.isMobile(phone1)) {
                            dialog = new ZBXAlertDialog(ContactsInviteActivity.this, new ZBXAlertListener() {
                                @Override
                                public void onDialogOk(Dialog dlg) {
                                    Invite(ContactsInviteActivity.this, phone1);
//                                contactList.get(position).setInviteOrNot(true);
//                                mAdapter.notifyDataSetChanged();
                                    dlg.dismiss();
                                }

                                @Override
                                public void onDialogCancel(Dialog dlg) {
                                    dlg.dismiss();
                                }
                            }, "", "确定要邀请该联系人吗？");
                            dialog.show();
                        } else {
                            MyToast.showToast("被邀请人的手机号码格式不正确");
                        }
                    }

                    break;
            }
        }
    };


    public void search() {
        String content = StringUtils.getEditText(etSearch);
        contactList.clear();
        mMyLetterListView.setVisibility(View.GONE);
        try {
            content = "%" + content + "%";
            contactList = BaseApp.DBLoader.findAll(
                    Selector.from(ContactsInviteEntity.class)
                            .where("contactname", "like", content)
                            .or("contactnumber", "like", content));
            if (StringUtils.isEmpty(contactList)) {
                contactList = new ArrayList<>();
                mAdapter = new ContactsInviteAdapter(this, contactList, listener, alphaIndexer, sections, true);
                mListView.setAdapter(mAdapter);
                MyToast.showToast("没找到对应的记录");
            } else {
                mAdapter = new ContactsInviteAdapter(this, contactList, listener, alphaIndexer, sections, true);
                mListView.setAdapter(mAdapter);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    private void initView() {

        setData();
        mAdapter = new ContactsInviteAdapter(this, contactList, listener, alphaIndexer, sections, true);
        mListView.setAdapter(mAdapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                MyToast.showToast("进行一个单个点击");
//            }
//        });

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

        progressDialog.dismiss();

    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {

        @Override
        public void run() {
            mLayoutOverlay.setVisibility(View.GONE);
        }
    }


    public void Invite(Context context, String phone) {
//        Call call = HttpRequest.getIResource().AddFriendRequest(toUserId, "1");
//        callRequest(call, new HttpCallBack(String.class, context, isShowProgress) {
//            @Override
//            public void onSuccess(ResultData mResult) {
//                if ("1".equals(mResult.getSuccess())) {//0成功
//                    MyToast.showToast("好友请求已发送，等待对方同意");
//                } else {
//                    MyToast.showToast(mResult.getMsg());
//                }
//            }
//
//            @Override
//            public void onFailure(String string) {
//                MyToast.showToast(R.string.NETWORKERROR);
//            }
//        });
        MyToast.showToast("进行一个邀请" + phone);


    }


    /**
     * 获取通讯录
     */
    public List<ContactsInviteEntity> getContactList() {

        TelephonyManager telManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = telManager.getLine1Number();
        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";

        contactList = new ArrayList<ContactsInviteEntity>();
        Cursor cursor = this.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, selectCol, select, null,
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
        if (cursor == null) {
            return null;
        }
        if (cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int contactId;
            contactId = cursor.getInt(cursor.getColumnIndex(
                    ContactsContract.Contacts._ID));
            if (cursor.getInt(COL_HAS_PHONE) > 0) {
                // the contact has numbers
                // 获得联系人的电话号码列表
                String displayName;
                displayName = cursor.getString(COL_NAME);                    //获取联系人名
                Cursor phoneCursor = this.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        selPhoneCols,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + "=" + contactId, null, null);
                if (phoneCursor.moveToFirst()) {
                    do {
                        //遍历所有的联系人下面所有的电话号码
                        String contactNumber = phoneCursor.getString(COL_PHONE_NUMBER);

                        ContactsInviteEntity contactData = new ContactsInviteEntity();
                        contactData.setContactname(displayName);
                        contactData.setContactnumber(contactNumber);
                        contactData.setPhonenumber(phoneNumber);
                        contactData.setInviteOrNot(false);
                        contactList.add(contactData);

                    } while (phoneCursor.moveToNext());
                    phoneCursor.close();
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
//        将数据保存到本地的数据库
        initView();
        saveToLocal(contactList);

        return contactList;
    }


    //通讯录保存至本地
    private void saveToLocal(List<ContactsInviteEntity> list) {
        try {
            BaseApp.DBLoader.dropTable(ContactsInviteEntity.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        mDBUtils.deleteAll();
        ContactsInviteEntity[] array = new ContactsInviteEntity[list.size()];
        list.toArray(array);
        mDBUtils.add(array);
    }


    //请求权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            if (3 == requestCode) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactList();
                } else {
                    //未授权
                    MyToast.showToast("请在设置中打开联系人权限");
                }
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 4);
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            if (4 == requestCode) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactList();
                } else {
                    //未授权
                    MyToast.showToast("请在设置中打开联系人权限");
                }
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 4);
        }
    }


}

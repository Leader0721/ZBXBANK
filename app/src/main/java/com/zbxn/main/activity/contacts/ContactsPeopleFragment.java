package com.zbxn.main.activity.contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.pub.base.BaseApp;
import com.pub.base.BaseFragment;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.MyLetterListView;
import com.pub.widget.ProgressLayout;
import com.pub.widget.dbutils.DBUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.adapter.ContactsPeopleAdapter;
import com.zbxn.main.entity.ContactsEntity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 通讯录按拼音排序
 *
 * @author Wuzy
 * @time 2016/8/8
 */
public class ContactsPeopleFragment extends BaseFragment {


    @BindView(R.id.mListView)
    ListView mListView;
    @BindView(R.id.mMyLetterListView)
    MyLetterListView mMyLetterListView;
    @BindView(R.id.mLayout)
    RelativeLayout mLayout;

    View mLayoutOverlay;
    @BindView(R.id.layout_progress)
    ProgressLayout layoutProgress;
    private TextView mOverlay;

    //    private BrandListAdapter adapter;
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;// 存放存在的汉语拼音首字母
    private Handler handler;
    private OverlayThread overlayThread;
    private List<ContactsEntity> mList;

    private ContactsPeopleAdapter adapter;

    private int mPosition = -1;

    //0-查看 1-多选 2-单选
    private int mType;

    private String mId;//公司id

    public void setFragmentTitle(String title) {
        mTitle = title;
    }

    private DBUtils<ContactsEntity> mDBUtils;

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View root = inflater.inflate(R.layout.fragment_contacts_people, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {
        mType = getArguments().getInt("type", 0);
        mId = getArguments().getString("id");

        GetAllUsersByCompanyId();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (mList.get(position).getId() == -1000) {
                    intent = new Intent(getActivity(), ContactsDepartmentActivity.class);
                    intent.putExtra("id", mId);
                    intent.putExtra("type", mType);
                    intent.putExtra("peopleList", (ArrayList<ContactsEntity>) mList);
                    startActivity(intent);
                    return;
                } else if (mList.get(position).getId() == -2000) {
                    intent = new Intent(getActivity(), ContactsDepartmentActivity.class);
                    intent.putExtra("id", mId);
                    intent.putExtra("type", mType);
                    intent.putExtra("peopleList", (ArrayList<ContactsEntity>) mList);
                    startActivity(intent);
                    return;
                }
                if (mType == 0) {
                    //判断当前点击的用户是否为登录用户
                    ContactsEntity contacts = (ContactsEntity) mListView.getAdapter().getItem(position);
                    intent = new Intent(getActivity(), ContactsDetailActivity.class);
                    intent.putExtra(ContactsDetailActivity.Flag_Input_Contactor, contacts);
                    startActivity(intent);
                } else if (mType == 1) {
                } else if (mType == 2) {
                }
            }
        });
    }

    private void setData() {
        try {
            mList = BaseApp.DBLoader.findAll(Selector.from(ContactsEntity.class)
                    .where("CompanyID", "=", mId));

            for (int i = 0; i < mList.size(); i++) {
                ContactsEntity entity = mList.get(i);
                entity.setCaptialChar(StringUtils.getPinYinHeadChar(entity.getUserName()));
            }

            //JDK7中的Collections.Sort方法实现中，如果两个值是相等的，那么compare方法需要返回0，否则可能会在排序时抛错，而JDK6是没有这个限制的
            Collections.sort(mList, new Comparator<ContactsEntity>() {
                @Override
                public int compare(ContactsEntity a, ContactsEntity b) {
                    String idA = a.getCaptialChar();
                    String idB = b.getCaptialChar();
                    if (idA.compareTo(idB) == 0) {
                        return 0;
                    } else if (idA.compareTo(idB) > 0)
                        return 1;
                    else
                        return -1;
                }
            });

            ContactsEntity contacts = new ContactsEntity();
            contacts.setId(-1000);
            contacts.setCaptialChar("部");
            contacts.setDepartmentName("按部门");
            mList.add(0, contacts);
            /*contacts = new ContactsEntity();
            contacts.setId(-2000);
            contacts.setCaptialCharStr("群");
            contacts.setDepartmentName("按群组");
            mList.add(0, contacts);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMyLetterListView.setWord(sortArr(mList));
        mMyLetterListView.invalidate();


        //设置选中数据
        refreshData();

        mMyLetterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        initOverlay();

        alphaIndexer = new HashMap<String, Integer>();
        sections = new String[mList.size()];
        adapter = new ContactsPeopleAdapter(getActivity(), mList, listener, alphaIndexer, sections, true, mType);
        mListView.setAdapter(adapter);
    }

    //获取数据
    public void GetAllUsersByCompanyId() {
        Call call = HttpRequest.getIResource().GetAllUsersByCompanyId(mId);
        callRequest(call, new HttpCallBack(ContactsEntity.class, getActivity(), true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    if (mDBUtils == null) {
                        mDBUtils = new DBUtils<>(ContactsEntity.class);
                    }
                    mDBUtils.delete(WhereBuilder.b("CompanyID", "=", mId));

                    mList = mResult.getDataList();
                    if (StringUtils.isEmpty(mList)) {
                        layoutProgress.showEmpty();
                    } else {
                        layoutProgress.showContent();

                        ContactsEntity[] array = new ContactsEntity[mList.size()];
                        mList.toArray(array);
                        mDBUtils.add(array);

                    }
                    setData();
                } else {
                    MyToast.showToast(mResult.getMsg());
                    layoutProgress.showError(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GetAllUsersByCompanyId();
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
                        GetAllUsersByCompanyId();
                    }
                });
            }
        });
    }

    private ICustomListener listener = new ICustomListener() {
        @Override
        public void onCustomListener(int obj0, Object obj1, int position) {
            mPosition = position;
            ContactsEntity result = (ContactsEntity) obj1;
            switch (obj0) {
                case 0:
                    break;

                default:
                    break;
            }
        }
    };

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mLayoutOverlay = (View) inflater.inflate(R.layout.view_letterlist_overlay, null);
        mOverlay = (TextView) mLayoutOverlay.findViewById(R.id.mText);
        mLayoutOverlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
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

    // 设置overlay不可见
    private class OverlayThread implements Runnable {

        @Override
        public void run() {
            mLayoutOverlay.setVisibility(View.GONE);
        }
    }

    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (EventKey.UPDATECONTACTSSELECT.equals(eventCustom.getTag())) {
            //取得map中所有的key和value
            /*for (Map.Entry<String, String> entry : ContactsChoseActivity.mHashMap.entrySet()) {
                contacts = new Contacts();
                String key = entry.getKey();
                String value = entry.getValue();
                contacts.setId(Integer.parseInt(key));
                contacts.setUserName(value);
            }*/
            refreshData();
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置选中数据
     */
    private void refreshData() {
        List<ContactsEntity> listTemp = new ArrayList<>();
        listTemp.addAll(mList);
        mList.clear();
        for (int i = 0; i < listTemp.size(); i++) {
            if (ContactsChoseActivity.mHashMap.containsKey(listTemp.get(i).getUserId())) {
                listTemp.get(i).setSelected(true);
            } else {
                listTemp.get(i).setSelected(false);
            }
        }
        mList.addAll(listTemp);
    }

    //数组去重
    private String[] sortArr(List<ContactsEntity> listContacts) {
        List<String> listTemp = new ArrayList<>();
        for (int i = 0; i < listContacts.size(); i++) {
            ContactsEntity entity = listContacts.get(i);

            if (StringUtils.isLetterEn(entity.getCaptialChar().charAt(0))) {
                listTemp.add(entity.getCaptialChar());
            } else if ("群".equals(entity.getCaptialChar()) ||
                    "部".equals(entity.getCaptialChar())) {
            } else {
                entity.setCaptialChar("#");
                listTemp.add("#");
            }
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
//        list.add(0, "群");
        list.add(0, "部");
        String[] arr = new String[list.size()];
        list.toArray(arr);
        return arr;
    }
}

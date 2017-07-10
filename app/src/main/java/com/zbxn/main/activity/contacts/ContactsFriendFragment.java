package com.zbxn.main.activity.contacts;

import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.adapter.ContactsFriendAdapter;
import com.zbxn.main.entity.ContactsEntity;
import com.zbxn.main.entity.ContactsFriendEntity;

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
 * 好友成员
 *
 * @author Wuzy
 * @time 2016/8/8
 */
public class ContactsFriendFragment extends BaseFragment {

    @BindView(R.id.mListView)
    PullRefreshListView mListView;
    @BindView(R.id.mMyLetterListView)
    MyLetterListView mMyLetterListView;
    @BindView(R.id.mLayout)
    RelativeLayout mLayout;
    @BindView(R.id.layout_progress)
    ProgressLayout layoutProgress;

    private View mLayoutOverlay;
    private TextView mOverlay;

    private Activity mContext;

    //    private BrandListAdapter adapter;
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;// 存放存在的汉语拼音首字母
    private Handler handler;
    private OverlayThread overlayThread;
    private List<ContactsFriendEntity> lists;

    private ContactsFriendAdapter adapter;

    private int mPosition = -1;

    //0-查看 1-多选 2-单选
    private int mType;

    public void setFragmentTitle(String title) {
        mTitle = title;
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View root = inflater.inflate(R.layout.fragment_contacts_friend, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {

        mType = getArguments().getInt("type", 0);

        mContext = getActivity();

        GetUserFriendList(true);

        mListView.startFirst();
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnabled(false);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (mType == 0) {
                    //判断当前点击的用户是否为登录用户
                    ContactsFriendEntity contacts = lists.get(position);
                    ContactsEntity entityTemp = new ContactsEntity();
                    entityTemp.setUserName(contacts.getRealName());
                    entityTemp.setHeadImgUrl(contacts.getHeadImgUrl());
                    entityTemp.setUserId(contacts.getUserId());
                    intent = new Intent(getActivity(), ContactsDetailActivity.class);
                    intent.putExtra(ContactsDetailActivity.Flag_Input_Contactor, entityTemp);
                    intent.putExtra("isFriends", true);
                    startActivity(intent);
                } else if (mType == 1) {
                } else if (mType == 2) {
                }
            }
        });
        mListView.setOnPullListener(new PullRefreshListView.OnPullListener() {
            @Override
            public void onRefresh() {
                mListView.startFirst();
                GetUserFriendList(false);
            }

            @Override
            public void onLoad() {
            }
        });
    }

    private void setData() {
        //JDK7中的Collections.Sort方法实现中，如果两个值是相等的，那么compare方法需要返回0，否则可能会在排序时抛错，而JDK6是没有这个限制的
        Collections.sort(lists, new Comparator<ContactsFriendEntity>() {
            @Override
            public int compare(ContactsFriendEntity a, ContactsFriendEntity b) {
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
        if (StringUtils.isEmpty(lists)) {
            return;
        }

        mMyLetterListView.setWord(sortArr(lists));
        mMyLetterListView.invalidate();

        //设置选中数据
        refreshData();

        mMyLetterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        initOverlay();

        alphaIndexer = new HashMap<String, Integer>();
        sections = new String[lists.size()];
        adapter = new ContactsFriendAdapter(mContext, lists, listener, alphaIndexer, sections, true, mType);
        mListView.setAdapter(adapter);
        mListView.setImageEmptyLoading("加载中...");
    }


    //获取数据
    public void GetUserFriendList(boolean isShow) {
        Call call = HttpRequest.getIResource().GetUserFriendList();
        callRequest(call, new HttpCallBack(ContactsFriendEntity.class, getActivity(), isShow) {
            @Override
            public void onSuccess(ResultData mResult) {
                mListView.onRefreshFinish();
                if (mResult.getSuccess().equals("1")) {
                    lists = mResult.getDataList();
                    if (StringUtils.isEmpty(lists)) {
                        layoutProgress.showEmpty(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GetUserFriendList(true);
                            }
                        });

                        mListView.setImageEmpty(R.mipmap.loading_haoyouchengyuan,"暂时没有好友成员哦");
                    } else {
                        layoutProgress.showContent();
                    }
                    setData();

                } else {
                    mListView.setImageEmpty(R.mipmap.loading_haoyouchengyuan,"暂时没有好友成员哦");
                    MyToast.showToast(mResult.getMsg());
                    layoutProgress.showError(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GetUserFriendList(true);
                        }
                    });
                }
            }

            @Override
            public void onFailure(String string) {
                mListView.onRefreshFinish();
                mListView.setImageEmpty(R.mipmap.loading_haoyouchengyuan,"暂时没有好友成员哦");
                MyToast.showToast(R.string.NETWORKERROR);
                layoutProgress.showError(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetUserFriendList(true);
                    }
                });
            }
        });
    }

    private ICustomListener listener = new ICustomListener() {
        @Override
        public void onCustomListener(int obj0, Object obj1, int position) {
            mPosition = position;
            ContactsFriendEntity result = (ContactsFriendEntity) obj1;
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
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mLayoutOverlay = (View) inflater.inflate(R.layout.view_letterlist_overlay, null);
        mOverlay = (TextView) mLayoutOverlay.findViewById(R.id.mText);
        mLayoutOverlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
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
        } else if (ContactsDetailActivity.SUCCESS.equals(eventCustom.getTag())) {
            GetUserFriendList(false);
        }
    }

    /**
     * 设置选中数据
     */
    private void refreshData() {
        List<ContactsFriendEntity> listTemp = new ArrayList<>();
        listTemp.addAll(lists);
        lists.clear();
        for (int i = 0; i < listTemp.size(); i++) {
            if (ContactsChoseActivity.mHashMap.containsKey(listTemp.get(i).getUserId() + "")) {
                listTemp.get(i).setSelected(true);
            } else {
                listTemp.get(i).setSelected(false);
            }
        }
        lists.addAll(listTemp);
    }

    //数组去重
    private String[] sortArr(List<ContactsFriendEntity> listContacts) {
        List<String> listTemp = new ArrayList<>();
        for (int i = 0; i < listContacts.size(); i++) {
            ContactsFriendEntity entity = listContacts.get(i);
            entity.setCaptialCharStr(StringUtils.getPinYinHeadChar(entity.getRealName()));
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

}

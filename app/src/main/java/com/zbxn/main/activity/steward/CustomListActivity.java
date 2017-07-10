package com.zbxn.main.activity.steward;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.pub.base.BaseActivity;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.drowdownmenu.DropDownMenu;
import com.pub.widget.drowdownmenu.TabEntity;
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.zbxn.R;
import com.zbxn.main.adapter.CustomListAdapter;
import com.zbxn.main.entity.CustomListEntity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/3/12.
 */
public class CustomListActivity extends BaseActivity {
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    private PullRefreshListView mListView;

    //tab类型
    private int[] types = new int[]{DropDownMenu.TYPE_LIST_CITY};

    private List<TabEntity> mTabList;
    private List<TabEntity> mLeftList;


    private List<CustomListEntity> mList;
    private CustomListAdapter mAdapter;

    //记录状态
    private int state = -1;
    private int mPage = 1;
    private int mPageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawdownmenu_default);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mTabList = new ArrayList<>();
        mTabList.add(new TabEntity("全部", 0));
        mLeftList = new ArrayList<>();
        mLeftList.add(new TabEntity("全部", 0));
        mLeftList.add(new TabEntity("待审核", 1));
        mLeftList.add(new TabEntity("已通过", 2));
        mLeftList.add(new TabEntity("已拒绝", 3));

        mList = new ArrayList<>();
    }

    private void initView() {
        mDropDownMenu.setTextViewIconCenter(false);

        View contentView = getLayoutInflater().inflate(R.layout.activity_steward_customlist, null);
        mListView = (PullRefreshListView) contentView.findViewById(R.id.mListView);
        mListView.setDividerHide();
        mDropDownMenu.setDropDownMenu(mTabList, initViewData(), contentView);
        //该监听回调只监听默认类型，如果是自定义view请自行设置，参照demo
        mDropDownMenu.addMenuSelectListener(new DropDownMenu.OnDefultMenuSelectListener() {
            @Override
            public void onSelectDefaultMenu(int index, int pos, TabEntity clickstr) {
                //index:点击的tab索引，pos：单项菜单中点击的位置索引，clickstr：点击位置的字符串
                state = clickstr.getType();
                setRefresh();
                mListView.startFirst();
            }
        });
        mAdapter = new CustomListAdapter(this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnPullListener(new PullRefreshListView.OnPullListener() {
            @Override
            public void onRefresh() {
                setRefresh();
            }

            @Override
            public void onLoad() {
                getMyApplyList();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CustomListActivity.this, CustomInfoActivity.class);
                intent.putExtra("Phone", StringUtils.isEmpty(mList.get(position).getContacTel()) ? "" : mList.get(position).getContacTel());
                intent.putExtra("LoanApplyId", mList.get(position).getLoanApplyId());
                intent.putExtra("VersionId", mList.get(position).getVersionId());

                startActivity(intent);
            }
        });
        setRefresh();
        mListView.startFirst();
    }

    /**
     * 设置类型和数据源：
     * DropDownMenu.KEY对应类型（DropDownMenu中的常量，参考上述核心源码）
     * DropDownMenu.VALUE对应数据源：key不是TYPE_CUSTOM则传递string[],key是TYPE_CUSTOM类型则传递对应view
     */
    private List<HashMap<String, Object>> initViewData() {
        List<HashMap<String, Object>> viewDatas = new ArrayList<>();
        HashMap<String, Object> map;
        for (int i = 0; i < mTabList.size(); i++) {
            map = new HashMap<String, Object>();
            map.put(DropDownMenu.KEY, types[i]);
            switch (types[i]) {
                case DropDownMenu.TYPE_LIST_CITY:
                    map.put(DropDownMenu.VALUE, mLeftList);
                    //默认选中项
                    map.put(DropDownMenu.SELECT_POSITION, 0);
                    break;
                default:
                    //如果是自定义的就用这个
                    //map.put(DropDownMenu.VALUE, getCustomView());
                    break;
            }
            viewDatas.add(map);
        }
        return viewDatas;
    }

    public void setRefresh() {
        mPage = 1;
        getMyApplyList();
    }

    public void getMyApplyList() {
        String stateStr;
        if (state == -1) {
            stateStr = "";
        } else {
            stateStr = state + "";
        }
        Call call = HttpRequest.getIResource().getApplyByAccountManagerId(stateStr, "", mPage + "", mPageSize + "");
        callRequest(call, new HttpCallBack(CustomListEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    List<CustomListEntity> list = mResult.getDataList();
                    if (mPage == 1) {
                        mList.clear();
                    }
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    mPage++;
                    if (StringUtils.isEmpty(mList)) {
                        mListView.setImageEmpty(R.mipmap.loading_xiaoxi, "暂时没有相关数据哦");
                    }
                } else {
                    mListView.setImageEmpty(R.mipmap.loading_xiaoxi, "暂时没有相关数据哦");
                    MyToast.showToast(mResult.getMsg());
                }
                mListView.onRefreshFinish();
            }

            @Override
            public void onFailure(String string) {
                mListView.setImageEmpty(R.mipmap.loading_xiaoxi, "暂时没有相关数据哦");
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }


    @Override
    public void initRight() {
        super.initRight();
        setTitle("客户列表");
        setRight1Show(true);
        setRight1Icon(R.mipmap.nav_search);
    }

    @Override
    public void actionRight1(MenuItem menuItem) {
        super.actionRight1(menuItem);
        Intent intent = new Intent(this, CustomSearchActivity.class);
        intent.putExtra("state", state);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }


    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (EventKey.STEWARDLIST.equals(eventCustom.getTag())) {
            setRefresh();
        }
    }


}

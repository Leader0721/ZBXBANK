package com.zbxn.main.activity.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.pub.base.BaseActivity;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.JsonUtil;
import com.pub.utils.MyToast;
import com.pub.utils.PreferencesUtils;
import com.pub.utils.StringUtils;
import com.pub.widget.drowdownmenu.DropDownMenu;
import com.pub.widget.drowdownmenu.TabEntity;
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.zbxn.R;
import com.zbxn.main.activity.credit.search.SearchActivity;
import com.zbxn.main.adapter.CreditApplyAdapter;
import com.zbxn.main.entity.BankListEntity;
import com.zbxn.main.entity.CreditApplyEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 草稿箱
 *
 * @author: ysj
 * @date: 2017-03-13 17:55
 */
public class CreditDraftActivity extends BaseActivity {

    public static final int REFRESH = 10000;

    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    private PullRefreshListView mListView;

    //tab类型
    private int[] types = new int[]{DropDownMenu.TYPE_LIST_CITY};

    private List<TabEntity> mTabList;
    private List<TabEntity> mLeftList;

    private List<CreditApplyEntity> mList;
    private CreditApplyAdapter mAdapter;
    private List<BankListEntity> mBankList;
    private String bankId;

    private int mPage = 1;
    private int mPageSize = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawdownmenu_default);
        ButterKnife.bind(this);
//        mDropDownMenu.setTabMenuViewGone();
        setTitle("草稿箱");
        initData();
        initView();
    }

    @Override
    public void initRight() {
        super.initRight();
        setRight1Icon(R.mipmap.nav_search);
        setRight1("搜索");
        setRight1Show(true);
    }

    @Override
    public void actionRight1(MenuItem menuItem) {
        super.actionRight1(menuItem);
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("name", "CreditDraftActivity");
        intent.putExtra("bankId", bankId);
        startActivity(intent);
    }

    private void initData() {
        mTabList = new ArrayList<>();
        mTabList.add(new TabEntity("银行类型", 0));

        String bankListStr = PreferencesUtils.getString(this, CreditActivity.BANKLISTSTR);
        if (!StringUtils.isEmpty(bankListStr)) {
            mBankList = JsonUtil.fromJsonList(bankListStr, BankListEntity.class);
        }
        mLeftList = new ArrayList<>();
        mLeftList.add(new TabEntity("银行类型", -1));
        if (!StringUtils.isEmpty(mBankList)) {
            for (int i = 0; i < mBankList.size(); i++) {
                TabEntity entity = new TabEntity();
                entity.setName(mBankList.get(i).getBankName());
                entity.setTypeStr(mBankList.get(i).getBankId());
                mLeftList.add(entity);
            }
        }
        mList = new ArrayList<>();
    }

    private void initView() {
        mDropDownMenu.setTextViewIconCenter(false);
        View contentView = getLayoutInflater().inflate(R.layout.activity_credit_draft, null);
        mListView = (PullRefreshListView) contentView.findViewById(R.id.mListView);
        mListView.setDividerHide();
        mDropDownMenu.setDropDownMenu(mTabList, initViewData(), contentView);
        //该监听回调只监听默认类型，如果是自定义view请自行设置，参照demo
        mDropDownMenu.addMenuSelectListener(new DropDownMenu.OnDefultMenuSelectListener() {
            @Override
            public void onSelectDefaultMenu(int index, int pos, TabEntity clickstr) {
                //index:点击的tab索引，pos：单项菜单中点击的位置索引，clickstr：点击位置的字符串
//                Toast.makeText(getBaseContext(), index + "---" + clickstr.getName() + "---" + clickstr.getType(), Toast.LENGTH_SHORT).show();
                bankId = clickstr.getTypeStr();
                mListView.startFirst();
                setRefresh();
            }
        });
        mAdapter = new CreditApplyAdapter(this, mList, 2);
        mListView.setAdapter(mAdapter);
        mListView.setImageEmptyLoading("加载中...");

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CreditDraftActivity.this, CreditProgressActivity.class);
                intent.putExtra("From", "3");//1新建  2查看申请  3草稿箱
                intent.putExtra("LoanApplyId", mList.get(position).getLoanApplyId());
                intent.putExtra("VersionId", mList.get(position).getVersionId());
                startActivityForResult(intent, REFRESH);

                /*JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("LoanApplyId", mList.get(position).getLoanApplyId());
                jsonObject.addProperty("VersionId", mList.get(position).getVersionId());
                jsonObject.addProperty("CreateType", "0");//用户：0；银行：1

                Intent intent = new Intent(CreditDraftActivity.this, WebviewActivity.class);
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("From", "3");//1新建  2查看申请  3草稿箱
                intent.putExtra("Step",  mList.get(position).getStep());
                intent.putExtra("LoanApplyId", mList.get(position).getLoanApplyId());
                startActivity(intent);*/
            }
        });
        mListView.setOnPullListener(new PullRefreshListView.OnPullListener() {
            @Override
            public void onRefresh() {
                setRefresh();
            }

            @Override
            public void onLoad() {
                getDraftList();
            }
        });
        setRefresh();
        mListView.startFirst();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REFRESH) {
//            if (resultCode == RESULT_OK) {
                setRefresh();
                mListView.startFirst();
//            }
        }
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

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    public void setRefresh() {
        mPage = 1;
        getDraftList();
    }

    public void getDraftList() {
        Call call = HttpRequest.getIResource().getDraftList(mPage + "", mPageSize + "", "", bankId);
        callRequest(call, new HttpCallBack(CreditApplyEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                mListView.onRefreshFinish();
                if (mResult.getSuccess().equals("1")) {
                    List<CreditApplyEntity> list = mResult.getDataList();
                    if (mPage == 1) {
                        mList.clear();
                    }
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    mPage++;
                    setMore(list);
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
                mListView.setImageEmpty(R.mipmap.loading_shuju, "暂时没有数据哦");
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
                mListView.onRefreshFinish();
                mListView.setImageEmpty(R.mipmap.loading_shuju, "暂时没有数据哦");
            }
        });
    }

    /**
     * 显示加载更多
     *
     * @param mResult
     */
    private void setMore(List mResult) {
        if (mResult == null) {
            mListView.setHasMoreData(true);
            return;
        }
        int pageTotal = mResult.size();
        if (pageTotal >= mPageSize) {
            mListView.setHasMoreData(true);
            mListView.setPullLoadEnabled(true);
        } else {
            mListView.setHasMoreData(false);
            mListView.setPullLoadEnabled(false);
        }
    }
}

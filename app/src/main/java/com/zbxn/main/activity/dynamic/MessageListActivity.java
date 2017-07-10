package com.zbxn.main.activity.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.pub.base.BaseActivity;
import com.pub.common.EventCustom;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.zbxn.R;
import com.zbxn.main.adapter.PersonalMessageListAdapter;
import com.zbxn.main.entity.PersonalMessageEntity;

import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class MessageListActivity extends BaseActivity {

    @BindView(R.id.mListView)
    PullRefreshListView mListView;

    private PersonalMessageListAdapter adapter;
    private List<PersonalMessageEntity> mList;

    private int mPage = 1;
    private int mPageSize = 10;
    private int type;
    private String creatTime;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);
        setTitle("消息");
        creatTime = sdf.format(new Date());
        initView();
    }

    private void initView() {
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0://系统
                setTitle("系统消息");
                break;
            case 1://消息
                setTitle("消息");
                break;
            case 12://贷款申请
                setTitle("贷款申请");
                break;
            case 13://信贷管理
                setTitle("信贷管理");
                break;
            default:
                setTitle("消息");
                break;
        }
        mList = new ArrayList<>();
        adapter = new PersonalMessageListAdapter(this, mList);
        mListView.setAdapter(adapter);
        mListView.setImageEmptyLoading("加载中...");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.get(position).isProcessed()) {
                    return;
                } else {
                    if (mList.get(position).getMessageType() == 4) {//企业邀请
                        Intent intent = new Intent(MessageListActivity.this, CompanyRequestsActivity.class);
                        intent.putExtra("id", mList.get(position).getId() + "");
                        intent.putExtra("body", mList.get(position).getBody() + "");
                        intent.putExtra("InviteId", mList.get(position).getInviteId() + "");
                        startActivity(intent);
                    } else if (mList.get(position).getMessageType() == 3){//好友邀请
                        Intent intent = new Intent(MessageListActivity.this, StaffRequestActivity.class);
                        intent.putExtra("userID",mList.get(position).getUserId());
                        intent.putExtra("id",mList.get(position).getId()+"");
                        startActivity(intent);
                    }
                }
            }
        });
        mListView.setOnPullListener(new PullRefreshListView.OnPullListener() {
            @Override
            public void onRefresh() {
                setRefresh();
            }

            @Override
            public void onLoad() {
                GetMessageListByUserId();
            }
        });
        mListView.startFirst();
        setRefresh();
    }

    public void setRefresh() {
        mPage = 1;
        creatTime = sdf.format(new Date());
        GetMessageListByUserId();
    }

    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (CompanyRequestsActivity.SUCCESS.equals(eventCustom.getTag())) {
            setRefresh();
        }

    }

    //数据请求
    private void GetMessageListByUserId() {
        Call call = HttpRequest.getIResource().GetMessageListById(0,mPage,mPageSize,creatTime);
        callRequest(call, new HttpCallBack(PersonalMessageEntity.class, this, false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    List<PersonalMessageEntity> list = mResult.getDataList();
                    if (list.size()>0) {
                        creatTime = list.get(list.size() - 1).getCreationDateStr();
                    }
                    if (mPage == 1) {
                        mList.clear();
                    }
                    mPage++;
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();
                    setMore(list);
                } else {
                    MyToast.showToast(mResult.getMsg());
                }
                mListView.onRefreshFinish();
                //如果成功且有数据会自动隐藏，没有数据则显示
                mListView.setImageEmpty(R.mipmap.loading_shuju,"暂时没有数据哦");
            }

            @Override
            public void onFailure(String string) {
                MyToast.showToast(R.string.NETWORKERROR);
                mListView.onRefreshFinish();
                mListView.setImageEmpty(R.mipmap.loading_shuju,"暂时没有数据哦");
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

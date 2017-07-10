package com.zbxn.main.activity.dynamic;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.pub.base.BaseApp;
import com.pub.base.BaseFragment;
import com.pub.dialog.ZBXAlertDialog;
import com.pub.dialog.ZBXAlertListener;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.adapter.DynamicMessageListAdapter;
import com.zbxn.main.entity.DynamicInviteEntity;
import com.zbxn.main.entity.PersonalMessageEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 好友消息
 *
 * @author: ysj
 * @date: 2017-03-23 11:10
 */
public class FriendsMessageFragment extends BaseFragment {

    @BindView(R.id.mListView)
    PullRefreshListView mListView;
    private ArrayList<PersonalMessageEntity> mList;
    private DynamicMessageListAdapter mAdapter;
    private int mPageSize = 10;
    private int mPageIndex = 0;
    private String id;
    private ZBXAlertDialog mDialog;

    public void setFragmentTitle(String title) {
        mTitle = title;
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_friends_message, null);
        ButterKnife.bind(this, view);
//        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnabled(false);
        initView();

        setRefresh();
        return view;
    }

    private ICustomListener mListener = new ICustomListener() {
        @Override
        public void onCustomListener(int obj0, Object obj1, final int position) {
            final PersonalMessageEntity entity = (PersonalMessageEntity) obj1;
            switch (obj0) {
                case 0:
                    RespondAddFriend("true", entity, position);
                    break;
            }
        }
    };

    private void initView() {
        mListView.setDividerHide();
        mList = new ArrayList<>();
        mAdapter = new DynamicMessageListAdapter(getContext(), mList, mListener);
        mListView.setAdapter(mAdapter);
        mListView.setImageEmptyLoading("加载中...");
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                PersonalMessageEntity entity = mList.get(position);
                if (!entity.isProcessed()) {
                    if (entity.getType() == 1 || entity.getType() == 3) {
                        mDialog = new ZBXAlertDialog(getActivity(), new ZBXAlertListener() {
                            @Override
                            public void onDialogOk(Dialog dlg) {
                                RespondAddFriend("false", mList.get(position), position);
                                mDialog.dismiss();
                            }

                            @Override
                            public void onDialogCancel(Dialog dlg) {
                                mDialog.dismiss();
                            }
                        }, "提示", "确定要拒绝对方的好友请求吗?");
                        mDialog.show();
                    }
                }
                return false;
            }
        });

        mListView.setOnPullListener(new PullRefreshListView.OnPullListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 0;
                GetUserMessageList();
            }

            @Override
            public void onLoad() {
                GetUserMessageList();
            }
        });
    }

    /**
     * 刷新 外部调用
     */
    public void setRefresh() {
        mPageIndex = 0;
//        mListView.startFirst();
        GetUserMessageList();
    }

    //同意或拒绝请求
    private void RespondAddFriend(String isagree, final PersonalMessageEntity entity, final int position) {
        Call call = HttpRequest.getIResource().RespondAddFriend(entity.getId() + "", isagree);
        callRequest(call, new HttpCallBack(DynamicInviteEntity.class, BaseApp.getContext(), true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    mList.remove(position);
                    entity.setProcessed(true);
                    mList.add(position, entity);
                    mAdapter.notifyDataSetChanged();
                }
                MyToast.showToast(mResult.getMsg());
                mListView.onComplete();
                mListView.setImageEmpty(R.mipmap.loading_shuju,"暂时没有数据哦");
            }

            @Override
            public void onFailure(String string) {
                mListView.onComplete();
                MyToast.showToast(R.string.NETWORKERROR);
                mListView.setImageEmpty(R.mipmap.loading_shuju,"暂时没有数据哦");
            }
        });
    }

    /**
     * 获取用户消息（会话）列表
     */
    public void GetUserMessageList() {
        //请求网络
        Call call = HttpRequest.getIResource().getUserMessageList("1", mPageSize + "", mPageIndex + "");
        callRequest(call, new HttpCallBack(PersonalMessageEntity.class, getActivity(), false) {
            @Override
            public void onSuccess(ResultData mResult) {
                if ("1".equals(mResult.getSuccess())) {
                    List<PersonalMessageEntity> childList = mResult.getDataList();
                    if (mPageIndex == 0) {
                        mList.clear();
                    }
                    mList.addAll(childList);
                    mAdapter.notifyDataSetChanged();
//                    setMore(childList);

                    mPageIndex++;
                    if (StringUtils.isEmpty(mList)) {
                        mListView.setImageEmpty(R.mipmap.loading_haoyouxiaoxi, "暂时没有好友消息哦");
                    }
                } else {
                    MyToast.showToast(mResult.getMsg());
                    mListView.setImageEmpty(R.mipmap.loading_haoyouxiaoxi, "暂时没有好友消息哦");
                }
                mListView.onRefreshFinish();
            }

            @Override
            public void onFailure(String string) {
                mListView.onRefreshFinish();
                mListView.setImageEmpty(R.mipmap.loading_haoyouxiaoxi, "暂时没有好友消息哦");
                MyToast.showToast(R.string.NETWORKERROR);
            }
        });
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {

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

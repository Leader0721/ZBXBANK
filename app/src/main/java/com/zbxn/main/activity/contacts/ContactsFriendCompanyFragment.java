package com.zbxn.main.activity.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.pub.base.BaseFragment;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.ProgressLayout;
import com.pub.widget.pulltorefreshlv.PullRefreshListView;
import com.zbxn.R;
import com.zbxn.main.adapter.ContactsCompanyAdapter;
import com.zbxn.main.entity.CompanyEntity;
import com.zbxn.main.entity.ContactsEntity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

/**
 * 企业好友
 *
 * @author Wuzy
 * @time 2016/8/8
 */
public class ContactsFriendCompanyFragment extends BaseFragment {

    @BindView(R.id.layout_progress)
    ProgressLayout layoutProgress;
    @BindView(R.id.mListView)
    PullRefreshListView mListView;

    private List<CompanyEntity> mList = new ArrayList<>();
    private ContactsCompanyAdapter mAdapter;

    public void setFragmentTitle(String title) {
        mTitle = title;
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View root = inflater.inflate(R.layout.activity_listview_pullrefresh, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {
        mList = new ArrayList<>();

        GetCompanyListByUserId();

        mListView.startFirst();
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnabled(false);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ContactsPeopleActivity.class);
                intent.putExtra("id", mList.get(position).getID());
                intent.putExtra("title", mList.get(position).getCompanyName());
                startActivity(intent);
            }
        });
        mListView.setOnPullListener(new PullRefreshListView.OnPullListener() {
            @Override
            public void onRefresh() {
                mListView.startFirst();
                GetCompanyListByUserId();
            }

            @Override
            public void onLoad() {
            }
        });
    }


    @Subscriber
    public void onEventMainThread(EventCustom eventCustom) {
        if (EventKey.COMPANYREFRESH.equals(eventCustom.getTag())) {
            mListView.startFirst();
            GetCompanyListByUserId();
        }

    }
    //获取数据
    public void GetCompanyListByUserId() {
        Call call = HttpRequest.getIResource().GetCompanyListByUserId();
        callRequest(call, new HttpCallBack(CompanyEntity.class, getActivity(), false) {
            @Override
            public void onSuccess(ResultData mResult) {
                mListView.onRefreshFinish();
                if (mResult.getSuccess().equals("1")) {
                    mList = mResult.getDataList();
                    if (StringUtils.isEmpty(mList)) {
                        layoutProgress.showEmpty(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GetCompanyListByUserId();
                            }
                        });
                        mListView.setImageEmpty(R.mipmap.loading_qiyetongshi,"暂时没有企业同事哦");
                    } else {
                        layoutProgress.showContent();
                    }

                    mAdapter = new ContactsCompanyAdapter(getActivity(), mList, null);
                    mListView.setAdapter(mAdapter);
                } else {
                    mListView.setImageEmpty(R.mipmap.loading_qiyetongshi,"暂时没有企业同事哦");
                    MyToast.showToast(mResult.getMsg());
                    layoutProgress.showError(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GetCompanyListByUserId();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String string) {
                mListView.onRefreshFinish();
                mListView.setImageEmpty(R.mipmap.loading_qiyetongshi,"暂时没有企业同事哦");
                MyToast.showToast(R.string.NETWORKERROR);
                layoutProgress.showError(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetCompanyListByUserId();
                    }
                });
            }
        });
    }

    /**
     * 选择接收人回调
     */
    private static final int Flag_Callback_ContactsPicker = 1001;//
    //选择人员负责人
    private ArrayList<ContactsEntity> mListContactsCharge = new ArrayList<>();

    /**
     * 回调需要接收的人员
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == Flag_Callback_ContactsPicker) { //负责人
            mListContactsCharge = (ArrayList<ContactsEntity>) data.getExtras().getSerializable(ContactsChoseActivity.Flag_Output_Checked);
//            mExecuterArray = new String[mListContactsCharge.size()];
            String content = "";
            //所有执行人id字符串
            String executersId = "";
            for (int i = 0; i < mListContactsCharge.size(); i++) {
//                mExecuterArray[i] = mListContactsCharge.get(i).getId() + "";
                content += mListContactsCharge.get(i).getUserName() + "、";
                executersId += mListContactsCharge.get(i).getUserId() + ",";
            }
            if (!StringUtils.isEmpty(executersId)) {
                content = content.substring(0, content.length() - 1);
                executersId = executersId.substring(0, executersId.length() - 1);
            }

            if (mListContactsCharge.size() >= 2) {
                content = mListContactsCharge.get(0).getUserName() + "、" + mListContactsCharge.get(1).getUserName() + "等" + mListContactsCharge.size() + "人";
            }
//            tvExecutorPeople.setText(content);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

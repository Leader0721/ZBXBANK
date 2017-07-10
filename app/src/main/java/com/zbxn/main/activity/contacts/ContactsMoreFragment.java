package com.zbxn.main.activity.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pub.base.BaseFragment;
import com.pub.http.HttpCallBack;
import com.pub.http.HttpRequest;
import com.pub.http.ResultData;
import com.pub.utils.MyToast;
import com.pub.utils.StringUtils;
import com.pub.widget.ProgressLayout;
import com.zbxn.R;
import com.zbxn.main.adapter.ContactsCompanyAdapter;
import com.zbxn.main.entity.CompanyEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 选择更多好友
 *
 * @author Wuzy
 * @time 2016/8/8
 */
public class ContactsMoreFragment extends BaseFragment {

    @BindView(R.id.mListView)
    ListView mListView;
    @BindView(R.id.layout_progress)
    ProgressLayout layoutProgress;

    private List<CompanyEntity> mList = new ArrayList<>();
    private ContactsCompanyAdapter mAdapter;

    //0-查看 1-多选 2-单选
    private int mType;

    public void setFragmentTitle(String title) {
        mTitle = title;
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View root = inflater.inflate(R.layout.activity_listview, container, false);
        ButterKnife.bind(this, root);
        mType = getArguments().getInt("type", 0);
        return root;
    }

    @Override
    protected void initialize(View root, @Nullable Bundle savedInstanceState) {
        mList = new ArrayList<>();

        GetCompanyListByUserId();

        mAdapter = new ContactsCompanyAdapter(getActivity(), mList, null);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ContactsPeopleActivity.class);
                intent.putExtra("type", mType);//0-查看 1-多选 2-单选
//                startActivityForResult(intent, Flag_Callback_ContactsPicker);
                startActivity(intent);
            }
        });
    }

    //获取数据
    public void GetCompanyListByUserId() {
        Call call = HttpRequest.getIResource().GetCompanyListByUserId();
        callRequest(call, new HttpCallBack(CompanyEntity.class, getActivity(), true) {
            @Override
            public void onSuccess(ResultData mResult) {
                if (mResult.getSuccess().equals("1")) {
                    mList = mResult.getDataList();
                    if (StringUtils.isEmpty(mList)) {
                        layoutProgress.showEmpty();
                    } else {
                        layoutProgress.showContent();
                    }

                    mAdapter = new ContactsCompanyAdapter(getActivity(), mList, null);
                    mListView.setAdapter(mAdapter);
                } else {
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
}

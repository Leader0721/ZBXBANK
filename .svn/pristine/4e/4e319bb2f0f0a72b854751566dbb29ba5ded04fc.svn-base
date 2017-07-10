package com.zbxn.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.entity.ContactsEntity;

import java.util.List;

/**
 * 选中人员 底部
 * 创建人：Wuzy
 * 创建时间：2016/10/10 10:05
 */
public class ContactsSelectPeopleAdapter extends
        RecyclerView.Adapter<ContactsSelectPeopleAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<ContactsEntity> mList;

    private ICustomListener mOnItemClickLitener;

    public ContactsSelectPeopleAdapter(Context context, List<ContactsEntity> list) {
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    public void setOnItemClickListener(ICustomListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        TextView tvPeople;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = mInflater.inflate(R.layout.list_item_contacts_select_people, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.tvPeople = (TextView) view.findViewById(R.id.tv_people);

        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final ContactsEntity entity = mList.get(i);

        String str = entity.getUserName();
        if (!StringUtils.isEmpty(str)) {
            if (str.length() >= 2) {// 判断是否长度大于2
                str = str.substring(str.length() - 2);//显示用户名后两位
            }
        } else {
            str = "";
        }
        viewHolder.tvPeople.setText(str);

        //如果设置了回调，则设置点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickLitener) {
                    mOnItemClickLitener.onCustomListener(0, entity, i);
                }
            }
        });
    }

}
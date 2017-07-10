package com.zbxn.main.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.entity.ContactsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称：申请列表的adapter
 * 创建人：Wuzy
 * 创建时间：2016/10/10 10:05
 */
public class ContactsPeopleAddAdapter extends BaseAdapter {

    private Activity mContext;
    private List<ContactsEntity> mList;
    private ICustomListener mListener;


    public ContactsPeopleAddAdapter(Activity mContext, List<ContactsEntity> mList, ICustomListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_item_contacts_people_add, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ContactsEntity entity = mList.get(position);

        holder.mRemarkName.setText(entity.getUserName() + "");
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCustomListener(0, entity, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.mLayout)
        LinearLayout mLayout;
        @BindView(R.id.mPortrait)
        CircleImageView mPortrait;
        @BindView(R.id.mRemarkName)
        TextView mRemarkName;
        @BindView(R.id.tv_add)
        TextView tvAdd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

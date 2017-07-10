package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.entity.ContactsGroupEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称：申请列表的adapter
 * 创建人：Wuzy
 * 创建时间：2016/10/10 10:05
 */
public class ContactsGroupAdapter extends BaseAdapter {

    private Context mCxontext;
    private List<ContactsGroupEntity> mList;
    private ICustomListener listener;

    public ContactsGroupAdapter(Context mContext, List<ContactsGroupEntity> mList, ICustomListener listener) {
        this.mCxontext = mContext;
        this.mList = mList;
        this.listener = listener;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mCxontext, R.layout.list_item_contacts_company, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactsGroupEntity entity = mList.get(position);

        //姓名
        holder.mRemarkName.setText(entity.getCompanyName() + "");

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.mPortrait)
        CircleImageView mPortrait;
        @BindView(R.id.tv_head)
        TextView tvHead;
        @BindView(R.id.civ_Portrait)
        CircleImageView civPortrait;
        @BindView(R.id.mRemarkName)
        TextView mRemarkName;
        @BindView(R.id.mImage)
        ImageView mImage;
        @BindView(R.id.mLayout)
        LinearLayout mLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

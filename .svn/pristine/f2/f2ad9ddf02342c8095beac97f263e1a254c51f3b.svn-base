package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zbxn.R;
import com.zbxn.main.entity.CompanyEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: ysj
 * @date: 2017-03-14 10:45
 */
public class CreditApplySelectAdapter extends BaseAdapter {

    private Context mContext;
    private List<CompanyEntity> mList;

    public CreditApplySelectAdapter(Context mContext, List<CompanyEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
            convertView = View.inflate(mContext, R.layout.list_item_credit_select, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(mList.get(position).getCompanyName());
        if (position == 0) {
            holder.imgSelect.setSelected(true);
        }else {
            holder.imgSelect.setSelected(false);
        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.img_select)
        ImageView imgSelect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

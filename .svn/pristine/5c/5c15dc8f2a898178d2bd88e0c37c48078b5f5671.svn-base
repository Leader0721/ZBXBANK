package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zbxn.R;
import com.zbxn.main.entity.BankListEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: ysj
 * @date: 2017-03-21 16:01
 */
public class BankListAdapter extends BaseAdapter {

    private Context mContext;
    private List<BankListEntity> mList;
    private String selectId;
    private int type;

    public BankListAdapter(Context mContext, List<BankListEntity> mList, String selectId, int type) {
        this.mContext = mContext;
        this.mList = mList;
        this.selectId = selectId;
        this.type = type;
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
        if (type == 1) {//银行
            holder.tvName.setText(mList.get(position).getBankName());

            if (selectId != null) {
                if (selectId.equals(mList.get(position).getBankId())) {
                    holder.imgSelect.setSelected(true);
                } else {
                    holder.imgSelect.setSelected(false);
                }
            }
        } else {//客户经理
            holder.tvName.setText(mList.get(position).getUserName());

            if (selectId != null) {
                if (selectId.equals(mList.get(position).getUserId())) {
                    holder.imgSelect.setSelected(true);
                } else {
                    holder.imgSelect.setSelected(false);
                }
            }
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.img_select)
        ImageView imgSelect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

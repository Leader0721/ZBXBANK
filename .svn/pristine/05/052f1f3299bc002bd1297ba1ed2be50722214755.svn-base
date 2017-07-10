package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zbxn.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: ysj
 * @date: 2017-03-12 13:55
 */
public class CreditAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;

    public CreditAdapter(Context mContext, List<String> mList) {
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
            convertView = View.inflate(mContext, R.layout.list_item_credit, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvContent.setText(mList.get(position));
        switch (position) {
            case 0:
                holder.imgHead.setImageResource(R.mipmap.credit_apply);
                break;
            case 1:
                holder.imgHead.setImageResource(R.mipmap.credit_kep);
                break;
            case 2:
                holder.imgHead.setImageResource(R.mipmap.credit_drafts);
                break;
            case 3:
                holder.imgHead.setImageResource(R.mipmap.credit_upload);
                break;
            default:
                holder.imgHead.setImageResource(R.mipmap.userhead_img);
                break;
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

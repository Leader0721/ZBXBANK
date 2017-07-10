package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.entity.CustomListEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/13.
 */
public class CustomListAdapter extends BaseAdapter {
    private Context mContext;
    private List<CustomListEntity> mList;

    public CustomListAdapter(Context mContext, List<CustomListEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
//        return 10;
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
            convertView = View.inflate(mContext, R.layout.list_item_customlist, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(mList.get(position).getCompanyName());

//        holder.tvState.setText(mList.get(position).getStautsName());
        if (mList.get(position).getStatusCode() == 1) {
            holder.tvState.setText("待审核");
        } else if (mList.get(position).getStatusCode() == 2) {
            holder.tvState.setText("已通过");
        } else if (mList.get(position).getStatusCode() == 3) {
            holder.tvState.setText("已拒绝");
        } else {
            holder.tvState.setText("待审核");
        }


        if (mList.get(position).getStatusCode() == 1) {
            holder.tvState.setTextColor(mContext.getResources().getColor(R.color.orange));
        } else {
            holder.tvState.setTextColor(mContext.getResources().getColor(R.color.tvc6));

        }
//        switch (mList.get(position).getStautsName()){
//            case "1":
//                holder.tvState.setText("待审核");
//                holder.tvState.setTextColor(Color.YELLOW);
//                break;
//            case "2":
//                holder.tvState.setText("已通过");
//                break;
//            case "3":
//                holder.tvState.setText("已拒绝");
//                break;
//            default:
//                holder.tvState.setText("成交状态");
//                break;
//        }
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String  seconds = mList.get(position).getApplyDate().substring(6,19);
//        holder.tvTime.setText(StringUtils.getDateTime(seconds,format));
        holder.tvTime.setText(StringUtils.convertDateWithMin(mList.get(position).getApplyDate()));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pub.utils.DateUtils;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.entity.DynamicEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author: ysj
 * @date: 2017-03-09 17:11
 */
public class DynamicAdapter extends BaseAdapter {

    private Context mContext;
    private List<DynamicEntity> mList;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DynamicAdapter(Context mContext, List<DynamicEntity> mList) {
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_item_dynamic, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(mList.get(position).getTitile());
//        holder.tvContent.setText(mList.get(position).getMessage());
        switch (mList.get(position).getType()) {
            case 0://系统
                holder.imgHead.setImageResource(R.mipmap.dynamic_system);
                holder.tvContent.setText("系统通知消息");
                holder.tvType.setVisibility(View.VISIBLE);
                break;
            case 1://消息
                holder.imgHead.setImageResource(R.mipmap.dynamic_news);
                holder.tvType.setVisibility(View.GONE);
                break;
            case 12://贷款申请
                holder.imgHead.setImageResource(R.mipmap.dynamic_agricole);
                holder.tvContent.setText("企业贷款申请消息");
                holder.tvType.setVisibility(View.GONE);
                break;
            case 13://信贷管理
                holder.imgHead.setImageResource(R.mipmap.dynamic_butler);
                holder.tvContent.setText("银行信贷管理消息");
                holder.tvType.setVisibility(View.GONE);
                break;
            default:
                holder.imgHead.setImageResource(R.mipmap.userhead_img);
                holder.tvType.setVisibility(View.GONE);
                break;
        }

        if (mList.get(position).getCreationDateStr() != null) {
            holder.tvTime.setText(DateUtils.fromTodaySimple(StringUtils.convertToDate(sdf, mList.get(position).getCreationDateStr())));
        }
        holder.tvCount.setVisibility(View.GONE);
        holder.tvTime.setVisibility(View.GONE);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_head)
        CircleImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_count)
        TextView tvCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

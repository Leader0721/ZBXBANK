package com.zbxn.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.entity.ReportListEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/16.
 */
public class FinalExcelAdapter extends BaseAdapter {
    private Context mContext;
    private List<ReportListEntity> mList;
    private String type = "";
    private ICustomListener listener;

    public FinalExcelAdapter(Context mContext, List<ReportListEntity> mList, String type, ICustomListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.type = type;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_item_view_profitexcel, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ReportListEntity entity = mList.get(position);

        if (!mList.get(position).isIsUpLoad() || mList.get(position).getFormInfo() == null) {
            holder.llOne.setVisibility(View.GONE);
            holder.llZero.setVisibility(View.GONE);
            holder.llTwo.setVisibility(View.VISIBLE);
            if (type.equals("1")) {
                holder.tvUpLoadFile.setText("去上传");
            } else {
                holder.tvUpLoadFile.setText("去提醒");
            }
            holder.tvUpLoadFile.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        } else {
            holder.llOne.setVisibility(View.VISIBLE);
            holder.llZero.setVisibility(View.VISIBLE);
            holder.llTwo.setVisibility(View.GONE);
            if (type.equals("1")) {
                holder.tvUpLoad.setVisibility(View.VISIBLE);
            } else {
                holder.tvUpLoad.setVisibility(View.GONE);
            }
            if (mList.get(position).getFormInfo() != null) {
                holder.tvTitle.setText(mList.get(position).getFormInfo().get(0).getRealName());
                if (!StringUtils.isEmpty(mList.get(position).getFormInfo().get(0).getFilePath())) {
                    holder.tvDate.setText(StringUtils.convertDateWithMin(mList.get(position).getCreateDate()));
                }
            }
        }


        holder.tvYear.setText(mList.get(position).getDate().substring(0, 4));
        holder.tvMonth.setText(mList.get(position).getDate().substring(5, 7));

        holder.tvUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCustomListener(1, entity, position);//进行上传附件
            }
        });
        if (type.equals("1")) {
            holder.tvUpLoadFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCustomListener(2, entity, position);//重新进行上传附件
                }
            });
        } else {
            holder.tvUpLoadFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCustomListener(3, entity, position);//進行一個提醒
                }
            });
        }


        return convertView;
    }


//    public String paserTime(int time){
//        System.setProperty("user.timezone", "Asia/Shanghai");
//        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
//        TimeZone.setDefault(tz);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String times = format.format(new Date(time * 1000L));
//        System.out.print("日期格式---->" + times);
//        return times;
//    }

    static class ViewHolder {
        @BindView(R.id.tv_Year)
        TextView tvYear;
        @BindView(R.id.tv_Month)
        TextView tvMonth;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_upload)
        TextView tvUpLoad;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_zero)
        LinearLayout llZero;
        @BindView(R.id.ll_one)
        LinearLayout llOne;
        @BindView(R.id.ll_two)
        LinearLayout llTwo;
        @BindView(R.id.tv_uploadFile)
        TextView tvUpLoadFile;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.zbxn.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.entity.ReportListEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class FinanceExcelAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<ReportListEntity> mList;
    private String type = "";
    private ICustomListener listener;


    public FinanceExcelAdapter(Context mContext, List<ReportListEntity> mList, String type, ICustomListener listener) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mList = mList;
        this.type = type;
        this.listener = listener;
    }

    public void setData(List<ReportListEntity> list) {
        mList = list;
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return mList.get(groupPosition).getFormInfo() == null ? 0 : mList.get(groupPosition).getFormInfo().size();
    }


    @Override
    public List<ReportListEntity.FormInfoBean> getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return mList.get(groupPosition).getFormInfo();
    }

    @Override
    public ReportListEntity.FormInfoBean getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return mList.get(groupPosition).getFormInfo().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.expandexcel_group_item, null);
        }
        final ReportListEntity entity = mList.get(groupPosition);

        GroupViewHolder holder = new GroupViewHolder();
        holder.mExcelName = (TextView) convertView
                .findViewById(R.id.tv_excelName);
        holder.mMonth = (TextView) convertView.findViewById(R.id.tv_Month);
        holder.mYear = (TextView) convertView.findViewById(R.id.tv_Year);
        holder.mUpLoad = (TextView) convertView.findViewById(R.id.tv_upload);
        holder.mYear.setText(mList.get(groupPosition).getDate().substring(0, 4));
        holder.mMonth.setText(mList.get(groupPosition).getDate().substring(5, 7));
        switch (mList.get(groupPosition).getDate().substring(5, 7)) {
            case "01":
                holder.mExcelName.setText("一月份");
                break;
            case "02":
                holder.mExcelName.setText("二月份");
                break;
            case "03":
                holder.mExcelName.setText("三月份");
                break;
            case "04":
                holder.mExcelName.setText("四月份");
                break;
            case "05":
                holder.mExcelName.setText("五月份");
                break;
            case "06":
                holder.mExcelName.setText("六月份");
                break;
            case "07":
                holder.mExcelName.setText("七月份");
                break;
            case "08":
                holder.mExcelName.setText("八月份");
                break;
            case "09":
                holder.mExcelName.setText("九月份");
                break;
            case "10":
                holder.mExcelName.setText("十月份");
                break;
            case "11":
                holder.mExcelName.setText("十一月份");
                break;
            case "12":
                holder.mExcelName.setText("十二月份");
                break;
            default:
                holder.mExcelName.setText("财务报表");
                break;
        }
        if (type == "2") {
            holder.mUpLoad.setText("提醒");
        } else {
            holder.mUpLoad.setText("上传");
        }


        holder.mUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == "2") {
                    listener.onCustomListener(3, entity, groupPosition);//进行提醒
                } else {
                    listener.onCustomListener(1, entity, groupPosition);//进行上传附件
                }

            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.expandexcel_child_item, null);
        }

        final ReportListEntity.FormInfoBean entity = mList.get(groupPosition).getFormInfo().get(childPosition);

        ChildViewHolder holder = new ChildViewHolder();
        holder.mTitle = (TextView) convertView.findViewById(R.id.tv_title);
        holder.mDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
        holder.mTitle.setText(mList.get(groupPosition).getFormInfo().get(childPosition).getRealName());
        if (type == "2") {
            holder.mDelete.setVisibility(View.GONE);
        } else {
            holder.mDelete.setVisibility(View.VISIBLE);
        }
        if (mList.get(groupPosition).getFormInfo().size() <= 1) {
            holder.mDelete.setVisibility(View.GONE);
        }

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCustomListener(2, entity, groupPosition);//进行上传附件
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        /*很重要：实现ChildView点击事件，必须返回true*/
        return true;
    }

    private class GroupViewHolder {
        TextView mExcelName;
        TextView mMonth;
        TextView mUpLoad;
        TextView mYear;
    }

    private class ChildViewHolder {
        ImageView mDelete;
        TextView mTitle;
    }
}

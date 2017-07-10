package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.entity.CreditApplyEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: ysj
 * @date: 2017-03-13 16:02
 */
public class CreditApplyAdapter extends BaseAdapter {

    private Context mContext;
    private List<CreditApplyEntity> mList;
    private int type;

    public CreditApplyAdapter(Context mContext, List<CreditApplyEntity> mList, int type) {
        this.mContext = mContext;
        this.mList = mList;
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
            convertView = View.inflate(mContext, R.layout.list_item_credit_apply, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (type == 1) {//查看申请
            holder.layoutApplyCompany.setVisibility(View.GONE);
            holder.layoutApplyState.setVisibility(View.VISIBLE);
            holder.layoutApplyRemark.setVisibility(View.VISIBLE);
        } else if (type == 2) {//草稿箱
            holder.layoutApplyCompany.setVisibility(View.VISIBLE);
            holder.layoutApplyState.setVisibility(View.GONE);
            holder.layoutApplyRemark.setVisibility(View.GONE);
        }
        CreditApplyEntity entity = mList.get(position);
        holder.tvApplyFrom.setText(entity.getBankName());
        holder.tvApplyMoney.setText(entity.getLoanAmount() + "");
        holder.tvApplyState.setText(entity.getStautsName());
        holder.tvApplyRemark.setText(entity.getRejectMsg());
        holder.tvApplyTime.setText(entity.getApplyDate());
        holder.tvApplyCompany.setText(entity.getCompanyName());

        if (StringUtils.isEmpty(entity.getRejectMsg())) {//如果拒绝理由为空，隐藏
            holder.layoutApplyRemark.setVisibility(View.GONE);
        } else {
            holder.layoutApplyRemark.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_apply_from)
        TextView tvApplyFrom;
        @BindView(R.id.tv_apply_time)
        TextView tvApplyTime;
        @BindView(R.id.tv_apply_money)
        TextView tvApplyMoney;
        @BindView(R.id.tv_apply_state)
        TextView tvApplyState;
        @BindView(R.id.tv_apply_remark)
        TextView tvApplyRemark;
        @BindView(R.id.tv_apply_company)
        TextView tvApplyCompany;
        @BindView(R.id.layout_apply_remark)
        LinearLayout layoutApplyRemark;
        @BindView(R.id.layout_apply_company)
        LinearLayout layoutApplyCompany;
        @BindView(R.id.layout_apply_state)
        LinearLayout layoutApplyState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

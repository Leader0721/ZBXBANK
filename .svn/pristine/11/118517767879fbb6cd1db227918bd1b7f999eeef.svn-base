package com.zbxn.main.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.base.BaseApp;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.entity.PersonalMessageEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

//好友消息

/**
 * Created by U on 2017/3/20.
 */
public class DynamicMessageListAdapter extends BaseAdapter {
    private Context mCxontext;
    private List<PersonalMessageEntity> mList;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private String tagTime = "";
    private ICustomListener mListener = null;

    public DynamicMessageListAdapter(Context mCxontext, List<PersonalMessageEntity> mList, ICustomListener listener) {
        this.mCxontext = mCxontext;
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

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mCxontext, R.layout.list_item_dynamic_message, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tvFlag.setVisibility(View.GONE);
        holder.tvFlag1.setVisibility(View.VISIBLE);
        final PersonalMessageEntity entity = mList.get(position);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
//                .displayer(new RoundedBitmapDisplayer(90))  // 设置成圆角图片
                .build();                                   // 创建配置过得DisplayImageOption对象
        String headUrl = entity.getUser().getHeadImgUrl();
        ImageLoader.getInstance().displayImage(headUrl, holder.ivUserHead, options);
        holder.tvReadtag.setVisibility(View.GONE);
        holder.tvTime.setText(entity.getCreationDate());
        holder.tvFlag1.setVisibility(View.GONE);
        int type = entity.getType();
        String userName;
//        if () {
        if (type == 0 || type == 2) {
            //系统和状态
            userName = "<font color='#2799EF'>" + entity.getUser().getRealName() + "</font>";
            holder.tvTitle.setText(entity.getUser().getRealName());
            holder.tvTitle.setTextColor(mCxontext.getResources().getColor(R.color.message_blue));
            String value = entity.getMessage();
            holder.tvValue.setText(value);
        } else {
            //私信和好友请求
            userName = "<font color='#2799EF'>" + entity.getUser().getRealName() + "</font>";
            holder.tvTitle.setText(Html.fromHtml(userName));
            if (StringUtils.isEmpty(entity.getTitle())) {
                holder.tvValue.setText("请求添加好友");
            } else {
                holder.tvValue.setText(entity.getTitle());
            }
        }

        if (entity.isProcessed()) {
            holder.tvFlag1.setVisibility(View.VISIBLE);
            holder.tvFlag1.setTextColor(mCxontext.getResources().getColor(R.color.tvc9));
            holder.tvFlag1.setText("已处理");
            holder.tvFlag1.setEnabled(false);
        } else {
            if (type == 0 || type == 2) {
                holder.tvFlag1.setVisibility(View.GONE);
            } else {
                holder.tvFlag1.setVisibility(View.VISIBLE);
                holder.tvFlag1.setText("同意");
                holder.tvFlag1.setEnabled(true);
                holder.tvFlag1.setTextColor(BaseApp.getContext().getResources().getColor(R.color.white));

//                convertView.setClickable(false);
//                holder.tvFlag1.setClickable(true);
                holder.tvFlag1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onCustomListener(0, entity, position);
                    }
                });
            }
        }
        holder.tvTagTime.setVisibility(View.GONE);
        holder.tvTime.setText(entity.getCreationDateStr().substring(5, 16));
        return convertView;

    }


    static class ViewHolder {
        @BindView(R.id.tv_tag_time)
        TextView tvTagTime;
        @BindView(R.id.iv_userHead)
        CircleImageView ivUserHead;
        @BindView(R.id.tv_readtag)
        TextView tvReadtag;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.tv_flag)
        TextView tvFlag;
        @BindView(R.id.tv_agree)
        TextView tvFlag1;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.zbxn.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.base.BaseApp;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.entity.PersonalMessageEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by U on 2017/3/20.
 */
public class PersonalMessageListAdapter extends BaseAdapter {
    private Context mCxontext;
    private List<PersonalMessageEntity> mList;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private String tagTime = "";
    private boolean flag;

    public PersonalMessageListAdapter(Context mCxontext, List<PersonalMessageEntity> mList) {
        this.mCxontext = mCxontext;
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
            convertView = View.inflate(mCxontext, R.layout.list_item_dynamic_message, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final PersonalMessageEntity entity = mList.get(position);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
//                .displayer(new RoundedBitmapDisplayer(90))  // 设置成圆角图片
                .build();                                   // 创建配置过得DisplayImageOption对象
        String headUrl = entity.getHeadImgUrl();
        ImageLoader.getInstance().displayImage(headUrl, holder.ivUserHead, options);
//        if (entity.isIsRead()) {
//            holder.tvReadtag.setVisibility(View.GONE);
//        } else {
//            holder.tvReadtag.setVisibility(View.VISIBLE);
//        }
        holder.tvReadtag.setVisibility(View.GONE);
        holder.tvTime.setText(entity.getCreationDate());
        String userName;
        if (!entity.isProcessed()) {//未处理的消息
            switch (entity.getMessageType()) {
                case 0://系统消息
                case 16:
                case 18:
                case 20:
                case 22:
                case 24:
                case 26:
                case 28:
                case 30:
                case 31:
                    userName = "<font color='#2799EF'>" + entity.getUserName() + "</font>";
                    holder.tvTitle.setText(Html.fromHtml(userName));
                    holder.tvValue.setText(entity.getBody());
                    holder.tvFlag.setVisibility(View.GONE);
                    break;
                case 1://私信
                    userName = "<font color='#2799EF'>" + entity.getUserName() + "</font>";
                    holder.tvTitle.setText(Html.fromHtml(userName));
                    if (StringUtils.isEmpty(entity.getBody())) {
                        holder.tvValue.setText("发送了一条私信");
                    } else {
                        holder.tvValue.setText(entity.getBody());
                    }
                    holder.tvFlag.setVisibility(View.GONE);
                    break;
                case 3://好友邀请
                    userName = "<font color='#2799EF'>" + entity.getUserName() + "</font>";
                    holder.tvTitle.setText(Html.fromHtml(userName));
                    if (StringUtils.isEmpty(entity.getBody())) {
                        holder.tvValue.setText("请求添加你为好友");
                    } else {
                        holder.tvValue.setText(entity.getBody());
                    }
                    holder.tvFlag.setText("查看详情");
                    holder.tvFlag.setTextColor(BaseApp.getContext().getResources().getColor(R.color.message_blue));
                    holder.tvFlag.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                    break;
                case 4://公司邀请
                    userName = "<font color='#2799EF'>" + entity.getUserName() + "</font>";
                    holder.tvTitle.setText(Html.fromHtml(userName));
                    String value = "邀请您加入" + "<font color='#2799EF'>" + entity.getCompanyName() + "</font>";
                    holder.tvValue.setText(Html.fromHtml(value));
                    holder.tvFlag.setText("查看详情");
                    holder.tvFlag.setTextColor(BaseApp.getContext().getResources().getColor(R.color.message_blue));
                    holder.tvFlag.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                    break;
                default:
                    break;
            }
        } else {//已处理的消息
            userName = "<font color='#2799EF'>" + entity.getUserName() + "</font>";
            holder.tvTitle.setText(Html.fromHtml(userName));

            if (entity.getMessageType() == 3) {//好友请求
                if (StringUtils.isEmpty(entity.getBody())) {
                    holder.tvValue.setText("好友申请已处理");
                } else {
                    holder.tvValue.setText(entity.getBody());
                }
            } else if (entity.getMessageType() == 1) {//私信
                if (StringUtils.isEmpty(entity.getBody())) {
                    holder.tvValue.setText("发送了一条私信");
                } else {
                    holder.tvValue.setText(entity.getBody());
                }
            } else {
                holder.tvValue.setText(entity.getBody());
            }

            if (entity.getMessageType() == 0 || entity.getMessageType() == 16 || entity.getMessageType() == 18 || entity.getMessageType() == 20 || entity.getMessageType() == 22
                    || entity.getMessageType() == 24 || entity.getMessageType() == 26 || entity.getMessageType() == 28 || entity.getMessageType() == 30 || entity.getMessageType() == 31) {
                holder.tvFlag.setVisibility(View.GONE);
            } else {
                holder.tvFlag.setText("已处理");
                holder.tvFlag.setTextColor(mCxontext.getResources().getColor(R.color.tvc9));
            }
        }


        holder.tvTagTime.setVisibility(View.GONE);
//        holder.tvTime.setText(DateUtils.fromTodayDay(DateUtils.toDate(entity.getCreationDateStr())));
        holder.tvTime.setText(entity.getCreationDateStr().substring(5, 16));
        return convertView;

    }


    static class ViewHolder {
        @BindView(R.id.tv_tag_time)
        TextView tvTagTime;
        @BindView(R.id.iv_userHead)
        ImageView ivUserHead;
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

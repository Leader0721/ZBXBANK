package com.zbxn.main.activity.dynamic;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.utils.DateUtils;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.entity.DynamicMessageEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author: ysj
 * @date: 2017-03-25 20:29
 */
public class CommentMessageAdapter extends BaseAdapter {

    private Context mContext;
    private List<DynamicMessageEntity> mList;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CommentMessageAdapter(Context mContext, List<DynamicMessageEntity> mList) {
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
            convertView = View.inflate(mContext, R.layout.list_item_comment, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DynamicMessageEntity entity = mList.get(position);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .build();                                   // 创建配置过得DisplayImageOption对象
//        String mBaseUrl = ConfigUtils.getConfig(ConfigUtils.KEY.webServer);
        String headUrl = entity.getUser().getHeadImgUrl();
        ImageLoader.getInstance().displayImage(headUrl, holder.imageViewPortrait, options);
        holder.commentsMemberid.setText(entity.getUser().getRealName());
        if (mList.get(position).getCreationDateStr() != null) {
            holder.commentsCreatetime.setText(DateUtils.fromTodaySimple(StringUtils.convertToDate(sdf, mList.get(position).getCreationDateStr())));
        }

        for (int i = 0; i < mList.size(); i++) {
            if (entity.getPid() == mList.get(i).getId()) {
                String content = "@" + mList.get(i).getUser().getRealName() + ":" + entity.getMessage();
                SpannableString spanText = new SpannableString(content);
                spanText.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.orange)), 0, mList.get(i).getUser().getRealName().length() + 2,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                holder.commentsContent.append(spanText);
            } else {
                holder.commentsContent.setText(entity.getMessage());
            }
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imageView_portrait)
        CircleImageView imageViewPortrait;
        @BindView(R.id.comments_createtime)
        TextView commentsCreatetime;
        @BindView(R.id.img_comment)
        ImageView imgComment;
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        @BindView(R.id.comments_memberid)
        TextView commentsMemberid;
        @BindView(R.id.comment_reply)
        TextView commentReply;
        @BindView(R.id.comments_content)
        TextView commentsContent;
        @BindView(R.id.comment_layout)
        LinearLayout commentLayout;
        @BindView(R.id.layout)
        LinearLayout layout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

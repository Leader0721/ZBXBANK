package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.entity.CompanyEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称：申请列表的adapter
 * 创建人：Wuzy
 * 创建时间：2016/10/10 10:05
 */
public class ContactsCompanyAdapter extends BaseAdapter {

    private Context mCxontext;
    private List<CompanyEntity> mList;
    private ICustomListener listener;

    public ContactsCompanyAdapter(Context mContext, List<CompanyEntity> mList, ICustomListener listener) {
        this.mCxontext = mContext;
        this.mList = mList;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mCxontext, R.layout.list_item_contacts_company, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CompanyEntity entity = mList.get(position);

        //姓名
        holder.mRemarkName.setText(entity.getCompanyName() + "");
        //获取到的数据显示上去
        if (!StringUtils.isEmpty(entity.getLogoUrl())){
            holder.civPortrait.setVisibility(View.VISIBLE);
            holder.tvHead.setVisibility(View.GONE);
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                    //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                    .build();                                   // 创建配置过得DisplayImageOption对象
            ImageLoader.getInstance().displayImage(entity.getLogoUrl(), holder.civPortrait, options);
        }else {
            if (entity.getCompanyName().length() >= 1 && !StringUtils.isEmpty(entity.getCompanyName())) {
                holder.tvHead.setText(entity.getCompanyName().substring(0, 1));
                holder.tvHead.setVisibility(View.VISIBLE);
                holder.civPortrait.setVisibility(View.GONE);
            }

        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_head)
        TextView tvHead;
        @BindView(R.id.civ_Portrait)
        CircleImageView civPortrait;
        @BindView(R.id.mRemarkName)
        TextView mRemarkName;
        @BindView(R.id.mImage)
        ImageView mImage;
        @BindView(R.id.mLayout)
        LinearLayout mLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.entity.CompanyEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by U on 2017/3/9.
 */

public class MemberCenterCompanyAdapter extends BaseAdapter {

    private Context mCxontext;
    private List<CompanyEntity> mList;

    public MemberCenterCompanyAdapter(Context mCxontext, List<CompanyEntity> mList) {
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
            convertView = View.inflate(mCxontext, R.layout.list_item_membercenter_company1, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CompanyEntity entity = mList.get(position);

        //获取到的数据显示上去
        if (!StringUtils.isEmpty(entity.getLogoUrl())){
            holder.civPortrait.setVisibility(View.VISIBLE);
            holder.mPortrait.setVisibility(View.GONE);
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
            holder.mPortrait.setVisibility(View.VISIBLE);
            holder.civPortrait.setVisibility(View.GONE);
            if (entity.getCompanyName().length() >= 1 && !StringUtils.isEmpty(entity.getCompanyName())){
                holder.mPortrait.setText(entity.getCompanyName().substring(0, 1));
            }
        }
//        holder.tvCount.setText(mList.get(position).getCount());
        holder.mCompanyName.setText(entity.getCompanyName());
        holder.mCount.setText(entity.getUserCount() + "");
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.mPortrait)
        TextView mPortrait;
        @BindView(R.id.civ_Portrait)
        CircleImageView civPortrait;
        @BindView(R.id.mCompanyName)
        TextView mCompanyName;
        @BindView(R.id.mCount)
        TextView mCount;
        @BindView(R.id.mLayout)
        RelativeLayout mLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

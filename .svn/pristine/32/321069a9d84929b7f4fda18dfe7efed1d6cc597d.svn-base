package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.utils.ConfigUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.entity.ContactsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/3/21.
 */
public class InformerAdapter extends BaseAdapter {
    private List<ContactsEntity> mList;
    private Context mContext;
    private ICustomListener listener;//

    public InformerAdapter(List<ContactsEntity> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.grid_item_inform, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactsEntity entity = mList.get(position);

        holder.name.setText(entity.getUserName());
        if (entity.getId() != -1) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showStubImage(R.mipmap.userhead_img)          // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.userhead_img)  // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.userhead_img)       // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                    //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                    .build();                                   // 创建配置过得DisplayImageOption对象
            String server = ConfigUtils.getConfig(ConfigUtils.KEY.webServer);
//            if (entity.getPortrait().contains("http")) {
            ImageLoader.getInstance().displayImage(server + entity.getHeadImgUrl(), holder.icon, options);
//            } else {
//                ImageLoader.getInstance().displayImage("file:///" + entity.getPortrait(), holder.icon, options);
//            }
        } else {
            holder.name.setText("");
            holder.delete.setVisibility(View.GONE);
            holder.icon.setImageResource(R.mipmap.add_circle);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mList.size() == 2||mList.size() == 1||mList.size() == 0){
//                    MyToast.showToast("至少选择一个通知人");
//                }else {
                    mList.remove(position);
                    notifyDataSetChanged();
//                }
            }
        });
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.iv_icon)
        CircleImageView icon;
        @BindView(R.id.tt_name)
        TextView name;
        @BindView(R.id.iv_delete)
        ImageView delete;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

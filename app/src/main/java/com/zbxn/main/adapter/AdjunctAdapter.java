package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.utils.StringUtils;
import com.pub.widget.FilletImageView;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.entity.AdjunctEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: ysj
 * @date: 2017-03-16 15:45
 */
public class AdjunctAdapter extends BaseAdapter {

    private Context mContext;
    private List<AdjunctEntity> mList;
    private ICustomListener listener;
    private int stateId;

    private boolean isDetail;
    private boolean isInit;
    private int mPosition;

    public AdjunctAdapter(Context mContext, List<AdjunctEntity> mList, ICustomListener listener, int stateId) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
        this.stateId = stateId;
        this.isInit = true;
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
            convertView = View.inflate(mContext, R.layout.grid_item_adjunct, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AdjunctEntity entity = mList.get(position);
        holder.imgContent.setOnClickListener(new View.OnClickListener() {//图片主体
            @Override
            public void onClick(View v) {
                listener.onCustomListener(0, isDetail, position);
            }
        });
        holder.imgHead.setOnClickListener(new View.OnClickListener() {//右上角图片重选
            @Override
            public void onClick(View v) {
                listener.onCustomListener(1, null, position);
            }
        });
        if (!StringUtils.isEmpty(entity.getFileList())) {
            if (entity.getFileList().size() > 1) {
                holder.tvNum.setVisibility(View.VISIBLE);
                holder.tvNum.setText(entity.getFileList().size() + "");
            } else {
                holder.tvNum.setVisibility(View.GONE);
            }
        } else {
            holder.tvNum.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(entity.getFileType())) {
            switch (entity.getFileType()) {
                case "png":
                case "jpg":
                case "jpeg":
                    if (!StringUtils.isEmpty(entity.getFileList())) {//设置图片
                        DisplayImageOptions options = new DisplayImageOptions.Builder()
                                .showStubImage(R.mipmap.attachment_opens)          // 设置图片下载期间显示的图片
                                .showImageForEmptyUri(R.mipmap.attachment_opens)  // 设置图片Uri为空或是错误的时候显示的图片
                                .showImageOnFail(R.mipmap.attachment_opens)       // 设置图片加载或解码过程中发生错误显示的图片
                                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                                .build();                                   // 创建配置过得DisplayImageOption对象
                        ImageLoader.getInstance().displayImage(entity.getFileList().get(entity.getFileList().size() - 1).getFileUrl(), holder.imgContent, options);
                    } else {
                        holder.imgContent.setImageResource(R.mipmap.attachment_opens);
                    }
                    break;
                case "doc":
                case "docx":
                    holder.imgContent.setImageResource(R.mipmap.attachment_doc);
                    break;
                case "pdf":
                    holder.imgContent.setImageResource(R.mipmap.attachment_pdf);
                    break;
                case "xlsx":
                case "xls":
                    holder.imgContent.setImageResource(R.mipmap.attachment_xel);
                    break;
                default:
                    holder.imgContent.setImageResource(R.mipmap.attachment_other);
                    break;
            }
        } else {
            holder.imgContent.setImageResource(R.mipmap.attachment_opens);
        }

        if (entity.isMust()) {//是否必需(填)
            holder.imgType.setVisibility(View.VISIBLE);
        } else {
            holder.imgType.setVisibility(View.GONE);
        }

        //设置文件名
        holder.tvName.setText(entity.getFileName());

        if (isDetail) {
            holder.imgType.setVisibility(View.GONE);
        }

        if (stateId == 2 || stateId == 1) {//申请单已通过审核
            holder.imgHead.setVisibility(View.GONE);
        } else {
            if (mList.get(position).isUpload()) {
                holder.imgHead.setVisibility(View.VISIBLE);
            } else {
                holder.imgHead.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    /**
     * 局部刷新
     *
     * @param gridView
     * @param position
     * @param isOne
     */
    public void notifyDataSetChanged(GridView gridView, int position, boolean isOne) {
        mPosition = position;
        int firstPosition = gridView.getFirstVisiblePosition();
        int lastPosition = gridView.getLastVisiblePosition();
        if (isOne) {
            isInit = false;
            if (position >= firstPosition && position <= lastPosition) {
                View view = gridView.getChildAt(position - firstPosition);
                getView(position, view, gridView);
            }
        } else {
            notifyDataSetChanged();
        }
    }


    static class ViewHolder {
        @BindView(R.id.img_content)
        FilletImageView imgContent;
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.img_type)
        TextView imgType;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_num)
        TextView tvNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    public void setIsDetail(boolean isDetail) {
        this.isDetail = isDetail;
    }
}

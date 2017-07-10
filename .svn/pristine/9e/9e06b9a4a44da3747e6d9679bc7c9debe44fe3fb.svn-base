package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.utils.StringUtils;
import com.pub.widget.FilletImageView;
import com.zbxn.R;
import com.zbxn.main.entity.AttachmentGetEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/15.
 */
public class EnterpriseInfoAdapter extends BaseAdapter {
    private Context mContext;
    private List<AttachmentGetEntity> mList;

    public EnterpriseInfoAdapter(Context context, List<AttachmentGetEntity> list) {
        mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
//        return 40;
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
            convertView = View.inflate(mContext, R.layout.gridview_item_enterprise, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mList.get(position).getAttachment().size() != 0) {
            if (mList.get(position).getAttachment().size() > 1) {
                holder.tvNum.setVisibility(View.VISIBLE);
                holder.tvNum.setText(mList.get(position).getAttachment().size() + "");
            } else {
                holder.tvNum.setVisibility(View.GONE);
            }
        } else {
            holder.tvNum.setVisibility(View.GONE);
        }


        switch (position) {
            case 0:
                holder.title.setText("营业执照");
                break;
            case 1:
                holder.title.setText("开户许可证");
                break;
            case 2:
                holder.title.setText("税务登记证");
                break;
            case 3:
                holder.title.setText("法人代表身份证");
                break;
            case 4:
                holder.title.setText("财务负责人身份证");
                break;
            case 5:
                holder.title.setText("股东身份证");
                break;
//            case 6:
//                holder.title.setText("财务报表");
//                break;
            case 6:
                holder.title.setText("企业章程");
                break;
            case 7:
                holder.title.setText("验资证明");
                break;
            case 8:
                holder.title.setText("土地租赁协议/房产证明");
                break;
            case 9:
                holder.title.setText("企业变更");
                break;
            case 10:
                holder.title.setText("项目可行性研究报告");
                break;
            case 11:
                holder.title.setText("相关部门许可证明");
                break;
            case 12:
                holder.title.setText("组织机构代码证");
                break;
            case 13:
                holder.title.setText("机构信用代码证");
                break;
        }
        if (mList.get(position).getAttachment().size() != 0 || mList.get(position).getAttachment() == null) {
            if (!StringUtils.isEmpty(mList.get(position).getAttachment().get(0).getFileType())) {
                switch (mList.get(position).getAttachment().get(0).getFileType()) {
                    case ".png":
                    case ".jpg":
                    case ".jpeg":
                        if (!StringUtils.isEmpty(mList.get(position).getAttachment())) {//设置图片
                            DisplayImageOptions options = new DisplayImageOptions.Builder()
                                    .showStubImage(R.mipmap.attachment_no)          // 设置图片下载期间显示的图片
                                    .showImageForEmptyUri(R.mipmap.attachment_no)  // 设置图片Uri为空或是错误的时候显示的图片
                                    .showImageOnFail(R.mipmap.picdown_fail)       // 设置图片加载或解码过程中发生错误显示的图片
                                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                                    .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                                    .build();                                   // 创建配置过得DisplayImageOption对象
                            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            ImageLoader.getInstance().displayImage(mList.get(position).getAttachment().get(0).getFilePath(), holder.imageView, options);
                        } else {
                            holder.imageView.setImageResource(R.mipmap.attachment_no);
                        }
                        break;
                    case ".doc":
                    case ".docx":
                        holder.imageView.setImageResource(R.mipmap.attachment_doc);
                        break;
                    case ".pdf":
                        holder.imageView.setImageResource(R.mipmap.attachment_pdf);
                        break;
                    case ".xlsx":
                    case ".xls":
                        holder.imageView.setImageResource(R.mipmap.attachment_xel);
                        break;
                    default:
                        holder.imageView.setImageResource(R.mipmap.attachment_other);
                        break;
                }
            } else {
                holder.imageView.setImageResource(R.mipmap.attachment_opens);
            }
        }else {
            holder.imageView.setImageResource(R.mipmap.attachment_no);
        }
//        if (mList.get(position).getAttachment().size() != 0 || mList.get(position).getAttachment() == null) {
//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .showStubImage(R.mipmap.attachment_no)          // 设置图片下载期间显示的图片
//                    .showImageForEmptyUri(R.mipmap.attachment_no)  // 设置图片Uri为空或是错误的时候显示的图片
//                    .showImageOnFail(R.mipmap.picdown_fail)       // 设置图片加载或解码过程中发生错误显示的图片
//                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
//                    .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
//                    //.displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
//                    .build();                                   // 创建配置过得DisplayImageOption对象
//            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            ImageLoader.getInstance().displayImage(mList.get(position).getAttachment().get(0).getFilePath(), holder.imageView, options);
//        } else {
//            holder.imageView.setImageResource(R.mipmap.attachment_no);
//        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_enterpriseIcon)
        FilletImageView imageView;
        @BindView(R.id.tv_imageTitle)
        TextView title;
        @BindView(R.id.tv_num)
        TextView tvNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

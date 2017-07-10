package com.zbxn.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pub.utils.StringUtils;
import com.zbxn.R;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.entity.ContactsInviteEntity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/26.
 */

public class ContactsInviteAdapter extends BaseAdapter {

    private Context mContext;
    private List<ContactsInviteEntity> mList;
    private ICustomListener mListener;
    private HashMap<String, Integer> mAlphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] mSections;// 存放存在的汉语拼音首字母

    private boolean mIsVisWord = true;

    public ContactsInviteAdapter(Context mContext, List<ContactsInviteEntity> mList, ICustomListener listener,
                                 final HashMap<String, Integer> alphaIndexer, final String[] sections, boolean isVisWord) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListener = listener;
        this.mIsVisWord = isVisWord;
        mAlphaIndexer = alphaIndexer;
        mSections = sections;
        try {
            for (int i = 0; i < mList.size(); i++) {
                // 当前汉语拼音首字母
                // getAlpha(list.get(i));
                String currentStr = mList.get(i).getCaptialCharStr();
                // 上一个汉语拼音首字母，如果不存在为“ ”
                String previewStr = (i - 1) >= 0 ? mList.get(i - 1).getCaptialCharStr() : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = mList.get(i).getCaptialCharStr();
                    mAlphaIndexer.put(name, i);
                    mSections[i] = name;
                }
            }
        } catch (Exception e) {

        }
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_item_invitepeople, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ContactsInviteEntity entity = mList.get(position);


        String currentStr = entity.getCaptialCharStr();
        String previewStr = (position - 1) >= 0 ? mList.get(position - 1).getCaptialCharStr() : " ";
//        try {
//            if (!previewStr.equals(currentStr)) {
//                holder.mCaptialChar.setVisibility(View.VISIBLE);
//                holder.mCaptialChar.setText(currentStr);
//            } else {
//                holder.mCaptialChar.setVisibility(View.GONE);
//            }
//
//            if (!mIsVisWord) {
//                holder.mCaptialChar.setVisibility(View.GONE);
//            }
//        } catch (Exception e) {
//
//        }


        if (!StringUtils.isEmpty(entity.getContactname())) {
            holder.name.setText(entity.getContactname() + "");
            if (entity.getContactname().length() >= 3) {
                holder.icon.setText(entity.getContactname().substring(entity.getContactname().length()-2, entity.getContactname().length()));
            }else if (entity.getContactname().length() == 2){
                holder.icon.setText(entity.getContactname());
            }else if (entity.getContactname().length() == 1){
                holder.icon.setText(entity.getContactname());
            }
        }
        if (!StringUtils.isEmpty(entity.getContactnumber())){
            holder.phone.setText(entity.getContactnumber());
        }

//        if (mList.get(position).isInviteOrNot()){
//            holder.invite.setText("已邀请");
//        }else {
//            holder.invite.setText("邀请");
//        }

        holder.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCustomListener(0, entity, position);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.tv_phone)
        TextView phone;
        @BindView(R.id.tv_invite)
        TextView invite;
        @BindView(R.id.tv_icon)
        TextView icon;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

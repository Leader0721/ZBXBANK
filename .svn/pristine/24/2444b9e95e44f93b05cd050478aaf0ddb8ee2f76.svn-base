package com.zbxn.main.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pub.common.EventCustom;
import com.pub.common.EventKey;
import com.zbxn.R;
import com.zbxn.main.activity.contacts.ContactsChoseActivity;
import com.zbxn.main.activity.listener.ICustomListener;
import com.zbxn.main.entity.ContactsEntity;
import com.zbxn.main.entity.ContactsFriendEntity;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class ContactsFriendAdapter extends BaseAdapter {

    private Activity mContext;
    private List<ContactsFriendEntity> mList;
    private ICustomListener mListener;
    private int mType;

    private HashMap<String, Integer> mAlphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] mSections;// 存放存在的汉语拼音首字母

    private boolean mIsVisWord = true;

    public ContactsFriendAdapter(Activity mContext, List<ContactsFriendEntity> mList, ICustomListener listener
            , final HashMap<String, Integer> alphaIndexer, final String[] sections, boolean isVisWord, int type) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListener = listener;
        this.mIsVisWord = isVisWord;
        this.mType = type;

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

        ViewHolderDepartment holderDepartment = null;
        ViewHolderGroup holderGroup = null;
        ViewHolderPeople holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_item_contacts_people, null);
            holder = new ViewHolderPeople(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolderPeople) convertView.getTag();
        }

        final ContactsFriendEntity entity = mList.get(position);

            String currentStr = entity.getCaptialCharStr();
            String previewStr = (position - 1) >= 0 ? mList.get(position - 1).getCaptialCharStr() : " ";
            try {
                if (!previewStr.equals(currentStr)) {
                    holder.mCaptialChar.setVisibility(View.VISIBLE);
                    holder.mCaptialChar.setText(currentStr);
                } else {
                    holder.mCaptialChar.setVisibility(View.GONE);
                }

                if (!mIsVisWord) {
                    holder.mCaptialChar.setVisibility(View.GONE);
                }
            } catch (Exception e) {

            }
            //可选人
            if (mType == 1 || mType == 2) {
                holder.mCheck.setVisibility(View.VISIBLE);
                if (entity.isSelected()) {
                    holder.mCheck.setSelected(true);
                } else {
                    holder.mCheck.setSelected(false);
                }

                holder.mLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mType == 2) {
                            changeStateSingle(entity);
                            if (!mIsVisWord) {
                                mContext.finish();
                            }
                            //通知更新选择人
                            EventCustom eventCustom = new EventCustom();
                            eventCustom.setTag(EventKey.UPDATECONTACTSSELECTSINGLE);
                            eventCustom.setObj("");
                            EventBus.getDefault().post(eventCustom);
                        } else if (mType == 1) {
                            changeState(entity);
                            //通知更新选择人
                            EventCustom eventCustom = new EventCustom();
                            eventCustom.setTag(EventKey.UPDATECONTACTSSELECT);
                            eventCustom.setObj("");
                            EventBus.getDefault().post(eventCustom);
                        }
//                        mListener.onCustomListener(0, entity, position);
                    }
                });
            }

            //姓名
            holder.mRemarkName.setText(entity.getRealName() + "");
//            String mBaseUrl = ConfigUtils.getConfig(ConfigUtils.KEY.webServer);
            ImageLoader.getInstance().displayImage(entity.getHeadImgUrl(), holder.mPortrait);

        return convertView;
    }

    class ViewHolderPeople {
        @BindView(R.id.mLayout)
        LinearLayout mLayout;
        @BindView(R.id.mCaptialChar)
        TextView mCaptialChar;
        @BindView(R.id.mCheck)
        ImageView mCheck;
        @BindView(R.id.mPortrait)
        CircleImageView mPortrait;
        @BindView(R.id.mRemarkName)
        TextView mRemarkName;

        ViewHolderPeople(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderDepartment {
        @BindView(R.id.mPortrait)
        CircleImageView mPortrait;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolderDepartment(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderGroup {
        @BindView(R.id.mPortrait)
        CircleImageView mPortrait;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolderGroup(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private static ContactsFriendEntity lastItemInfo = null;

    // 修改选中的状态
    public void changeState(ContactsFriendEntity itemInfo) { // 多选时
        ContactsEntity entity = new ContactsEntity();
        entity.setUserId(itemInfo.getUserId());
        entity.setUserName(itemInfo.getRealName());
        entity.setHeadImgUrl(itemInfo.getHeadImgUrl());
        entity.setDepartmentName("");
        // 单选时
        /*if (lastItemInfo != null) {
            lastItemInfo.setSelected(false);// 取消上一次的选中状态
        }*/
        itemInfo.setSelected(!itemInfo.isSelected());// 直接取反即可
        //lastItemInfo = itemInfo; // 记录本次选中的位置
        notifyDataSetChanged(); // 通知适配器进行更新
        if (itemInfo.isSelected()) {
            ContactsChoseActivity.mHashMap.put(itemInfo.getUserId() + "", entity);
        } else {
            ContactsChoseActivity.mHashMap.remove(itemInfo.getUserId() + "");
        }
    }

    // 修改选中的状态
    public void changeStateSingle(ContactsFriendEntity itemInfo) { // 多选时
        ContactsEntity entity = new ContactsEntity();
        entity.setUserId(itemInfo.getUserId());
        entity.setUserName(itemInfo.getRealName());
        entity.setHeadImgUrl(itemInfo.getHeadImgUrl());
        entity.setDepartmentName("");
        // 单选时
        itemInfo.setSelected(!itemInfo.isSelected());// 直接取反即可
        notifyDataSetChanged(); // 通知适配器进行更新
        ContactsChoseActivity.mHashMap.clear();
        ContactsChoseActivity.mHashMap.put(itemInfo.getUserId() + "", entity);
    }
}

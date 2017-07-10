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

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称：申请列表的adapter
 * 创建人：Wuzy
 * 创建时间：2016/10/10 10:05
 */
public class ContactsPeopleAdapter extends BaseAdapter {

    private Activity mContext;
    private List<ContactsEntity> mList;
    private ICustomListener mListener;
    private int mType;

    private HashMap<String, Integer> mAlphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] mSections;// 存放存在的汉语拼音首字母

    //定义两个int常量标记不同的Item视图
    public static final int FIRST_ITEM_DEPARTMENT = 0;
    public static final int FIRST_ITEM_GROUP = 1;
    public static final int WORD_ITEM = 2;

    private boolean mIsVisWord = true;

    public ContactsPeopleAdapter(Activity mContext, List<ContactsEntity> mList, ICustomListener listener
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
                String currentStr = mList.get(i).getCaptialChar();
                // 上一个汉语拼音首字母，如果不存在为“ ”
                String previewStr = (i - 1) >= 0 ? mList.get(i - 1).getCaptialChar() : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = mList.get(i).getCaptialChar();
                    mAlphaIndexer.put(name, i);
                    mSections[i] = name;
                }
            }
        } catch (Exception e) {

        }
    }

    //返回当前布局的样式type
    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getId() == -1000) {
            return FIRST_ITEM_DEPARTMENT;
        } else if (mList.get(position).getId() == -2000) {
            return FIRST_ITEM_GROUP;
        } else {
            return WORD_ITEM;
        }
    }

    //返回你有多少个不同的布局
    @Override
    public int getViewTypeCount() {
        return 3;
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
            if (getItemViewType(position) == FIRST_ITEM_DEPARTMENT) {
                convertView = View.inflate(mContext, R.layout.list_item_contacts_people_group, null);
                holderDepartment = new ViewHolderDepartment(convertView);
                convertView.setTag(holderDepartment);
            } else if (getItemViewType(position) == FIRST_ITEM_GROUP) {
                convertView = View.inflate(mContext, R.layout.list_item_contacts_people_group, null);
                holderGroup = new ViewHolderGroup(convertView);
                convertView.setTag(holderGroup);
            } else {
                convertView = View.inflate(mContext, R.layout.list_item_contacts_people, null);
                holder = new ViewHolderPeople(convertView);
                convertView.setTag(holder);
            }
        } else {
            if (getItemViewType(position) == FIRST_ITEM_DEPARTMENT) {
                holderDepartment = (ViewHolderDepartment) convertView.getTag();
            } else if (getItemViewType(position) == FIRST_ITEM_GROUP) {
                holderGroup = (ViewHolderGroup) convertView.getTag();
            } else {
                holder = (ViewHolderPeople) convertView.getTag();
            }
        }

        final ContactsEntity entity = mList.get(position);

        if (getItemViewType(position) == WORD_ITEM) {
            String currentStr = entity.getCaptialChar();
            String previewStr = (position - 1) >= 0 ? mList.get(position - 1).getCaptialChar() : " ";
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
            holder.mRemarkName.setText(entity.getUserName() + "");
//            String mBaseUrl = ConfigUtils.getConfig(ConfigUtils.KEY.webServer);
            ImageLoader.getInstance().displayImage(entity.getHeadImgUrl(), holder.mPortrait);
        } else if (getItemViewType(position) == FIRST_ITEM_DEPARTMENT) {
            holderDepartment.mPortrait.setImageResource(R.mipmap.address_department);
            holderDepartment.tvName.setText("按部门");
        } else if (getItemViewType(position) == FIRST_ITEM_GROUP) {
            holderGroup.mPortrait.setImageResource(R.mipmap.address_cohort);
            holderGroup.tvName.setText("按群组");
        }

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

    private static ContactsEntity lastItemInfo = null;

    // 修改选中的状态
    public void changeState(ContactsEntity itemInfo) { // 多选时
        // 单选时
        /*if (lastItemInfo != null) {
            lastItemInfo.setSelected(false);// 取消上一次的选中状态
        }*/
        itemInfo.setSelected(!itemInfo.isSelected());// 直接取反即可
        //lastItemInfo = itemInfo; // 记录本次选中的位置
        notifyDataSetChanged(); // 通知适配器进行更新
        if (itemInfo.isSelected()) {
            ContactsChoseActivity.mHashMap.put(itemInfo.getUserId(), itemInfo);
        } else {
            ContactsChoseActivity.mHashMap.remove(itemInfo.getUserId());
        }
    }

    // 修改选中的状态
    public void changeStateSingle(ContactsEntity itemInfo) { // 多选时
        // 单选时
        itemInfo.setSelected(!itemInfo.isSelected());// 直接取反即可
        notifyDataSetChanged(); // 通知适配器进行更新
        ContactsChoseActivity.mHashMap.clear();
        ContactsChoseActivity.mHashMap.put(itemInfo.getUserId(), itemInfo);
    }
}

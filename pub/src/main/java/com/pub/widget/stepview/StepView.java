package com.pub.widget.stepview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pub.R;
import com.pub.widget.stepview.bean.StepBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ysj
 * @date: 2017-03-15 11:04
 */
public class StepView extends LinearLayout {

    private Context mContext;
    private List<StepBean> mList = new ArrayList<>();
    private List<Boolean> isSelectList = new ArrayList<>();

    private OnStepViewItemClickListener mListener;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void init() {
        setOrientation(HORIZONTAL);
        if (isSelectList != null) {
            isSelectList.clear();
        }
        for (int i = 0; i < mList.size(); i++) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.stepview_item, null);
            RelativeLayout mLayout;
            View mViewLeft;
            View mViewRight;
            mLayout = (RelativeLayout) itemView.findViewById(R.id.layout);
            TextView mText = (TextView) itemView.findViewById(R.id.tv_stepview);
            ImageView mImg = (ImageView) itemView.findViewById(R.id.img_stepview);
            mViewLeft = itemView.findViewById(R.id.view_stepview_left);
            mViewRight = itemView.findViewById(R.id.view_stepview_right);
            final int finalI = i;
            mLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.itemClickListener(finalI);
                    }
                }
            });
            mText.setText(mList.get(i).getName());
            if (i == 0) {
                mViewLeft.setVisibility(INVISIBLE);
            } else if (i == mList.size() - 1) {
                mViewRight.setVisibility(INVISIBLE);
            } else {
            }
            LayoutParams imgParms = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            switch (mList.get(i).getState()) {
                case -1://未完成
                    mImg.setLayoutParams(imgParms);
                    break;
                case 0://进行中
                    imgParms.width = dpToPx(mContext, 30);
                    imgParms.height = dpToPx(mContext, 30);
                    mImg.setLayoutParams(imgParms);
                    isSelectList.add(false);
                    mImg.setImageResource(R.mipmap.stepview_circle);
                    break;
                case 1://已完成
                    imgParms.width = dpToPx(mContext, 40);
                    imgParms.height = dpToPx(mContext, 40);
                    mImg.setLayoutParams(imgParms);
                    isSelectList.add(true);
                    mImg.setImageResource(R.mipmap.stepview_select);
                    break;
            }
            addView(itemView);
        }
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LayoutParams parms = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < getCount(); i++) {//为每个item重新设置宽度
            parms.weight = 1;//宽高不好控，干脆按比例吧
            getChildAt(i).findViewById(R.id.layout).setLayoutParams(parms);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 是否选中
     *
     * @param num
     * @param isSelect
     */
    public void setSelect(int num, boolean isSelect) {
        if (num >= getCount()) {
            return;
        }
        for (int i = 0; i < getCount(); i++) {
            LayoutParams imgParms = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ImageView imageView = (ImageView) getChildAt(num).findViewById(R.id.img_stepview);
            if (isSelect) {//选中
                if (isSelect(num - 1)) {
                    imgParms.width = dpToPx(mContext, 40);
                    imgParms.height = dpToPx(mContext, 40);
                    imageView.setLayoutParams(imgParms);
                    imageView.setImageResource(R.mipmap.stepview_select);
                    isSelectList.set(num, true);
                } else {
                    Toast.makeText(mContext, "请先完成前一个流程", Toast.LENGTH_SHORT).show();
                }
            } else {//不选中
                imgParms.width = dpToPx(mContext, 30);
                imgParms.height = dpToPx(mContext, 30);
                imageView.setLayoutParams(imgParms);
                imageView.setImageResource(R.mipmap.stepview_circle);
                isSelectList.set(num, false);
            }
        }
        //因为item宽高改变，所以需要重新绘制
        //重要，防止视图错乱
        requestLayout();
    }

    /**
     * 当前item是否选中
     *
     * @param num
     * @return
     */
    public boolean isSelect(int num) {
        if (num >= getCount() || num < 0) {
            return true;
        }
        if (isSelectList != null) {
            return isSelectList.get(num);
        }
        return false;
    }

    /**
     * 获取item个数
     *
     * @return
     */
    private int getCount() {
        return mList.size();
    }

    /**
     * 获取数据
     */
    public List<StepBean> getmList() {
        return mList;
    }

    /**
     * 设置数据
     *
     * @param mList
     */
    public void setmList(List<StepBean> mList) {
        this.mList = mList;
        removeAllViews();
        init();
    }

    /**
     * 点击事件监听
     *
     * @param mListener
     */
    public void setItemClickListener(OnStepViewItemClickListener mListener) {
        this.mListener = mListener;

    }

    /**
     * 点击事件回调
     */
    public interface OnStepViewItemClickListener {
        void itemClickListener(int position);
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }
}

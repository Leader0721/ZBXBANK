package com.zbxn.main.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pub.base.BaseApp;
import com.pub.base.BaseFragment;
import com.pub.entity.UserEntity;
import com.zbxn.R;
import com.zbxn.main.activity.credit.CreditActivity;
import com.zbxn.main.activity.steward.StewardActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by U on 2017/3/8.
 */

public class MainFragmentManager implements View.OnClickListener {
    private MainActivity mMain;

    @BindView(R.id.layout_bottombar)
    ViewGroup mBottomBar;

    private ViewGroup[] mBottomContainer = null;
    /**
     * 不同模块的标题
     */
    private String[] mTitles = null;

    /**
     * Fragment集合
     */
    private BaseFragment[] mFragments = new BaseFragment[4];
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager mFragmentManager;

    /**
     * 当前页面索引位置
     */
    private int mCurrentIndex;

    @BindColor(R.color.main_tab_text_gray)
    int normalColor;

    @BindColor(R.color.main_tab_text_blue)
    int checkColor;

    public MainFragmentManager(MainActivity main) {
        this.mMain = main;
        ButterKnife.bind(this, main.getWindow().getDecorView());
        initView();
        setTabSelection(0, 0);
    }

    private void initView() {
        mFragmentManager = mMain.getSupportFragmentManager();

        List<ViewGroup> layoutList = new ArrayList<>();
        for (int i = 0; i < mBottomBar.getChildCount(); i++) {
            View v = mBottomBar.getChildAt(i);
            if (v instanceof ViewGroup)
                layoutList.add((ViewGroup) v);
        }
        mBottomContainer = new ViewGroup[layoutList.size()];
        layoutList.toArray(mBottomContainer);
        mTitles = new String[layoutList.size()];
        // 底部导航栏绑定点击事件
        for (int i = 0; i < mBottomContainer.length; i++) {
            ViewGroup vg = mBottomContainer[i];
            vg.setOnClickListener(this);
            // 每个模块的标题
            for (int j = 0; j < vg.getChildCount(); j++) {
                if (vg.getChildAt(j) instanceof TextView) {
                    TextView title = (TextView) vg.getChildAt(j);
                    mTitles[i] = title.getTag().toString();
                    break;
                }
            }
        }
    }

    private View mView;

    /**
     * 底部功能菜单点击
     */
    @Override
    public void onClick(View v) {
        int position = 0;
        mView = v;
        for (int i = 0; i < mBottomContainer.length; i++) {
            ViewGroup layout = mBottomContainer[i];
            ImageView imgTitle = (ImageView) layout.getChildAt(0);
            TextView tvTitle = (TextView) layout.getChildAt(1);
            int textColor = checkColor;
            int imageId = 0;
            imageId = getImageResource(i, false);
            if (v == layout) {
                position = i;
                imageId = getImageResource(i, true);
            } else {
                textColor = normalColor;
            }

            if (i != 1) {
                tvTitle.setTextColor(textColor);
                imgTitle.setImageResource(imageId);
            }
        }
        if (position != 1) {
            setTabSelection(position, 0);
        } else {
            showPopupWindow();
            ViewGroup layout = mBottomContainer[mCurrentIndex];
            ImageView imgTitle = (ImageView) layout.getChildAt(0);
            TextView tvTitle = (TextView) layout.getChildAt(1);
            int textColor = checkColor;
            int imageId = getImageResource(mCurrentIndex, true);
            tvTitle.setTextColor(textColor);
            imgTitle.setImageResource(imageId);
        }
    }

    /**
     * 获取图片资源ID
     *
     * @param position 位置
     * @param isSelect 是否选中
     * @return
     */
    private int getImageResource(int position, boolean isSelect) {
        switch (position) {
            case 0:
                return isSelect ? R.mipmap.bg_main_message_check
                        : R.mipmap.bg_main_message_uncheck;
            case 1:
                return isSelect ? R.mipmap.bg_main_tools_check
                        : R.mipmap.bg_main_tools_uncheck;
            case 2:
                return isSelect ? R.mipmap.bg_main_contact_check
                        : R.mipmap.bg_main_contact_uncheck;
            case 3:
                return isSelect ? R.mipmap.bg_main_membercenter_check
                        : R.mipmap.bg_main_membercenter_uncheck;
            default:
                return R.mipmap.ic_launcher;
        }
    }

    /**
     * 取得当前显示的Fragment
     */
    public BaseFragment getSelectedPage() {
        for (BaseFragment fragment : mFragments) {
            if (fragment == null)
                continue;
            if (!fragment.isHidden())
                return fragment;
        }
        return null;
    }

    /**
     * 取得Fragment
     */
    public BaseFragment getFragment(int index) {
        if (mFragments.length > index)
            return mFragments[index];
        return null;
    }

    /**
     * 获取当前页面的索引
     *
     * @return
     */
    public int getCurrentPageIndex() {
        return mCurrentIndex;
    }

    /**
     * 此方法专供刷新推送消息使用
     *
     * @param index
     */
    public void setRefresh(int index, int indexSub) {
        setTabSelection(index, indexSub);
        mBottomContainer[0].performClick();
        MainMessageFragment fragment = (MainMessageFragment) mFragments[0];
        fragment.setRefresh(indexSub);
    }


    /**
     * 设置当前显示的Fragment
     *
     * @param index 需要显示的Fragment的位置索引
     */
    public void setTabSelection(int index, int indexSub) {
        if (index >= mFragments.length)
            return;
        mCurrentIndex = index;
        // 开启一个Fragment事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        BaseFragment fragment = mFragments[index];
        if (fragment == null) {
            switch (index) {
                case 0:
                    fragment = new MainMessageFragment();
                    Bundle bundle0 = new Bundle();
                    bundle0.putInt("index", indexSub);
                    fragment.setArguments(bundle0);
                    break;
                case 1:
                    fragment = new MainToolsFragment();
                    break;
                case 2:
                    fragment = new MainContactsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 0);
                    fragment.setArguments(bundle);
                    break;
                case 3:
                    fragment = new MainPersonalFragment();
                    break;
            }
            mFragments[index] = fragment;
            if (fragment != null) {
                transaction.add(R.id.framlayout_content, fragment, fragment.mTitle);
            }
        } else {
            // 如果Fragment不为空，则直接将它显示出来
            transaction.show(fragment);
        }
        transaction.commit();
        // Fragment继承了状态监听
//        if (fragment instanceof IStatusCatcher)
//            ((IStatusCatcher) fragment).onFocused(fragment);

        changeTitle(mTitles[index]);
        changeToolbarStatus(index);
        mMain.invalidateOptionsMenu();
    }

    //显示应用选择
    private void showPopupWindow() {
        MainMessageFragment fragment = (MainMessageFragment) mFragments[0];
        fragment.setCancel();
        final View popView = ((LayoutInflater) BaseApp.getContext().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.popup_application, null, false);

        final LinearLayout llEntry = (LinearLayout) popView.findViewById(R.id.ll_entry);
        final RelativeLayout llLayout = (RelativeLayout) popView.findViewById(R.id.ll_layout);

        final PopupWindow pop = new PopupWindow(popView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        //添加进入动画
        llEntry.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animatorIn = ObjectAnimator.ofFloat(llEntry, "translationY", llEntry.getHeight() * 3, 0);
                animatorIn.setDuration(300);
                animatorIn.setInterpolator(new OvershootInterpolator());
                animatorIn.start();
            }
        });
        //使用PopupWindow的setAnimationStyle设置动画，会出现卡顿现象，所以直接给view设置动画
        llLayout.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animatorIn = ObjectAnimator.ofFloat(llLayout, "alpha", 0f, 1f);
                animatorIn.setDuration(200);
                animatorIn.start();
            }
        });
        //点击背景隐藏popupwindow
        popView.findViewById(R.id.ll_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCancelAnimator(llEntry, pop, llLayout);
            }
        });
        //点击取消按钮
        popView.findViewById(R.id.img_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCancelAnimator(llEntry, pop, llLayout);
            }
        });
        //贷款申请
        popView.findViewById(R.id.ll_credit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMain, CreditActivity.class);
                mMain.startActivity(intent);
                pop.dismiss();
            }
        });

        if (null != BaseApp.mUserEntity) {
            for (int i = 0; i < BaseApp.mUserEntity.getPermission().size(); i++) {
                UserEntity.PermissionBean permissionBean = BaseApp.mUserEntity.getPermission().get(i);
                for (int j = 0; j < permissionBean.getPermissoin().size(); j++) {
                    if ("03".equals(permissionBean.getPermissoin().get(j).getCode())) {
                        popView.findViewById(R.id.ll_housekeeper).setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }
        //信贷管理
        popView.findViewById(R.id.ll_housekeeper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (null == mUserEntity) {
//                    MyToast.showToast("暂未获取到权限");
//                    return;
//                }
//                for (int i = 0; i < mUserEntity.getPermission().size(); i++) {
//                    UserEntity.PermissionBean permissionBean = MainActivity.mUserEntity.getPermission().get(i);
//                    for (int j = 0; j < permissionBean.getPermissoin().size(); j++) {
//                        if ("03".equals(permissionBean.getPermissoin().get(j).getCode())) {
                Intent intent = new Intent(mMain, StewardActivity.class);
                mMain.startActivity(intent);
                pop.dismiss();
//                          return;
//                        }
//                    }
//                }
//                MyToast.showToast("暂无权限");
            }
        });

        pop.showAtLocation(mView, Gravity.NO_GRAVITY, 0, getStatusBarHeight());
    }

    //取消时动画
    public void setCancelAnimator(View view, final PopupWindow window, final View layout) {
        ObjectAnimator animatorOut = ObjectAnimator.ofFloat(view, "translationY", 0, view.getHeight() * 3);
        animatorOut.setInterpolator(new OvershootInterpolator());
        animatorOut.start();
        animatorOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束时在这做处理
                window.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ObjectAnimator animatorOutAlpha = ObjectAnimator.ofFloat(layout, "alpha", 1f, 0f);
        animatorOutAlpha.setDuration(300);
        animatorOutAlpha.start();
    }

    private int getStatusBarHeight() {
        return Math.round(25 * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 隐藏所有Fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        for (BaseFragment fragment : mFragments) {
            if (fragment == null)
                continue;
            transaction.hide(fragment);
        }
    }

    /**
     * 切换页面时，顶部标题随着改变
     *
     * @param title
     * @author GISirFive
     */
    private void changeTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            title = mMain.getResources().getString(R.string.app_name);
        }
//        mTitle.setText(title);
        mMain.setTitle(title);
    }

    /**
     * 改变Toolbar的状态
     *
     * @param position
     * @author GISirFive
     */
    public void changeToolbarStatus(int position) {
        /*if (position == 1 || position == 2) {
            mMain.getToolbarHelper().setShadowEnable(false);
        } else {
            mMain.getToolbarHelper().setShadowEnable(true);
        }*/
        if (position == 2) {
            mMain.getToolbarHelper().setShadowEnable(false);
            try {
                mMain.setRight1Show(true);
                mMain.setRight1("添加好友");
                mMain.setRight1Icon(R.mipmap.pub_contacts_add);
                mMain.setRight2Show(true);
                mMain.setRight2("搜索");
                mMain.setRight2Icon(R.mipmap.pub_contacts_search);
            } catch (Exception e) {

            }
        } else {
            mMain.getToolbarHelper().setShadowEnable(true);
            try {
                mMain.setRight1Show(false);
                mMain.setRight2Show(false);
            } catch (Exception e) {

            }
        }
    }
}

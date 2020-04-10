package cn.huanjv.home_module.view;


import androidx.fragment.app.Fragment;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.alibaba.android.arouter.launcher.ARouter;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.huanjv.base_module.adapter.FragmentAdapter;
import cn.huanjv.base_module.arouter.RouterPath;
import cn.huanjv.base_module.base.BaseActivity;
import cn.huanjv.base_module.util.NotificationUtil;
import cn.huanjv.base_module.util.StatusBarUtil;
import cn.huanjv.base_module.widget.ColorFlipPagerTitleView;
import cn.huanjv.home_module.R;
import cn.huanjv.home_module.contract.HomeContract;
import cn.huanjv.home_module.databinding.ActivityHomeBinding;
import cn.huanjv.home_module.presenter.HomePresenter;
import cn.huanjv.home_module.view.fragment.HomeAllOrderFragment;

public class HomeActivity extends BaseActivity<HomeContract.View, HomePresenter> {

    private ActivityHomeBinding mActivityHomeBinding;
    private String[] mDataList = {"全部", "抢单", "待完成", "已完成"};
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentAdapter mFragmentAdapter;
    private Handler statusHandler;

    @Override
    public HomePresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setColor(this, 0xfffafafa);
        mActivityHomeBinding = ActivityHomeBinding.inflate(LayoutInflater.from(this));
        setContentView(mActivityHomeBinding.getRoot());
        initViewPager();
        initDrawLayout();
        initListener();
        statusHandler = new Handler();
        setNotification();
    }
    Runnable statusRb = new Runnable() {
        @Override
        public void run() {
            StatusBarUtil.setStatusBarColor(HomeActivity.this,0xff000000);
            statusHandler.postDelayed(statusRb, 100);
        }
    };
    private void setNotification() {
        NotificationUtil.getInstance().showNotification(this, HomeActivity.class, R.mipmap.ic_user_auth,
                "黑诶嘿嘿", "正在进行xxxx模式一推拿治疗，点击返回治疗", "tickerText");
        statusHandler.post(statusRb);
    }

    private void initDrawLayout() {

        mActivityHomeBinding.ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityHomeBinding.drawHome.openDrawer(Gravity.LEFT);
            }
        });

    }

    private void initListener() {
        mActivityHomeBinding.tvAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterPath.USER_SETTING)
                        .navigation();
                mActivityHomeBinding.drawHome.closeDrawer(Gravity.LEFT);
            }
        });
        mActivityHomeBinding.tvMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterPath.USER_MONEY)
                        .navigation();
                mActivityHomeBinding.drawHome.closeDrawer(Gravity.LEFT);
            }
        });
    }

    private void initViewPager() {
        mFragmentList.add(new HomeAllOrderFragment());
        mFragmentList.add(new HomeAllOrderFragment());
        mFragmentList.add(new HomeAllOrderFragment());
        mFragmentList.add(new HomeAllOrderFragment());
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mActivityHomeBinding.vpContainer.setAdapter(mFragmentAdapter);
        mActivityHomeBinding.mgTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mDataList[index]);
                simplePagerTitleView.setTextSize(17);
                simplePagerTitleView.getPaint().setFakeBoldText(true);
                simplePagerTitleView.setNormalColor(Color.parseColor("#929292"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#0D0D0D"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivityHomeBinding.vpContainer.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 6));
                indicator.setLineWidth(UIUtil.dip2px(context, 26));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#ffdf00"));
                return indicator;
            }
        });
        mActivityHomeBinding.mgTitle.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mActivityHomeBinding.mgTitle, mActivityHomeBinding.vpContainer);
    }

    @Override
    public void initData() {

    }
}

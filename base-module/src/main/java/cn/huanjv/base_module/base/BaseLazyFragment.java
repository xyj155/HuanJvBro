package cn.huanjv.base_module.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseLazyFragment<V extends BaseView, T extends BasePresenter> extends Fragment {
    protected T mPresenter;
    protected V mBaseView;
    protected Context mContext;//activity的上下文对象


    //双重判定，保证懒加载
    protected boolean isVisible;//这个，标记，当前Fragment是否可见
    private boolean isPrepared = false;//这个，标记当前Fragment是否已经执行了onCreateView
    //只有两个标记同时满足，才进行数据加载


    /**
     * 绑定activity
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public abstract void initData();

    public abstract void initView(View view);

    /**
     * 运行在onAttach之后
     * 可以接受别人传递过来的参数,实例化对象.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取bundle,并保存起来

        //创建presenter
        mPresenter = initPresenter();


    }


    private ViewGroup viewGroup;
    public View inflate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isPrepared = true;
        inflate = inflater.inflate(initLayout(), container, false);
//        LoadingView loadingView = inflate.findViewById(R.id.loading_footer);
//        if (loadingView != null)
//            loadingView.start();
//        LoadingView loadingView1 = inflate.findViewById(R.id.loading_header);
//        if (loadingView1 != null)
//            loadingView1.start();

        this.viewGroup = container;
        initView(inflate);
        onLazyLoad();
        return inflate;
    }




    protected static Handler mHandler = new Handler(Looper.getMainLooper());

    private void onLazyLoad() {
        if (isPrepared && isVisible) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    isPrepared = false;//懒加载，只加载一次,这句话要不要，就具体看需求
                    initData();
                }
            }, 200);
        } else {

        }
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public abstract int initLayout();

    /**
     * 类似Activity的OnBackgress
     * fragment进行回退
     */
    public void onBack() {
        getFragmentManager().popBackStack();
    }

    /**
     * 初始化Fragment应有的视图
     *
     * @return
     */
//    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 创建prensenter
     *
     * @return <T extends BasePresenter> 必须是BasePresenter的子类
     */
    public abstract T initPresenter();

    @Override
    public Context getContext() {
        return mContext;
    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }


    }

    /**
     * 判断当前的Fragment是否可见(相对于其他的Fragment)
     */
    protected boolean mIsVisible;

    /**
     * 标志位，标志已经初始化完成
     */
    protected boolean mIsprepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    protected boolean mHasLoadedOnce;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //设置Fragment的可见状态
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {//getUserVisibleHint获取Fragment可见状态
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }

        if (isResumed()) {
            onVisibilityChangedToUser(isVisibleToUser);
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
        stopLoad();
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {
    }

    //region 神策埋点需要，统计Fragement 可见时间
    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            onVisibilityChangedToUser(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onVisibilityChangedToUser(false);
        }
    }

    /**
     * 当Fragment对用户的可见性发生了改变的时候就会回调此方法
     *
     * @param isVisibleToUser true：用户能看见当前Fragment；false：用户看不见当前Fragment
     */
    public void onVisibilityChangedToUser(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            visibleToUser();
        } else {
            inVisibleToUser();
        }
    }

    protected void visibleToUser() {

    }

    protected void inVisibleToUser() {

    }
    //endregion
}
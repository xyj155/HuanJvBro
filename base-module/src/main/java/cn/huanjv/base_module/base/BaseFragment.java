package cn.huanjv.base_module.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.umeng.analytics.MobclickAgent;


public abstract class BaseFragment<V extends BaseView, T extends BasePresenter> extends Fragment {
    protected T mPresenter;
    protected V mBaseView;
    protected Context mContext;//activity的上下文对象



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
        inflate = inflater.inflate(initLayout(), container, false);
//        LoadingView loadingView = inflate.findViewById(R.id.loading_footer);
//        if (loadingView != null)
//            loadingView.start();
//        LoadingView loadingView1 = inflate.findViewById(R.id.loading_header);
//        if (loadingView1 != null)
//            loadingView1.start();

        this.viewGroup = container;
        initView(inflate);
        initData();
        return inflate;
    }




    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    public abstract int initLayout();

    /**
     * 类似Activity的OnBackgress
     * fragment进行回退
     */
    public void onBack() {
        getFragmentManager().popBackStack();
    }


    public abstract T initPresenter();

    @Override
    public Context getContext() {
        return mContext;
    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onStart() {
        super.onStart();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }
}

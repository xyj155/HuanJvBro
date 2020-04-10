package cn.huanjv.home_module.view.fragment;

import android.view.View;

import cn.huanjv.base_module.base.BaseFragment;
import cn.huanjv.home_module.R;
import cn.huanjv.home_module.contract.HomeContract;
import cn.huanjv.home_module.presenter.HomePresenter;

public class HomeAllOrderFragment extends BaseFragment<HomeContract.View, HomePresenter> {
    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_home_all_order;
    }

    @Override
    public HomePresenter initPresenter() {
        return null;
    }
}

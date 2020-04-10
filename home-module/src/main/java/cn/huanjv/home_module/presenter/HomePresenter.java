package cn.huanjv.home_module.presenter;

import cn.huanjv.base_module.base.BasePresenter;
import cn.huanjv.home_module.contract.HomeContract;

public class HomePresenter  extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    public HomePresenter(HomeContract.View mMvpView) {
        super(mMvpView);
    }
}

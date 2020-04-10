package cn.huanjv.user_module.presenter;

import cn.huanjv.base_module.base.BasePresenter;
import cn.huanjv.user_module.contract.UserContract;

public class UserPresenter extends BasePresenter<UserContract.View> implements UserContract.Presenter {

    public UserPresenter(UserContract.View mMvpView) {
        super(mMvpView);
    }
}

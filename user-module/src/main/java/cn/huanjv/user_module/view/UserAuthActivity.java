package cn.huanjv.user_module.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.huanjv.base_module.arouter.RouterPath;
import cn.huanjv.base_module.base.BaseActivity;
import cn.huanjv.user_module.R;
import cn.huanjv.user_module.contract.UserContract;
import cn.huanjv.user_module.presenter.UserPresenter;

@Route(path = RouterPath.USER_SETTING)
public class UserAuthActivity extends BaseActivity<UserContract.View, UserPresenter> {


    @Override
    public UserPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_auth;
    }

    @Override
    public void initView() {
        initToolbar().setTitle("");
    }

    @Override
    public void initData() {

    }
}

package cn.huanjv.user_module.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.huanjv.base_module.base.BaseActivity;
import cn.huanjv.user_module.R;
import cn.huanjv.user_module.contract.UserContract;
import cn.huanjv.user_module.presenter.UserPresenter;

public class UserBankCardActivity extends BaseActivity<UserContract.View, UserPresenter> {


    @Override
    public UserPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_bank_card;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}

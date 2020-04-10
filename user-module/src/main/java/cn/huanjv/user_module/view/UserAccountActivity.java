package cn.huanjv.user_module.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.huanjv.base_module.arouter.RouterPath;
import cn.huanjv.base_module.base.BaseActivity;
import cn.huanjv.user_module.R;
import cn.huanjv.user_module.contract.UserContract;
import cn.huanjv.user_module.databinding.ActivityUserAccountBinding;
import cn.huanjv.user_module.presenter.UserPresenter;

@Route(path = RouterPath.USER_MONEY)
public class UserAccountActivity extends BaseActivity<UserContract.View, UserPresenter> {

    private ActivityUserAccountBinding mActivityUserAccountBinding;

    @Override
    public UserPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_account;
    }

    @Override
    public void initView() {
        mActivityUserAccountBinding = ActivityUserAccountBinding.inflate(LayoutInflater.from(this));
        View lRoot = mActivityUserAccountBinding.getRoot();
        setContentView(lRoot);
        initToolbar().setTitle("");
        mActivityUserAccountBinding.tvBankCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserBankCardActivity.class);
            }
        });
    }

    @Override
    public void initData() {

    }
}

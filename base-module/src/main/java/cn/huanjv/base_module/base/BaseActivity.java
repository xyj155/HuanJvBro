package cn.huanjv.base_module.base;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.umeng.analytics.MobclickAgent;

import cn.huanjv.base_module.R;
import cn.huanjv.base_module.util.ActivityCollector;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BaseActivity<V extends BaseView, T extends BasePresenter<V>> extends FragmentActivity {
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();
    public T mPresenter;

    private CompositeDisposable mDisposables = new CompositeDisposable();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        ActivityCollector.addActivity(this);

        mPresenter = getPresenter();
        setContentView(intiLayout());

        initView();
        initData();
    }



    public void subscribe(Disposable disposable) {
        mDisposables.add(disposable);
    }

    public void setLinerLayout(RecyclerView recyclerView, BaseQuickAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void unsubscribe() {
        if (mDisposables != null && !mDisposables.isDisposed()) {
            mDisposables.dispose();
            mDisposables.clear();
        }
    }

    public interface OnRefreshListener {
        void refresh(SmartRefreshLayout smartRefreshLayout);

        void reload(SmartRefreshLayout smartRefreshLayout);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        boolean finish = getIntent().getBooleanExtra("finish", false);
//        if (finish){
//            ARouter.getInstance().build(ARouterAPI.SPLASH_ACTIVITY)
//                    .withString("package",this.getClass().getPackage().getName()+"."+this.getClass().getSimpleName()).navigation();
//            Log.i(TAG, "onRestart: "+this.getClass().getSimpleName().toString());
//            Log.i(TAG, "onRestart: "+this.getClass().getPackage().getName());
//        }

    }


    public BaseActivity initToolbar() {

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        return this;
    }

    public BaseActivity setTitle(String title) {
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
        return this;
    }

    public BaseActivity setMenuTitle(String title, final View.OnClickListener clickListener) {
        TextView tvTitle = findViewById(R.id.tv_menu);
        tvTitle.setText(title);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view);
            }
        });
        return this;
    }


    private OnToolBarMenuOnClickListener onToolBarMenuOnClickListener;

    public interface OnToolBarMenuOnClickListener {
        void onMenuClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }

        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        unsubscribe();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
        ActivityCollector.removeActivity(this);
    }


    private onMenuClickListener onMenuClickListener;

    public void setOnMenuClickListener(BaseActivity.onMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    public interface onMenuClickListener {
        void onMenuClickListener();
    }

    public abstract T getPresenter();

    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();


}
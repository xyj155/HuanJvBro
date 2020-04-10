package cn.huanjv.base_module.base;

public interface Presenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}

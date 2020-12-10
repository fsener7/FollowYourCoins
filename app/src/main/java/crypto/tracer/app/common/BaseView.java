package crypto.tracer.app.common;


public interface BaseView<T> {

    void initView();

    void setPresenter(T presenter);

    void showProgress();

    void hideProgress();

}

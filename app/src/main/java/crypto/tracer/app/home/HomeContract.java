package crypto.tracer.app.home;

import crypto.tracer.app.common.BasePresenter;
import crypto.tracer.app.common.BaseView;
import crypto.tracer.app.models.PriceMultiFull;

import java.util.HashMap;



public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void showTrackedCoins(PriceMultiFull priceMultiFull);

        void goToSearchCoinView();

        void showErrorMsg(boolean showError);

        void goToCoinDetails(String coinTag, String coinName);

    }

    interface Presenter extends BasePresenter {

        void getTrackedCoinData(HashMap params);

    }

}

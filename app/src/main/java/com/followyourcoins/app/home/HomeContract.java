package com.followyourcoins.app.home;

import com.followyourcoins.app.common.BasePresenter;
import com.followyourcoins.app.common.BaseView;
import com.followyourcoins.app.models.PriceMultiFull;

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

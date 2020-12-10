package com.followyourcoins.app.data;

import com.followyourcoins.app.models.CoinListResponse;
import com.followyourcoins.app.models.PriceDetailsResponse;
import com.followyourcoins.app.models.PriceMultiFull;

import java.util.HashMap;

import io.reactivex.Observable;


public class LocalDataSource implements DataSource {
    private static LocalDataSource INSTANCE;

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource();
        }

        return INSTANCE;
    }

    @Override
    public Observable<PriceMultiFull> getTrackedCoins(HashMap params) {
        return null;
    }

    @Override
    public Observable<CoinListResponse> getAllCoins() {
        return null;
    }

    @Override
    public Observable<PriceDetailsResponse> getCoinDetails(HashMap params) {
        return null;
    }
}

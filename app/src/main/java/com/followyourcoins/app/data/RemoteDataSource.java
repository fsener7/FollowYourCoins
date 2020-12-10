package com.followyourcoins.app.data;

import com.followyourcoins.app.models.CoinListResponse;
import com.followyourcoins.app.models.PriceDetailsResponse;
import com.followyourcoins.app.models.PriceMultiFull;
import com.followyourcoins.app.webservice.APIService;
import com.followyourcoins.app.webservice.RestClient;

import java.util.HashMap;

import io.reactivex.Observable;


public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE;
    APIService service = RestClient.getAPIService();

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }

        return INSTANCE;
    }

    @Override
    public Observable<PriceMultiFull> getTrackedCoins(HashMap params) {
        return service.getTrackedCoins(params);
    }

    @Override
    public Observable<CoinListResponse> getAllCoins() {
        return service.getAllCoins();
    }

    @Override
    public Observable<PriceDetailsResponse> getCoinDetails(HashMap params) {
        return service.getCoinDetails(params);
    }
}

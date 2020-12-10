package com.followyourcoins.app.data;

import com.followyourcoins.app.models.CoinListResponse;
import com.followyourcoins.app.models.PriceDetailsResponse;
import com.followyourcoins.app.models.PriceMultiFull;

import java.util.HashMap;

import io.reactivex.Observable;


public interface DataSource {

    Observable<PriceMultiFull> getTrackedCoins(HashMap params);

    Observable<CoinListResponse> getAllCoins();

    Observable<PriceDetailsResponse> getCoinDetails(HashMap params);

}

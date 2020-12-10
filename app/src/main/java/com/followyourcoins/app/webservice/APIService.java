package com.followyourcoins.app.webservice;

import com.followyourcoins.app.models.CoinListResponse;
import com.followyourcoins.app.models.PriceDetailsResponse;
import com.followyourcoins.app.models.PriceMultiFull;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface APIService {

    @GET("pricemultifull")
    Observable<PriceMultiFull> getTrackedCoins(@QueryMap Map<String, Object> options);

    @GET("coinlist/")
    Observable<CoinListResponse> getAllCoins();

    @GET("histoday")
    Observable<PriceDetailsResponse> getCoinDetails(@QueryMap Map<String, Object> options);

}

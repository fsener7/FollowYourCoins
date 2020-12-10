package crypto.tracer.app.webservice;

import crypto.tracer.app.models.CoinListResponse;
import crypto.tracer.app.models.PriceDetailsResponse;
import crypto.tracer.app.models.PriceMultiFull;

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

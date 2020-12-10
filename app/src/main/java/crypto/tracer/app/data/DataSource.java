package crypto.tracer.app.data;

import crypto.tracer.app.models.CoinListResponse;
import crypto.tracer.app.models.PriceDetailsResponse;
import crypto.tracer.app.models.PriceMultiFull;

import java.util.HashMap;

import io.reactivex.Observable;


public interface DataSource {

    Observable<PriceMultiFull> getTrackedCoins(HashMap params);

    Observable<CoinListResponse> getAllCoins();

    Observable<PriceDetailsResponse> getCoinDetails(HashMap params);

}

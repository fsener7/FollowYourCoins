package crypto.tracer.app.data;

import crypto.tracer.app.models.CoinListResponse;
import crypto.tracer.app.models.PriceDetailsResponse;
import crypto.tracer.app.models.PriceMultiFull;

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

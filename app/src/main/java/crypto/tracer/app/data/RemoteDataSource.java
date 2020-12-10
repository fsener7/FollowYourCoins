package crypto.tracer.app.data;

import crypto.tracer.app.models.CoinListResponse;
import crypto.tracer.app.models.PriceDetailsResponse;
import crypto.tracer.app.models.PriceMultiFull;
import crypto.tracer.app.webservice.APIService;
import crypto.tracer.app.webservice.RestClient;

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

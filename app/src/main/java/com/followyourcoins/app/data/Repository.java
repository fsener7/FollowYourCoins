package com.followyourcoins.app.data;

import androidx.annotation.NonNull;

import com.followyourcoins.app.models.CoinListResponse;
import com.followyourcoins.app.models.PriceDetailsResponse;
import com.followyourcoins.app.models.PriceMultiFull;

import java.util.HashMap;

import io.reactivex.Observable;


public class Repository implements DataSource {

    private static Repository INSTANCE = null;
    private final DataSource remoteDataSource;
    private final DataSource localdDataSource;

    private Repository(@NonNull DataSource remoteDataSource,
                       @NonNull DataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localdDataSource = localDataSource;
    }

    public static Repository getInstance(@NonNull DataSource remoteDataSource,
                                         @NonNull DataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(remoteDataSource, localDataSource);
        }

        return INSTANCE;
    }

    @Override
    public Observable<PriceMultiFull> getTrackedCoins(HashMap params) {
        return remoteDataSource.getTrackedCoins(params);
    }

    @Override
    public Observable<CoinListResponse> getAllCoins() {
        return remoteDataSource.getAllCoins();
    }

    @Override
    public Observable<PriceDetailsResponse> getCoinDetails(HashMap params) {
        return remoteDataSource.getCoinDetails(params);
    }
}

package com.followyourcoins.app.utils;

import android.content.Context;
import com.followyourcoins.app.data.LocalDataSource;
import com.followyourcoins.app.data.RemoteDataSource;
import com.followyourcoins.app.data.Repository;
import com.followyourcoins.app.utils.schedulers.BaseSchedulerProvider;
import com.followyourcoins.app.utils.schedulers.SchedulerProvider;

public class Injection {
    public static Repository providesRepository(Context context) {
        return Repository.getInstance(RemoteDataSource.getInstance(),
                LocalDataSource.getInstance());
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}

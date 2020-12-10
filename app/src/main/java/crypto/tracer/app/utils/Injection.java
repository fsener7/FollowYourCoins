package crypto.tracer.app.utils;

import android.content.Context;
import crypto.tracer.app.data.LocalDataSource;
import crypto.tracer.app.data.RemoteDataSource;
import crypto.tracer.app.data.Repository;
import crypto.tracer.app.utils.schedulers.BaseSchedulerProvider;
import crypto.tracer.app.utils.schedulers.SchedulerProvider;

public class Injection {
    public static Repository providesRepository(Context context) {
        return Repository.getInstance(RemoteDataSource.getInstance(),
                LocalDataSource.getInstance());
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}

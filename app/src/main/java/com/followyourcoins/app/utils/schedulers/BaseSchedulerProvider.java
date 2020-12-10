package com.followyourcoins.app.utils.schedulers;

import androidx.annotation.NonNull;

import io.reactivex.Scheduler;


public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

}

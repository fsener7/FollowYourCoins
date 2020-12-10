package com.followyourcoins.app;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import com.followyourcoins.app.home.HomeActivity;
import com.followyourcoins.app.models.CoinListResponse;
import com.followyourcoins.app.utils.CoinHelper;
import com.followyourcoins.app.utils.Injection;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.tvAppName)
    TextView tvAppName;

    @BindView(R.id.ivLogo)
    ImageView ivLogo;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        initUserAction("", 0, false);
        CoinHelper.getInstance().prePopulateUserCoins();
        getAllCoins();
        startSplashAnimation();
    }

    private void goToHome() {
        timer = new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                overridePendingTransition(0, R.anim.fade_out);
                finish();
            }
        };

        timer.start();
    }

    private void getAllCoins() {
        if (CoinHelper.getInstance().getAllCachedCoinTags().size() > 0) return;

        service.getAllCoins()
                .subscribeOn(Injection.provideSchedulerProvider().io())
                .observeOn(Injection.provideSchedulerProvider().computation())
                .subscribe(new Observer<CoinListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CoinListResponse coinListResponse) {
                        Logger.i("getAllCoins done");
                        CoinHelper coinHelper = CoinHelper.getInstance();
                        coinHelper.setContext(SplashActivity.this);
                        coinHelper.updateAllCachedCoins(coinListResponse.getData(), true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.i("getAllCoins error");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void startSplashAnimation() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivLogo.getLayoutParams();
        params.topMargin = (int) (0.35 * height);
        ivLogo.setLayoutParams(params);

        ivLogo.animate()
                .setDuration(1000)
                .alpha(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tvAppName.animate()
                                .setDuration(400)
                                .alpha(1f)
                                .translationY(-(float) (0.45 * height))
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        goToHome();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                })
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }
}

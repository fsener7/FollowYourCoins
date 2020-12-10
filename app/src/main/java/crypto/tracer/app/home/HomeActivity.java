package crypto.tracer.app.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tapadoo.alerter.Alerter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import crypto.tracer.app.BaseActivity;
import crypto.tracer.app.CoinDetailsActivity;
import crypto.tracer.app.R;
import crypto.tracer.app.models.DisplayPrice;
import crypto.tracer.app.models.PriceMultiFull;
import crypto.tracer.app.models.RawPrice;
import crypto.tracer.app.search.SearchCoinsActivity;
import crypto.tracer.app.utils.CoinHelper;
import crypto.tracer.app.utils.Constants;
import crypto.tracer.app.utils.FontManager;
import crypto.tracer.app.utils.Injection;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @BindView(R.id.rvMyCoins)
    RecyclerView rvMyCoins;

    @BindView(R.id.tvMyCoinsCount)
    TextView tvMyCoinsCount;

    @BindView(R.id.llErrorMsg)
    LinearLayout llErrorMsg;

    @BindView(R.id.tvRefresh)
    TextView tvRefresh;

    @BindView(R.id.aviLoader)
    AVLoadingIndicatorView aviLoader;

    MyCoinsAdapter myCoinsAdapter;
    LinearLayoutManager layoutManager;
    HomeContract.Presenter presenter;
    boolean isRefreshUserCoins = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.fade_in, 0);
        new HomePresenterImpl(this,
                Injection.provideSchedulerProvider(),
                Injection.providesRepository(this));
    }

    @Override
    public void initView() {
        initToolbar("Crypto Tracer", 0);
        setupActionButton();
        setupErrorButton();
        refreshUserCoins();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        * If saved crypto and rvCoinCount
        * is not the same refresh the UI
        * */
        if (layoutManager != null) {
            int rvCoinCount = layoutManager.getItemCount();
            int userCoinCount = CoinHelper.getInstance()
                    .getAllUserCoins().size();

            if (rvCoinCount != userCoinCount) {
                isRefreshUserCoins = true;
                refreshUserCoins(); //
            }
        }
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress() {
        rvMyCoins.setVisibility(View.GONE);
        aviLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        rvMyCoins.setVisibility(View.VISIBLE);
        aviLoader.setVisibility(View.GONE);
    }

    @Override
    public void showTrackedCoins(PriceMultiFull priceMultiFull) {
        PriceMultiFull price = priceMultiFull;
        HashMap display = priceMultiFull.getDISPLAY();
        ArrayList<DisplayPrice> displayPrices = new ArrayList<>();
        ArrayList<RawPrice> rawPrices = new ArrayList<>();
        RecyclerView.ItemDecoration dividerItemDecoration =
                new RVDividerItemDecoration(ContextCompat.getDrawable(this,
                        R.drawable.bg_rv_separator));

        if (display.size() > 0) {
            displayPrices = priceMultiFull.getDisplayPrices();
            rawPrices = priceMultiFull.getRawPrices();

            myCoinsAdapter = new MyCoinsAdapter(this, displayPrices, rawPrices, this);
            layoutManager = new LinearLayoutManager(this);
            ScaleInAnimator animator = new ScaleInAnimator();
            animator.setChangeDuration(2000);
            rvMyCoins.setAdapter(myCoinsAdapter);
            rvMyCoins.setLayoutManager(layoutManager);
            rvMyCoins.setNestedScrollingEnabled(false);
            rvMyCoins.setItemAnimator(animator);

            if (!isRefreshUserCoins) {
                // add item decoration only once
                rvMyCoins.addItemDecoration(dividerItemDecoration);
            } else {
                isRefreshUserCoins = false;
            }

            if (isCoinAdded) {
                Alerter.create(this)
                        .setTitle("Your crypto(s) have been added")
                        .setBackgroundColorRes(R.color.colorPositiveNotification)
                        .setIcon(R.drawable.ic_thumbs_up_o)
                        .setDuration(2000)
                        .show();
                isCoinAdded = false;
            }
        } else {

        }
    }

    @Override
    public void goToSearchCoinView() {
        startActivity(new Intent(this, SearchCoinsActivity.class));
        overridePendingTransition(R.anim.slide_from_bottom, R.anim.stay);
    }

    @Override
    public void showErrorMsg(boolean showError) {
        if (showError) {
            llErrorMsg.setVisibility(View.VISIBLE);
        } else {
            llErrorMsg.setVisibility(View.GONE);
        }
    }

    @Override
    public void goToCoinDetails(String coinTag, String coinName) {
        Bundle extras = new Bundle();
        extras.putString(Constants.COIN_TAG, coinTag);
        extras.putString(Constants.COIN_NAME, coinName);
        Intent intent = CoinDetailsActivity.getIntent(this, extras);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @OnClick(R.id.llErrorMsg)
    public void refreshUserCoins() {
        HashMap params = new HashMap();
        ArrayList<String> coinsList = CoinHelper.getInstance().getAllUserCoins();
        tvMyCoinsCount.setText("My Coins(" +
                Integer.toString(coinsList.size()) + ")");
        String coins = android.text.TextUtils.join(",", coinsList);
        params.put("fsyms", coins);
        params.put("tsyms", "USD");
        presenter.getTrackedCoinData(params);
    }

    private void setupActionButton() {
        TextView tvActionButton = (TextView) findViewById(R.id.tvActionButton);
        TextView tvActionDescription = (TextView) findViewById(R.id.tvActionDescription);
        Typeface fontawesome = FontManager.getTypeface(this, FontManager.FONTMATERIAL);
        FontManager.setTypeface(tvActionButton, fontawesome);

        tvActionButton.setText(getResources().getString(R.string.material_icon_plus));
        tvActionDescription.setText("Add a Coin");

        tvActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchCoinView();
            }
        });
    }

    private void setupErrorButton() {
        Typeface fontawesome = FontManager.getTypeface(this, FontManager.FONTMATERIAL);
        FontManager.setTypeface(tvRefresh, fontawesome);
    }
}
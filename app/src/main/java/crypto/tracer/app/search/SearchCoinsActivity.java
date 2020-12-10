package crypto.tracer.app.search;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import crypto.tracer.app.BaseActivity;
import crypto.tracer.app.R;
import crypto.tracer.app.home.RVDividerItemDecoration;
import crypto.tracer.app.utils.CoinHelper;
import crypto.tracer.app.utils.FontManager;

public class SearchCoinsActivity extends BaseActivity implements SearchCoinListener {

    @BindView(R.id.rvAllCoins)
    RecyclerView rvAllCoins;

    @BindView(R.id.tvSelectedCoinsCount)
    TextView tvSelectedCoinsCount;

    @BindView(R.id.tvClearSearch)
    TextView tvClearSearch;

    @BindView(R.id.etSearch)
    EditText etSearch;

    AllCoinsAdapter allCoinsAdapter;
    LinearLayoutManager layoutManager;
    private EndlessRecyclerViewScrollListener scrollListener;
    ArrayList<String> allCoinNames = new ArrayList<>();
    ArrayList<String> allCoinTags = new ArrayList<>();
    HashMap<String, String> selectedCoins = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_coins);
        getSupportActionBar().hide();
        setupActionButton();
        setupSearchBar();

        allCoinNames = CoinHelper.getInstance().getAllCachedCoinNames();
        allCoinTags = CoinHelper.getInstance().getAllCachedCoinTags();

        // Search activity doesn't have a toolbar. Adding this rule, since
        // To prevent search bar from coming down near useractionbar
        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) rlContainer.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    }

    private void setupAllCoins() {
        ArrayList<String> coins = CoinHelper.getInstance().getCachedCoinsByPage(0);

        /*
        *
        * call the API, save and load coins
        * */
        if (coins.size() == 0) {
            return; //
        }

        allCoinsAdapter = new AllCoinsAdapter(this, coins);
        layoutManager = new LinearLayoutManager(this);
        rvAllCoins.setAdapter(allCoinsAdapter);
        rvAllCoins.setLayoutManager(layoutManager);
        rvAllCoins.setHasFixedSize(true);
        rvAllCoins.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration dividerItemDecoration =
                new RVDividerItemDecoration(ContextCompat.getDrawable(this,
                        R.drawable.bg_rv_separator));
        rvAllCoins.addItemDecoration(dividerItemDecoration);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Logger.i("COINLIST reached end page " + page);
                loadNextData(page);
            }
        };

        rvAllCoins.addOnScrollListener(scrollListener);
    }

    private void setupActionButton() {
        TextView tvActionButton = (TextView) findViewById(R.id.tvActionButton);
        TextView tvActionDescription = (TextView) findViewById(R.id.tvActionDescription);
        Typeface fontawesome = FontManager.getTypeface(this, FontManager.FONTMATERIAL);
        FontManager.setTypeface(tvActionButton, fontawesome);

        tvActionButton.setText(getResources().getString(R.string.material_icon_done));
        tvActionDescription.setText("Done");

        tvActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String coinTag : selectedCoins.keySet()) {
                    CoinHelper.getInstance()
                            .addUserCoin(coinTag, selectedCoins.get(coinTag));
                    isCoinAdded = true;
                }

                dismissView();
            }
        });
    }

    @Override
    public void onCoinSelected(String coinTag, String coinName) {
        selectedCoins.put(coinTag, coinName);
        tvSelectedCoinsCount.setText("Selected Coins(" +
                Integer.toString(selectedCoins.size()) + ")");
    }

    @Override
    public void onCoinUnselected(String coinTag, String coinName) {
        if (selectedCoins.containsKey(coinTag)) {
            selectedCoins.remove(coinTag);
            tvSelectedCoinsCount.setText("Selected Coins(" +
                    Integer.toString(selectedCoins.size()) + ")");
        }
    }

    private void loadNextData(int page) {
        ArrayList<String> newCoins = CoinHelper.getInstance().getCachedCoinsByPage(page);
        int count = allCoinsAdapter.getItemCount();
        allCoinsAdapter.addItems(newCoins);
        int newCount = allCoinsAdapter.getItemCount();
        allCoinsAdapter.notifyItemRangeInserted(count, newCount - count);
    }

    private void setupSearchBar() {
        Typeface fontawesome = FontManager.getTypeface(this, FontManager.FONTMATERIAL);
        FontManager.setTypeface(tvClearSearch, fontawesome);
        tvClearSearch.setText(getResources().getString(R.string.material_icon_close));
        tvClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
                resetAutoCompleteResults();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadAutoCompleteResults(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadAutoCompleteResults(String str) {
        rvAllCoins.removeOnScrollListener(scrollListener);
        ArrayList<String> results = new ArrayList<>();
        String searchStr = str.toLowerCase();

        for (int i = 0; i < allCoinNames.size(); i++) {
            if (allCoinNames.get(i)
                    .toLowerCase()
                    .startsWith(searchStr)) {
                results.add(allCoinTags.get(i));
            }
        }

        // reset all items
        allCoinsAdapter.reset();
        allCoinsAdapter.notifyDataSetChanged();

        // add search items
        int count = allCoinsAdapter.getItemCount();
        allCoinsAdapter.addItems(results);
        int newCount = allCoinsAdapter.getItemCount();
        allCoinsAdapter.notifyItemRangeInserted(count, newCount - count);
    }

    private void resetAutoCompleteResults() {
        ArrayList<String> coins = CoinHelper.getInstance().getCachedCoinsByPage(0);
        allCoinsAdapter.addItems(coins);
        allCoinsAdapter.notifyDataSetChanged();
        rvAllCoins.addOnScrollListener(scrollListener);
    }

    private void dismissView() {
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_to_bottom);
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        setupAllCoins();
    }

    @Override
    public void onBackPressed() {
        dismissView();
    }
}

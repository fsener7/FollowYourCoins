package com.followyourcoins.app.search;

import android.content.Context;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.followyourcoins.app.R;
import com.followyourcoins.app.utils.CoinHelper;
import com.followyourcoins.app.utils.TextUtils;

public class AllCoinsAdapter extends RecyclerView.Adapter<AllCoinsAdapter.ViewHolder> {

//    private ArrayList<String> items;
    private ArrayList<String> coinTags;
    private Context context;
    SearchCoinListener searchCoinListener;
    CoinHelper coinHelper;

    public AllCoinsAdapter(Context context,
                           ArrayList<String> coinTags) {
//        this.items = items;
        this.context = context;
        this.searchCoinListener = (SearchCoinListener) context;
        this.coinTags = coinTags;
        coinHelper = CoinHelper.getInstance();

        // Don't show coins that user has already selected
        ArrayList<String> userCoins = coinHelper.getAllUserCoins();
        for (String coinTag : userCoins) {
            coinTags.remove(coinTag);
        }

        Logger.i("COINLIST coinTags size " + Integer.toString(coinTags.size()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_coin, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String coinTag = coinTags.get(position);
        final String coinName = coinHelper.getCoinName(coinTag);
        String displayTag = coinTag;

        if (TextUtils.isValidString(coinName)) {
            holder.tvCoinName.setText(coinName);
        } else {
            holder.tvCoinName.setText("n/a");
        }

        if (TextUtils.isValidString(displayTag)) {
            if (displayTag.length() > 4) {
                displayTag = displayTag.substring(0, 4) + "..";
            }
            holder.tvCoinTag.setText(displayTag);
        }

        holder.cbSelectedCoin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (searchCoinListener != null) {
                    if (isChecked) {
                        searchCoinListener.onCoinSelected(coinTag, coinName);
                    } else {
                        searchCoinListener.onCoinUnselected(coinTag, coinName);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return coinTags.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCoinTag)
        TextView tvCoinTag;

        @BindView(R.id.tvCoinName)
        TextView tvCoinName;

        @BindView(R.id.cbSelectedCoin)
        CheckBox cbSelectedCoin;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @UiThread
    public void addItems(ArrayList<String> coinTags) {
        Logger.i("COINLIST additems called");
        // Don't show coins that user has already selected
        ArrayList<String> userCoins = CoinHelper.getInstance().getAllUserCoins();
        for (String coinTag : userCoins) {
            coinTags.remove(coinTag);
        }

        this.coinTags.addAll(coinTags);
    }

    public void reset() {
        this.coinTags.clear();
    }
}

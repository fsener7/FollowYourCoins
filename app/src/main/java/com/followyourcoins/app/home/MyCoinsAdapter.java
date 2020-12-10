package com.followyourcoins.app.home;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.followyourcoins.app.R;
import com.followyourcoins.app.models.DisplayPrice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.followyourcoins.app.models.RawPrice;
import com.followyourcoins.app.utils.CoinHelper;
import com.followyourcoins.app.utils.TextUtils;


public class MyCoinsAdapter extends RecyclerView.Adapter<MyCoinsAdapter.ViewHolder> {

    ArrayList<DisplayPrice> displayPrices;
    ArrayList<RawPrice> rawPrices;
    Context context;
    DecimalFormat format = new DecimalFormat(TextUtils.COIN_FORMAT);
    HomeContract.View view;

    public MyCoinsAdapter(Context context,
                          ArrayList<DisplayPrice> displayPrices,
                          ArrayList<RawPrice> rawPrices,
                          HomeContract.View view) {
        this.displayPrices = displayPrices;
        this.rawPrices = rawPrices;
        this.context = context;
        this.view = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_coin, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DisplayPrice displayPrice = displayPrices.get(position);
        final RawPrice rawPrice = rawPrices.get(position);

        if (displayPrice.getPRICE() != null) {
            String formattedPrice = format.format(Float.parseFloat(rawPrice.getPRICE()));
            holder.tvPrice.setText(formattedPrice);
        } else {
            holder.tvPrice.setText("n/a");
        }

        if (rawPrice.getFROMSYMBOL() != null) {
            holder.tvCoinTag.setText(rawPrice.getFROMSYMBOL());
            String symbol = rawPrice.getFROMSYMBOL();
            String coinName = CoinHelper.getInstance().getCoinName(symbol);

            if (TextUtils.isValidString(coinName)) {
                holder.tvCoinName.setText(coinName);
            }
        } else {
            holder.tvCoinTag.setText("C");
        }


        holder.rlCoinItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coinName = CoinHelper.getInstance()
                        .getCoinName(rawPrice.getFROMSYMBOL());

                if (view != null) {
                    view.goToCoinDetails(rawPrice.getFROMSYMBOL(), coinName);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return displayPrices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvPrice)
        TextView tvPrice;

        @BindView(R.id.tvCoinTag)
        TextView tvCoinTag;

        @BindView(R.id.tvCoinName)
        TextView tvCoinName;

        @BindView(R.id.rlCoinItem)
        RelativeLayout rlCoinItem;

        @BindView(R.id.tvPriceGraph)
        TextView tvPriceGraph;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void remove(int position) {
        this.displayPrices.remove(position);
        this.rawPrices.remove(position);
        notifyItemRemoved(position);
    }
}

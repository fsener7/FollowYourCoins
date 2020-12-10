package com.followyourcoins.app.models;

import java.util.ArrayList;
import java.util.HashMap;

import com.followyourcoins.app.utils.CoinHelper;


public class PriceMultiFull {

    HashMap<String, HashMap<String, RawPrice>> RAW;
    HashMap<String, HashMap<String, DisplayPrice>> DISPLAY;

    public HashMap<String, HashMap<String, RawPrice>> getRAW() {
        return RAW;
    }

    public void setRAW(HashMap<String, HashMap<String, RawPrice>> RAW) {
        this.RAW = RAW;
    }

    public HashMap<String, HashMap<String, DisplayPrice>> getDISPLAY() {
        return DISPLAY;
    }

    public void setDISPLAY(HashMap<String, HashMap<String, DisplayPrice>> DISPLAY) {
        this.DISPLAY = DISPLAY;
    }

    public ArrayList<DisplayPrice> getDisplayPrices() {
        ArrayList<String> userCoins = CoinHelper.getInstance().getAllUserCoins();
        ArrayList<DisplayPrice> displayPrices = new ArrayList<>();
        HashMap<String, HashMap<String, DisplayPrice>> displayHash = getDISPLAY();

        for (String coinTag : userCoins) {
            HashMap h = displayHash.get(coinTag);

            if (h != null && h.containsKey("USD")) {
                DisplayPrice displayPrice = (DisplayPrice) h.get("USD");
                displayPrices.add(displayPrice);
            }
        }

        return displayPrices;
    }

    public ArrayList<RawPrice> getRawPrices() {
        ArrayList<String> userCoins = CoinHelper.getInstance().getAllUserCoins();
        ArrayList<RawPrice> rawPrices = new ArrayList<>();
        HashMap<String, HashMap<String, RawPrice>> rawHash = getRAW();

        for (String coinTag : userCoins) {
            HashMap h = rawHash.get(coinTag);
            if (h != null && h.containsKey("USD")) {
                RawPrice rawPrice = (RawPrice) h.get("USD");
                rawPrices.add(rawPrice);
            }
        }

        return rawPrices;
    }
}

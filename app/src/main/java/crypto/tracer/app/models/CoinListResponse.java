package crypto.tracer.app.models;

import java.util.HashMap;


public class CoinListResponse {
    HashMap<String, CoinListItem> Data;

    public HashMap<String, CoinListItem> getData() {
        return Data;
    }
}

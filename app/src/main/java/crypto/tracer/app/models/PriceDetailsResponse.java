package crypto.tracer.app.models;

import java.util.ArrayList;


public class PriceDetailsResponse {

    ArrayList<PricePoint> Data;

    public ArrayList<PricePoint> getData() {
        return Data;
    }

    public void setData(ArrayList<PricePoint> data) {
        Data = data;
    }
}

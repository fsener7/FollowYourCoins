package crypto.tracer.app.search;


public interface SearchCoinListener {

    void onCoinSelected(String coinTag, String coinName);

    void onCoinUnselected(String coinTag, String coinName);

}

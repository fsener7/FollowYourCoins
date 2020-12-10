package crypto.tracer.app.utils;



public class TextUtils {

    public static final String COIN_FORMAT  = "##,##,###.###";
    public static final String COIN_FORMAT_ROUNDED_OFF  = "##,###";

    public static boolean isValidString(String str) {
        if (str != null && str.length() > 0 &&
                !str.isEmpty() && !str.equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }
}

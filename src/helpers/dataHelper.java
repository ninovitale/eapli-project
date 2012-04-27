package helpers;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class dataHelper {

    public static boolean validaData(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        return sdf.parse(s, new ParsePosition(0)) != null;
    }

    public static boolean validaValidadeCartaoCredito(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        sdf.setLenient(false);
        return sdf.parse(s, new ParsePosition(0)) != null;
    }
}

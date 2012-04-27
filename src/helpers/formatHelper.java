package helpers;

/**
 *
 * @author HBK
 */
public class formatHelper {
    public static boolean isNumber(String string) {
        char[] c = string.toCharArray();
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(c[i])) {
                return false;
            }
        }
        return true;
    }
}

package utils;

public class Utils {

    private Utils() { /* No Instance allowed*/ }

    public static String getZerosForDifficulty(int length) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append("0");
        }
        return builder.toString();
    }
}

package util;

/**
 * Utility class. Provides methods for validating strings.
 *
 * @author Abbishek Kamak, Bao Hoang, Muskaan Sheik, Tom
 * @version 5/16/2024
 */
public class StringValidation {

    /**
     * Checks if the given string can be parsed as an integer.
     *
     * @param str the string to be checked
     * @return true if the string can be parsed as an integer, false otherwise
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the given string can be parsed as a double.
     *
     * @param str the string to be checked
     * @return true if the string can be parsed as a double, false otherwise
     */
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * deal with some exception
 */
public class Catcher {
    /**
     * validate the email input
     * @param email
     */
    public static void validateEmail(String email) {
        String emailRegEx = "^(.+)@(.+).com$";
        Pattern emailPattern = Pattern.compile(emailRegEx);
        Matcher emailMatcher = emailPattern.matcher(email);

        if (!emailMatcher.matches()) {
            throw new IllegalArgumentException("Please enter the correct email");
        }
    }


    /**
     * validate a date,the year must be 2022
     * @param date string of the date input
     */
    public static void validateDate(String date) {
        String dateRegEx = "^(\\d{2})/(\\d{2})/2022$";
        Pattern datePattern = Pattern.compile(dateRegEx);
        Matcher dateMatcher = datePattern.matcher(date);

        if (!dateMatcher.matches()) {
            throw new IllegalArgumentException("Please enter the correct date");
        }
    }
}

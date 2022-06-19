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
}

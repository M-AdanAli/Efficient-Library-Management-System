package com.adanali.library.util;

public class StringUtil {

    public static void validateNotNullOrBlank(String str, String attributeName) {
        if (str == null || str.trim().isEmpty()){
            throw new IllegalArgumentException(attributeName+" cannot be empty/blank!");
        }
    }

    public static boolean isValidEmail(String email) {
        validateNotNullOrBlank(email,"E-mail");
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (email.matches(emailPattern)) {
            return true;
        }else throw new IllegalArgumentException("Invalid E-mail format");
    }

    public static boolean isValidPassword(String password) {
        /*
        ^: Asserts the start of the string.
        (?=.*[0-9]): Positive lookahead ensuring at least one digit (0-9) is present.
        (?=.*[a-z]): Positive lookahead ensuring at least one lowercase English letter (a-z) is present.
        (?=.*[A-Z]): Positive lookahead ensuring at least one uppercase English letter (A-Z) is present.
        (?=.*[!@#$%^&+=]): Positive lookahead ensuring at least one special character from the specified set (!@#$%^&+=) is present.
        (?=\\S+$): Positive lookahead ensuring no whitespace characters are present in the entire string. \\S matches any non-whitespace character, and + ensures one or more.
        .{8,20}: Matches any character (except newline) between 8 and 20 times, defining the minimum and maximum length of the password.
        $: Asserts the end of the string.
        */
        validateNotNullOrBlank(password,"Password");
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*!@#$%^&+=])(?=\\S+$).{8,20}$";
        if (password.matches(passwordPattern)) {
            return true;
        } else throw new IllegalArgumentException("Password should be at least 8 characters long and at max 20.\n Must containing an uppercase character, a lower case character and a special character!");
    }

    public static boolean isValidIsbn(String isbn) {
        validateNotNullOrBlank(isbn,"ISBN");
        // Step 1: Check regex pattern (hyphens + group lengths) OR plain pattern without hyphen
        boolean hyphenedPatternValid = isbn.matches("^(?:978|979)-(\\d{1,5})-(\\d{1,7})-(\\d{1,6})-\\d$") && (isbn.length() == 17);
        boolean plainPattern = isbn.matches("^(978|979)\\d{10}$");
        // Step 2: Verify total length = 17 characters
        // (3 prefix + 9 group digits + 1 check digit + 4 hyphens)
        if ((hyphenedPatternValid || plainPattern)) {
            return true;
        }else throw new IllegalArgumentException("Invalid ISBN format");
    }
}

package com.adanali.library.util;

public class StringUtil {

    public static boolean isNotNullOrBlank(String str) {
        return (str != null) && !(str.trim().isEmpty());
    }

    public static boolean isValidEmail(String email) {
        if (isNotNullOrBlank(email)) {
            String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            return email.matches(emailPattern);
        }
        return false;
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

        if (isNotNullOrBlank(password)) {
            String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}$";
            return password.matches(passwordPattern);
        }
        return false;
    }

    public static boolean isValidIsbn(String isbn) {
        if (isNotNullOrBlank(isbn)) {
            // Step 1: Check regex pattern (hyphens + group lengths) OR plain pattern without hyphen
            boolean hyphenedPatternValid = isbn.matches("^(?:978|979)-(\\d{1,5})-(\\d{1,7})-(\\d{1,6})-\\d$") && (isbn.length() == 17);
            boolean plainPattern = isbn.matches("^(978|979)\\d{10}$");
            // Step 2: Verify total length = 17 characters
            // (3 prefix + 9 group digits + 1 check digit + 4 hyphens)
            return (hyphenedPatternValid || plainPattern);
        }
        return false;
    }

    public static boolean isNumber(String value) {
        if (isNotNullOrBlank(value)) {
            return value.matches("\\d+");
        }
        return false;
    }

    public static boolean isValidBookSearchAttribute(String attribute){
        if (isNotNullOrBlank(attribute)){
            return (attribute.equalsIgnoreCase("title") || attribute.equalsIgnoreCase("author") || attribute.equalsIgnoreCase("genre") || attribute.equalsIgnoreCase("all"));
        }
        return false;
    }
}

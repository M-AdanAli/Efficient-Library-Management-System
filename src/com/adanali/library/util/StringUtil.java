package com.adanali.library.util;

public class StringUtil {

    public static boolean isNotNullOrBlank(String str){
        return (str!=null) && (!str.isEmpty());
    }

    public static boolean isValidEmail(String email){
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailPattern);
    }

}

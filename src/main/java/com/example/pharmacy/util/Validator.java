package com.example.pharmacy.util;

import java.util.regex.Pattern;

public class Validator {
    private Validator(){}
    private static final int MIN_USERNAME_LENGTH = 1;
    private static final int MAX_USERNAME_LENGTH = 15;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

    public static boolean validateUsername(String username) {
        int usernameLength = username.length();
        return usernameLength >= MIN_USERNAME_LENGTH &&
                usernameLength <= MAX_USERNAME_LENGTH &&
                USERNAME_PATTERN.matcher(username).matches();
    }
}
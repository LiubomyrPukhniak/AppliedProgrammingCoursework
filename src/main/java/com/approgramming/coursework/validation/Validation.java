package com.approgramming.coursework.validation;

import java.util.regex.Pattern;

public class Validation {
    public static boolean email(String email) {
        return Pattern.matches("^[a-zA-z][\\w\\.\\_]+@([a-zA-z]{2,7}\\.)+[a-zA-z]{2,5}", email) && email.length() <= 20;
    }
    public static boolean password(String password) {
        return Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", password);
    }
    public static boolean name(String name) {
        return Pattern.matches("[a-zA-z]{2,20}", name);
    }
    public static boolean surname(String surname) {
        return Pattern.matches("[a-zA-z]{2,20}", surname);
    }
}

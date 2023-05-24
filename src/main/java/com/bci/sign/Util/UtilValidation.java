package com.bci.sign.Util;

import java.util.regex.Pattern;

public class UtilValidation {

    public static boolean patternMatches(String field, String regex) {
        return Pattern.compile(regex)
                .matcher(field)
                .matches();
    }
}

package com.makapps.streeksha.Utilities;

import static com.makapps.streeksha.Utilities.Constants.ALPHA_NUMERIC_STRING;

public class UtilFn {
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}

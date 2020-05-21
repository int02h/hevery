package com.dpforge.hevery.cli.util;

public class TextUtils {
    private TextUtils() {
    }

    public static boolean isNullOrEmpty(CharSequence value) {
        return value == null || value.length() == 0;
    }
}

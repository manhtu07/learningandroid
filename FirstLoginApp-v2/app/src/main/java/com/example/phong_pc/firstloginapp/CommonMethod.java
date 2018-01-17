package com.example.phong_pc.firstloginapp;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by Phong-PC on 1/17/2018.
 */

public class CommonMethod {
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{6,12}"
        );
        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

    public static boolean isValidUsername(String s) {
        Pattern USERNAME_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9]{4,12}"
        );
        return !TextUtils.isEmpty(s) && USERNAME_PATTERN.matcher(s).matches();
    }
}

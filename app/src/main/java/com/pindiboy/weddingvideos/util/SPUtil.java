package com.pindiboy.weddingvideos.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.pindiboy.weddingvideos.App;
import com.pindiboy.weddingvideos.common.Constant;

/**
 * Created by Jiangwenjin on 2017/3/7.
 */

public class SPUtil {
    public static SharedPreferences getSP() {
        return App.getInstance().getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
    }

    public static String getCountryCode() {
        return getSP().getString(Constant.SP_KEY_COUNTRY_CODE, "");
    }

    public static void setCountryCode(String countryCode) {
        getSP().edit().putString(Constant.SP_KEY_COUNTRY_CODE, countryCode).apply();
    }
}

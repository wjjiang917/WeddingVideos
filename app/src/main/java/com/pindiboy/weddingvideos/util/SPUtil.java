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
        return App.getInstance().getSharedPreferences(Constant.NAME_SP, Context.MODE_PRIVATE);
    }
}

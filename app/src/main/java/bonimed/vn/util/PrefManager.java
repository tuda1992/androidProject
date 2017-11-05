package bonimed.vn.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by acv on 10/26/17.
 */

public class PrefManager {

    public static void putJsonObjectUserLogin(Context context, String jsonObject) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.USER_LOGIN, jsonObject).commit();
    }

    public static void removeJsonObjectUserLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(Constants.USER_LOGIN).commit();
    }

    public static String getJsonObjectUserLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.USER_LOGIN, "");
    }

    public static void putJsonObjectOrderProduct(Context context, String jsonObject) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.ORDER_PRODUCT, jsonObject).commit();
    }

    public static void removeJsonObjectOrderProduct(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(Constants.ORDER_PRODUCT).commit();
    }

    public static String getJsonObjectOrderProduct(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.ORDER_PRODUCT, "");
    }

    public static void putLoginState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(Constants.LOGIN_STATE, 1).commit();
    }

    public static int getLoginState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constants.LOGIN_STATE, 0);
    }

    public static void clearAllData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

}

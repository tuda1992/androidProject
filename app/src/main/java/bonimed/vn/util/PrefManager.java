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

    public static void clearAllData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

}
package bonimed.vn.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import bonimed.vn.listener.JsonArrayCallBackListener;
import bonimed.vn.listener.JsonObjectCallBackListener;

/**
 * Created by mac on 10/24/17.
 */

public class FastNetworking {

    private final String BASE_URL = "bonimed.vn/";
    private final String BASE_URL_TEST = "bonimed.com.vn/";
    private final String URL_LOGIN = "Authenticate/Login";
    private final String URL_PRODUCTS = "Products/ListProductsPaging";
    private final String URL_ORDERS = "";

    private Context mContext;
    private JsonObjectCallBackListener mListenerObject;
    private JsonArrayCallBackListener mListenerArray;

    public FastNetworking(Context context, JsonObjectCallBackListener listenerObject) {
        this.mContext = context;
        this.mListenerObject = listenerObject;
    }

    public FastNetworking(Context context, JsonArrayCallBackListener listenerArray) {
        this.mContext = context;
        this.mListenerArray = listenerArray;
    }

    public void callApiLogin(JSONObject jsonObject) {
        HashMap<String, String> headers = initCustomHeader("");
        AndroidNetworking.post(BASE_URL_TEST + URL_LOGIN)
                .addHeaders(headers)
               .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mListenerObject != null)
                            mListenerObject.onResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        if (mListenerObject != null)
                            mListenerObject.onError(error.getErrorBody().toString());
                    }
                });
    }

    @NonNull
    private HashMap<String, String> initCustomHeader(String token) {
        HashMap<String, String> headers = new HashMap<>();
//        if (!TextUtils.isEmpty(token))
//            headers.put("Authorization", "Bearer " + token);
        headers.put("Accept", "application/json");
        return headers;
    }


}
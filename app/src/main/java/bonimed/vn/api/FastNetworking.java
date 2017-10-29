package bonimed.vn.api;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.GzipRequestInterceptor;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import bonimed.vn.listener.JsonArrayCallBackListener;
import bonimed.vn.listener.JsonObjectCallBackListener;
import bonimed.vn.util.Constants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mac on 10/24/17.
 */

public class FastNetworking {

    private final String BASE_URL = "https://bonimed.vn/api/";
    private final String BASE_URL_TEST = "https://bonimed.com.vn/api/";
    private final String URL_LOGIN = "Authenticate/Login";
    private final String URL_PRODUCTS = "Products/ListProductsPaging";
    private final String URL_ORDERS = "Orders/ListAll";
    private final String URL_ORDERS_CREATE = "Orders/Create";

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
        try {
            jsonObject.put("VersionApp", mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        HashMap<String, String> headers = initCustomHeader("");
        AndroidNetworking.post(BASE_URL + URL_LOGIN)
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

    public void callApiProducts(JSONObject jsonObject, String token) {
        HashMap<String, String> headers = initCustomContentType();
        AndroidNetworking.post(BASE_URL + URL_PRODUCTS)
                .addHeaders(headers)
                .addStringBody("application/json")
                .addQueryParameter(Constants.SECURITY_TOKEN, token)
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
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }

    @NonNull
    private HashMap<String, String> initCustomContentType() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        return headers;
    }

}

package bonimed.vn.api;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public FastNetworking(Context context, JsonObjectCallBackListener listenerObject){
        this.mContext = context;
        this.mListenerObject = listenerObject;
    }

    public FastNetworking(Context context, JsonArrayCallBackListener listenerArray){
        this.mContext = context;
        this.mListenerArray = listenerArray;
    }

    public void callApiMethodGetReturnJsonObject(){
        AndroidNetworking.get("")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                    @Override
                    public void onError(ANError error) {
                    }
                });
    }

    public void callApiMethodGetReturnJsonArray(){
        AndroidNetworking.get("")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                    @Override
                    public void onError(ANError error) {
                    }
                });
    }

    public void callApiMethodPostReturnJsonObject(JSONObject jsonObject){
        AndroidNetworking.post("")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                    }
                    @Override
                    public void onError(ANError error) {
                    }
                });
    }

    public void callApiMethodPostReturnJsonArray(JSONObject jsonObject){
        AndroidNetworking.post("")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                    }
                    @Override
                    public void onError(ANError error) {
                    }
                });
    }

}

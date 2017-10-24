package bonimed.vn.listener;

import com.google.gson.JsonArray;

/**
 * Created by mac on 10/24/17.
 */

public interface JsonObjectCallBackListener {

    void onResponse(JsonArray jsonArray);
    void onError(String messageError);

}

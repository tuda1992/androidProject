package bonimed.vn.listener;

import com.google.gson.JsonObject;

/**
 * Created by mac on 10/24/17.
 */

public interface JsonArrayCallBackListener {

    void onResponse(JsonObject jsonObject);
    void onError(String messageError);

}

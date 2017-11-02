package bonimed.vn.listener;

import org.json.JSONObject;

/**
 * Created by acv on 11/2/17.
 */

public interface StringCallBackListener {

    void onResponse(String string);

    void onError(String messageError);

}

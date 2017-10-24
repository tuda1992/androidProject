package bonimed.vn;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by mac on 10/24/17.
 */

public class BonimedApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}

package bonimed.vn.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by acv on 10/25/17.
 */

public class UserLogin {

    @SerializedName("UserName")
    public String userName;
    @SerializedName("Password")
    public String password;
    @SerializedName("VersionApp")
    public String versionApp;


}

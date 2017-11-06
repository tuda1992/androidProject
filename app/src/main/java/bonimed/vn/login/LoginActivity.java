package bonimed.vn.login;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import bonimed.vn.MainActivity;
import bonimed.vn.R;
import bonimed.vn.api.FastNetworking;
import bonimed.vn.base.BaseActivity;
import bonimed.vn.listener.DialogOneButtonCallBackListener;
import bonimed.vn.listener.DialogTwoButtonCallBackListener;
import bonimed.vn.listener.JsonObjectCallBackListener;
import bonimed.vn.util.Constants;
import bonimed.vn.util.DialogUtil;
import bonimed.vn.util.Network;
import bonimed.vn.util.PrefManager;
import bonimed.vn.util.ProgressDialogUtils;
import bonimed.vn.util.Utils;

/**
 * Created by mac on 10/24/17.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEdtUsername, mEdtPassword;
    private Button mBtnSignIn;
    private TextView mTvLicenseUrl;
    private ProgressDialogUtils mProgress;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        mEdtUsername = (EditText) findViewById(R.id.edt_username);
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mBtnSignIn = (Button) findViewById(R.id.btn_login);
        mTvLicenseUrl = (TextView) findViewById(R.id.tv_license_url);
        mTvLicenseUrl.setPaintFlags(mTvLicenseUrl.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected void initListeners() {
        mBtnSignIn.setOnClickListener(this);
        mTvLicenseUrl.setOnClickListener(this);
        mEdtUsername.setOnFocusChangeListener(new MyOnFocusChangeListener());
        mEdtPassword.setOnFocusChangeListener(new MyOnFocusChangeListener());
        mProgress = new ProgressDialogUtils(this);
    }

    @Override
    protected void initDatas(Bundle saveInstanceStatte) {
        if (PrefManager.getLoginState(LoginActivity.this) == 1) {
            startActivityAnim(MainActivity.class, null);
            finish();
            return;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (!validateName()) {
                    return;
                }
                if (Network.isOnline(this)) {
                    callApiLogin(mEdtUsername.getText().toString(), mEdtPassword.getText().toString());
                } else {
                    DialogUtil.showAlertDialogOneButtonClicked(this, getString(R.string.title_no_connection)
                            , getString(R.string.message_no_connection), getString(R.string.text_positive), null);
                }
                break;
            case R.id.tv_license_url:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.URL_BONIMED));
                startActivity(i);
                break;
        }

    }

    private void callApiLogin(String username, String password) {
        mProgress.showDialog();
        final Gson gson = new Gson();
        UserLogin userLogin = new UserLogin();
        userLogin.userName = username;
        userLogin.password = password;
        final String json = gson.toJson(userLogin);
        try {
            JSONObject jsonObject = new JSONObject(json);
            FastNetworking fastNetworking = new FastNetworking(this, new JsonObjectCallBackListener() {
                @Override
                public void onResponse(final JSONObject jsonObject) {
                    mProgress.hideDialog();
                    UserLogin userLoginSuccess = gson.fromJson(jsonObject.toString(), UserLogin.class);
                    Log.d("TUDA", "user = " + userLoginSuccess);
                    if (userLoginSuccess.securityToken != null) {
                        DialogUtil.showAlertDialogButtonClicked(LoginActivity.this, ""
                                , getString(R.string.message_save_data), getString(R.string.text_positive), getString(R.string.text_negative), new DialogTwoButtonCallBackListener() {
                                    @Override
                                    public void onPositiveButtonClick() {
                                        PrefManager.putLoginState(LoginActivity.this);
                                        loginSuccess(jsonObject.toString());
                                    }

                                    @Override
                                    public void onNegativeButtonClick() {
                                        loginSuccess(jsonObject.toString());
                                    }
                                });
                    }
                }

                @Override
                public void onError(String messageError) {
                    mProgress.hideDialog();
                }
            });
            fastNetworking.callApiLogin(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            mProgress.hideDialog();
        }
    }

    private void loginSuccess(String jsonObject) {
        PrefManager.putJsonObjectUserLogin(LoginActivity.this, jsonObject);
        startActivityAnim(MainActivity.class, null);
        finish();
    }

    private class MyOnFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) {
                Utils.hideKeyboard(LoginActivity.this, view);
            }
        }
    }

    private boolean validateName() {
        if (TextUtils.isEmpty(mEdtUsername.getText().toString().trim())) {
            mEdtUsername.setError(getString(R.string.err_msg_username));
            requestFocus(mEdtUsername);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}

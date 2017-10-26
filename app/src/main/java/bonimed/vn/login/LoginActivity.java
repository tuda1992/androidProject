package bonimed.vn.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import bonimed.vn.R;
import bonimed.vn.api.FastNetworking;
import bonimed.vn.base.BaseActivity;
import bonimed.vn.listener.JsonObjectCallBackListener;
import bonimed.vn.util.Constants;
import bonimed.vn.util.Utils;

/**
 * Created by mac on 10/24/17.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEdtUsername, mEdtPassword;
    private Button mBtnSignIn;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        mEdtUsername = (EditText) findViewById(R.id.edt_username);
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mBtnSignIn = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected void initListeners() {
        mBtnSignIn.setOnClickListener(this);
//        mEdtUsername.addTextChangedListener(new MyTextWatcher(mEdtUsername));
//        mEdtPassword.addTextChangedListener(new MyTextWatcher(mEdtPassword));
        mEdtUsername.setOnFocusChangeListener(new MyOnFocusChangeListener());
        mEdtPassword.setOnFocusChangeListener(new MyOnFocusChangeListener());
    }

    @Override
    protected void initDatas(Bundle saveInstanceStatte) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (!validateName()) {
                    return;
                }
                callApiLogin(mEdtUsername.getText().toString(), mEdtPassword.getText().toString());
                break;
        }

    }

    private void callApiLogin(String username, String password) {
        Gson gson = new Gson();
        UserLogin userLogin = new UserLogin();
        userLogin.userName = username;
        userLogin.password = password;
        userLogin.versionApp = Constants.VERSION_APP;
        String json = gson.toJson(userLogin);
        try {
            JSONObject jsonObject = new JSONObject(json);
            FastNetworking fastNetworking = new FastNetworking(this, new JsonObjectCallBackListener() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Log.d("TUDA", "json = " + jsonObject);
                }

                @Override
                public void onError(String messageError) {
                    Log.d("TUDA", "error = " + messageError);
                }
            });
            fastNetworking.callApiLogin(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class MyOnFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) {
                Utils.hideKeyboard(LoginActivity.this, view);
            }
        }
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edt_username:
                    validateName();
                    break;
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

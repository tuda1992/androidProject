package bonimed.vn.orders;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bonimed.vn.R;
import bonimed.vn.api.FastNetworking;
import bonimed.vn.base.BaseActivity;
import bonimed.vn.listener.JsonArrayCallBackListener;
import bonimed.vn.login.UserLogin;
import bonimed.vn.util.Constants;
import bonimed.vn.util.DialogUtil;
import bonimed.vn.util.Network;
import bonimed.vn.util.PrefManager;
import bonimed.vn.util.ProgressDialogUtils;
import bonimed.vn.util.Utils;

/**
 * Created by acv on 11/7/17.
 */

public class DetailOrderActivity extends BaseActivity {

    private RecyclerView mRv;
    private LinearLayoutManager mLinearLayoutManager;
    private DetailOrderAdapter mDetailOrderAdapter;
    private List<DetailOrder> mListData = new ArrayList<>();
    private Gson mGson;
    private UserLogin mUserLogin;
    private Toolbar mToolbar;
    private ProgressDialogUtils mProgress;
    private TextView mTvProductMoney, mTvServiceMoney, mTvTotalMoney;
    private int mShipFee, mTotalPrice;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_detail_order;
    }

    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(getResources().getString(R.string.title_detail_order));

        mRv = (RecyclerView) findViewById(R.id.rv_data);
        mTvProductMoney = (TextView) findViewById(R.id.tv_product_money);
        mTvServiceMoney = (TextView) findViewById(R.id.tv_service_money);
        mTvTotalMoney = (TextView) findViewById(R.id.tv_total_money);
        mLinearLayoutManager = new LinearLayoutManager(this);
    }

    @Override
    protected void initListeners() {
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(mLinearLayoutManager);
        mDetailOrderAdapter = new DetailOrderAdapter(this, mListData);
        mRv.setAdapter(mDetailOrderAdapter);
        mProgress = new ProgressDialogUtils(this);
    }

    @Override
    protected void initDatas(Bundle saveInstanceStatte) {
        mGson = new Gson();

        String jsonUserLogin = PrefManager.getJsonObjectUserLogin(this);
        if (!TextUtils.isEmpty(jsonUserLogin)) {
            Gson gson = new Gson();
            mUserLogin = gson.fromJson(jsonUserLogin, UserLogin.class);
        }

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String orderId = b.getString(Constants.ORDER_ID, "");
            mShipFee = b.getInt(Constants.SHIP_FEE, 0);
            mTotalPrice = b.getInt(Constants.TOTAL_PRICE, 0);

            mTvProductMoney.setText(Utils.convertToCurrencyStr(mTotalPrice));
            mTvServiceMoney.setText(Utils.convertToCurrencyStr(mShipFee));
            mTvTotalMoney.setText(Utils.convertToCurrencyStr((mShipFee + mTotalPrice)));

            if (Network.isOnline(this)) {
                callApi(orderId);
            } else {
                DialogUtil.showAlertDialogOneButtonClicked(this, getString(R.string.title_no_connection)
                        , getString(R.string.message_no_connection), getString(R.string.text_positive), null);
            }
        }
    }

    private void callApi(String orderId) {
        if (mUserLogin != null) {
            mProgress.showDialog();
            FastNetworking fastNetworking = new FastNetworking(this, new JsonArrayCallBackListener() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    mProgress.hideDialog();
                    Type listType = new TypeToken<List<DetailOrder>>() {
                    }.getType();
                    List<DetailOrder> list = (List<DetailOrder>) mGson.fromJson(jsonArray.toString(), listType);
                    if (list.size() > 0) {
                        mListData.clear();
                        mListData.addAll(list);
                        mDetailOrderAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(String messageError) {
                    mProgress.hideDialog();
                }
            });
            fastNetworking.callApiGetDetailOrder(mUserLogin.securityToken, orderId);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finishActivityAnim();
        }

        return super.onOptionsItemSelected(item);
    }

}

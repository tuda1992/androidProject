package bonimed.vn.cart;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bonimed.vn.MainActivity;
import bonimed.vn.R;
import bonimed.vn.api.FastNetworking;
import bonimed.vn.base.BaseFragment;
import bonimed.vn.listener.DialogTwoButtonCallBackListener;
import bonimed.vn.listener.JsonObjectCallBackListener;
import bonimed.vn.listener.StringCallBackListener;
import bonimed.vn.products.DataProduct;
import bonimed.vn.products.Products;
import bonimed.vn.products.ResultProduct;
import bonimed.vn.util.DialogUtil;
import bonimed.vn.util.Network;
import bonimed.vn.util.PrefManager;
import bonimed.vn.util.Utils;
import bonimed.vn.widget.SearchLayout;
import okhttp3.internal.Util;

/**
 * Created by mac on 10/24/17.
 */

public class CartFragment extends BaseFragment implements SearchLayout.SearchCallBackListener, View.OnClickListener, CartAdapter.ItemClickCallBackListener {

    private RecyclerView mRv;
    private TextView mTvProductMoney, mTvServiceMoney, mTvTotalMoney;
    private RelativeLayout mRlSave, mRlCancel;
    private SearchLayout mSearchLayout;
    private CartAdapter mCartAdapter;
    //    private List<OrderDataProduct> mListData = new ArrayList<>();
    private List<OrderProduct> mListData = new ArrayList<>();
    private ArrayList<DataProduct> mListSearch = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private String mStrOrder;
    private Gson mGson;
    private String mSearch = "";
    private boolean mIsExist;

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void initViews(View view) {
        mStrOrder = PrefManager.getJsonObjectOrderProduct(getActivity());
        mRv = (RecyclerView) view.findViewById(R.id.rv_data);
        mTvProductMoney = (TextView) view.findViewById(R.id.tv_product_money);
        mTvServiceMoney = (TextView) view.findViewById(R.id.tv_service_money);
        mTvTotalMoney = (TextView) view.findViewById(R.id.tv_total_money);
        mRlSave = (RelativeLayout) view.findViewById(R.id.rl_save);
        mRlCancel = (RelativeLayout) view.findViewById(R.id.rl_cancel);
        mSearchLayout = (SearchLayout) view.findViewById(R.id.search_layout);
        mSearchLayout.setResourceForBtnSearch();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());

    }

    @Override
    protected void initListeners() {
        mSearchLayout.setListener(this);
        mRlSave.setOnClickListener(this);
        mRlCancel.setOnClickListener(this);
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        mGson = new Gson();
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(mLinearLayoutManager);

        mCartAdapter = new CartAdapter(getActivity(), mListData, this);
        mRv.setAdapter(mCartAdapter);

        if (!TextUtils.isEmpty(mStrOrder)) {
            OrderLines orderLines = mGson.fromJson(mStrOrder, OrderLines.class);
            if (orderLines != null && orderLines.orderList != null) {
                mListData.clear();
                mListData.addAll(orderLines.orderList);
                mCartAdapter.notifyDataSetChanged();
                if (mListData.size() == 0) {
                    priceWhenNoData();
                } else {
                    updateSalePrice();
                }
            }
        } else {
            priceWhenNoData();
        }
    }

    private void priceWhenNoData() {
        mTvProductMoney.setText(Utils.convertToCurrencyStr(0));
        mTvTotalMoney.setText(Utils.convertToCurrencyStr(0));
        mTvServiceMoney.setText(Utils.convertToCurrencyStr(((MainActivity) getActivity()).getShipFee()));
    }

    @Override
    public void OnActionSearch(String input) {
        if (mListData.size() > 0) {
            for (int i = 0; i < mListData.size(); i++) {
                if (input.equalsIgnoreCase(mListData.get(i).productName)) {
                    mListData.get(i).quantity++;
                    mIsExist = true;
                    break;
                } else {
                    mIsExist = false;
                }
            }
        } else {
            mIsExist = false;
        }
        if (!mIsExist) {
            OrderProduct orderProduct = new OrderProduct(input);
            mListData.add(orderProduct);
        }
        mCartAdapter.notifyDataSetChanged();
        updateSalePrice();
        updateTitle();
    }

    @Override
    public void OnTextChanged(CharSequence charSequence) {
        mSearch = charSequence.toString();
        callApiProducts();
    }

    @Override
    public void OnItemClick(DataProduct item) {
        mSearch = item.productName;
        callApiProducts();
        if (mListData.size() > 0) {
            for (int i = 0; i < mListData.size(); i++) {
                if (item.id.equalsIgnoreCase(mListData.get(i).productId)) {
                    mListData.get(i).quantity++;
                    mIsExist = true;
                    break;
                } else {
                    mIsExist = false;
                }
            }
        } else {
            mIsExist = false;
        }

        if (!mIsExist) {
            mIsExist = true;
            OrderProduct orderProduct = new OrderProduct(item);
            mListData.add(orderProduct);
        }

        mCartAdapter.notifyDataSetChanged();
        updateSalePrice();

        updateTitle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_save:
                if (!Network.isOnline(getActivity())) {
                    DialogUtil.showAlertDialogOneButtonClicked(getActivity(), getString(R.string.title_no_connection)
                            , getString(R.string.message_no_connection), getString(R.string.text_positive), null);
                    return;
                }
                OrderLines orderLines = new OrderLines();
                orderLines.buyerId = ((MainActivity) getActivity()).getUserId();
                orderLines.orderList = mListData;
                String jsonUpload = mGson.toJson(orderLines);
                callApiUpload(jsonUpload);
                break;
            case R.id.rl_cancel:
                if (isAdded()) {
                    DialogUtil.showAlertDialogButtonClicked(getActivity(), getString(R.string.title_remove_cart)
                            , getString(R.string.message_remove_cart), getString(R.string.text_positive), getString(R.string.text_negative), new DialogTwoButtonCallBackListener() {
                                @Override
                                public void onPositiveButtonClick() {
                                    PrefManager.removeJsonObjectOrderProduct(getActivity());
                                    mListData.clear();
                                    mCartAdapter.notifyDataSetChanged();
                                    priceWhenNoData();
                                    ((MainActivity) getActivity()).setTitleForCart(0);
                                }

                                @Override
                                public void onNegativeButtonClick() {

                                }
                            });
                }
                break;
        }
    }

    private void callApiUpload(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            FastNetworking fastNetworking = new FastNetworking(getActivity(), new StringCallBackListener() {
                @Override
                public void onResponse(String string) {
                    Log.d("TUDA", "onResponse = " + string);
                    if (string.equalsIgnoreCase("true")) {
                        DialogUtil.showAlertDialogOrderSuccessOneButtonClicked(getActivity(), null);
                        mListData.clear();
                        mCartAdapter.notifyDataSetChanged();
                        PrefManager.removeJsonObjectOrderProduct(getActivity());
                        priceWhenNoData();
                        ((MainActivity) getActivity()).setTitleForCart(0);
                    }
                }

                @Override
                public void onError(String messageError) {
                }
            });
            fastNetworking.callApiUpload(jsonObject, ((MainActivity) getActivity()).getSecurityToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callApiProducts() {
        if (!Network.isOnline(getActivity())) {
            DialogUtil.showAlertDialogOneButtonClicked(getActivity(), getString(R.string.title_no_connection)
                    , getString(R.string.message_no_connection), getString(R.string.text_positive), null);
            return;
        }
        Products products = new Products();
        products.pageIndex = 1;
        products.pageSize = 25;
        products.isSpecial = false;
        products.productStatus = 1;
        products.productType = 2;
        products.searchText = mSearch;
        final String json = mGson.toJson(products);
        try {
            JSONObject jsonObject = new JSONObject(json);

            FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonObjectCallBackListener() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    ResultProduct resultProduct = mGson.fromJson(jsonObject.toString(), ResultProduct.class);
                    if (resultProduct.data != null && resultProduct.data.size() > 0) {
                        mListSearch.clear();
                        mListSearch.addAll(resultProduct.data);
                        mSearchLayout.setDataForAutoComplete(mListSearch);
                    }
                }

                @Override
                public void onError(String messageError) {
                }
            });
            fastNetworking.callApiProducts(jsonObject, ((MainActivity) getActivity()).getSecurityToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickItemCancel(final OrderProduct product) {
        if (isAdded()) {
            DialogUtil.showAlertDialogButtonClicked(getActivity(), getString(R.string.title_remove_product)
                    , getString(R.string.message_remove_product), getString(R.string.text_positive), getString(R.string.text_negative), new DialogTwoButtonCallBackListener() {
                        @Override
                        public void onPositiveButtonClick() {
                            mListData.remove(product);
                            mCartAdapter.notifyDataSetChanged();
                            updateSalePrice();
                            updateTitle();
                        }

                        @Override
                        public void onNegativeButtonClick() {

                        }
                    });
        }
    }

    private void updateTitle() {
        int quantity = 0;
        if (mListData.size() > 0) {
            quantity += mListData.size();
        }
        ((MainActivity) getActivity()).setTitleForCart(quantity);
    }

    @Override
    public void onInputQuantityChanged() {
        updateSalePrice();
    }

    private void updateSalePrice() {
        int totalPrice = 0;
        int shipFee = ((MainActivity) getActivity()).getShipFee();
        for (OrderProduct item : mListData) {
            totalPrice += item.quantity * item.salePrice.intValue();
        }

        mTvProductMoney.setText(Utils.convertToCurrencyStr(totalPrice));
        mTvServiceMoney.setText(Utils.convertToCurrencyStr(shipFee));

        if (mListData.size() == 0) {
            mTvTotalMoney.setText(Utils.convertToCurrencyStr(0));
        } else {
            mTvTotalMoney.setText(Utils.convertToCurrencyStr(totalPrice + shipFee));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mListData != null && mListData.size() > 0) {
            OrderLines orderLines = new OrderLines();
            orderLines.orderList = mListData;
            String orderList = mGson.toJson(orderLines);
            PrefManager.putJsonObjectOrderProduct(getActivity(), orderList);
        }
    }

}

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
import bonimed.vn.products.DataProduct;
import bonimed.vn.products.ListOrderDataProduct;
import bonimed.vn.products.OrderDataProduct;
import bonimed.vn.products.Products;
import bonimed.vn.products.ProductsAdapter;
import bonimed.vn.products.ResultProduct;
import bonimed.vn.util.DialogUtil;
import bonimed.vn.util.PrefManager;
import bonimed.vn.util.Utils;
import bonimed.vn.widget.EndlessRecyclerViewScrollListener;
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
    private List<DataProduct> mListData = new ArrayList<>();
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
            ListOrderDataProduct listOrderDataProduct = mGson.fromJson(mStrOrder, ListOrderDataProduct.class);
            if (listOrderDataProduct != null && listOrderDataProduct.orderList != null) {
                mListData.clear();
                mListData.addAll(listOrderDataProduct.orderList);
                mCartAdapter.notifyDataSetChanged();
                if (mListData.size() == 0) {
                    priceWhenNoData();
                }
            }
        } else {
            priceWhenNoData();
        }
    }

    private void priceWhenNoData() {
        mTvProductMoney.setText(Utils.convertToCurrencyStr(0));
        mTvTotalMoney.setText(Utils.convertToCurrencyStr(0));
        mTvServiceMoney.setText(Utils.convertToCurrencyStr(0));
    }

    @Override
    public void OnActionSearch(String input) {

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
                if (item.id.equalsIgnoreCase(mListData.get(i).id)) {
                    mListData.get(i).orderQuantity++;
                    mIsExist = true;
                    break;
                } else {
                    mIsExist = false;
                }
            }
        } else {
            mIsExist = true;
        }

        if (!mIsExist) {
            mIsExist = true;
            mListData.add(item);
        }

        mCartAdapter.updateSalePrice();

        updateTitle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_save:
//                OrderLines orderLines = new OrderLines();
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

    private void callApiProducts() {
        final Gson gson = new Gson();
        Products products = new Products();
        products.pageIndex = 1;
        products.pageSize = 25;
        products.isSpecial = false;
        products.productStatus = 1;
        products.productType = 2;
        products.searchText = mSearch;
        final String json = gson.toJson(products);
        try {
            JSONObject jsonObject = new JSONObject(json);

            FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonObjectCallBackListener() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    ResultProduct resultProduct = gson.fromJson(jsonObject.toString(), ResultProduct.class);
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
    public void onClickItemCancel(final DataProduct product) {
        if (isAdded()) {
            DialogUtil.showAlertDialogButtonClicked(getActivity(), getString(R.string.title_remove_product)
                    , getString(R.string.message_remove_product), getString(R.string.text_positive), getString(R.string.text_negative), new DialogTwoButtonCallBackListener() {
                        @Override
                        public void onPositiveButtonClick() {
                            mListData.remove(product);
                            mCartAdapter.updateSalePrice();
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
            for (int i = 0; i < mListData.size(); i++) {
                quantity += mListData.get(i).orderQuantity;
            }
        }
        ((MainActivity) getActivity()).setTitleForCart(quantity);
    }

    @Override
    public void onInputQuantityChanged(int totalPrice) {
        mTvProductMoney.setText(Utils.convertToCurrencyStr(totalPrice));
        mTvTotalMoney.setText(Utils.convertToCurrencyStr(totalPrice + 20000));
        updateTitle();
    }

    @Override
    public void onStop() {
        super.onStop();
        ListOrderDataProduct listOrderDataProduct = new ListOrderDataProduct();
        listOrderDataProduct.orderList = mListData;
        String orderList = mGson.toJson(listOrderDataProduct, ListOrderDataProduct.class);
        PrefManager.putJsonObjectOrderProduct(getActivity(), orderList);
    }

}

package bonimed.vn.products;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import bonimed.vn.cart.OrderLines;
import bonimed.vn.cart.OrderProduct;
import bonimed.vn.listener.JsonObjectCallBackListener;
import bonimed.vn.util.PrefManager;
import bonimed.vn.widget.EndlessRecyclerViewScrollListener;
import bonimed.vn.widget.SearchLayout;

/**
 * Created by mac on 10/24/17.
 */

public class ProductsFragment extends BaseFragment implements ProductsAdapter.ItemClickCallBackListener, SearchLayout.SearchCallBackListener {

    private SearchLayout mSearchLayout;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRv;
    private TextView mTvTotal;
    private ProductsAdapter mProductsAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private List<DataProduct> mListData = new ArrayList<>();
    private ArrayList<DataProduct> mListSearch = new ArrayList<>();
    //    private List<OrderDataProduct> mListOrder = new ArrayList<>();
    private List<OrderProduct> mListOrder = new ArrayList<>();
    private int mCurrentPage = 1;
    private int mTotalProduct;
    private String mSearch = "";
    private Gson mGson;

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_products;
    }

    @Override
    protected void initViews(View view) {
        mSearchLayout = (SearchLayout) view.findViewById(R.id.search_layout);
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mRv = (RecyclerView) view.findViewById(R.id.rv_data);
        mTvTotal = (TextView) view.findViewById(R.id.tv_total);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
    }

    @Override
    protected void initListeners() {
        mScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                callApiProducts();
            }
        };

        mRv.addOnScrollListener(mScrollListener);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mScrollListener.resetState();
                mCurrentPage = 1;
                mSearch = "";
                mListData.clear();
                callApiProducts();

            }
        });
        mSearchLayout.setListener(this);
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        mGson = new Gson();
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(mLinearLayoutManager);
        mProductsAdapter = new ProductsAdapter(getActivity(), mListData, this);
        mRv.setAdapter(mProductsAdapter);

        String orderProduct = PrefManager.getJsonObjectOrderProduct(getActivity());
        if (!TextUtils.isEmpty(orderProduct)) {
            OrderLines orderLines = mGson.fromJson(orderProduct, OrderLines.class);
            if (orderLines != null && orderLines.orderList != null) {
                mListOrder.clear();
                mListOrder.addAll(orderLines.orderList);
            }
        }

        callApiProducts();
    }

    private void callApiProducts() {
        final Gson gson = new Gson();
        Products products = new Products();
        products.pageIndex = mCurrentPage;
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
                    mSwipe.setRefreshing(false);
                    ResultProduct resultProduct = gson.fromJson(jsonObject.toString(), ResultProduct.class);
                    mTvTotal.setText(getString(R.string.text_total) + " " + resultProduct.pagination.rowCount + " sản phẩm");

                    if (resultProduct.data != null && resultProduct.data.size() > 0) {
                        if (mCurrentPage <= resultProduct.pagination.pageCount) {
                            mCurrentPage++;
                            for (DataProduct item : resultProduct.data) {
                                for (OrderProduct childItem : mListOrder) {
                                    if (item.id.equalsIgnoreCase(childItem.productId)) {
                                        item.isChecked = true;
                                    }
                                }
                                mListData.add(item);
                            }
                            mProductsAdapter.notifyDataSetChanged();
                            mListSearch.clear();
                            mListSearch.addAll(resultProduct.data);
                            mSearchLayout.setDataForAutoComplete(mListSearch);
                        }
                    }
                }

                @Override
                public void onError(String messageError) {
                    mSwipe.setRefreshing(false);
                }
            });
            fastNetworking.callApiProducts(jsonObject, ((MainActivity) getActivity()).getSecurityToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickPurchase(DataProduct item) {

//        OrderDataProduct itemData = null;
//        if (mListOrder.size() > 0) {
//            for (int i = 0; i < mListOrder.size(); i++) {
//                if (item.id.equalsIgnoreCase(mListOrder.get(i).dataProduct.id)) {
//                    mListOrder.get(i).orderQuantity++;
//                    mIsExist = true;
//                    break;
//                } else {
//                    mIsExist = false;
//                }
//            }
//        } else {
//            mIsExist = true;
//            itemData = new OrderDataProduct();
//            itemData.orderQuantity = 1;
//            itemData.dataProduct = item;
//            mListOrder.add(itemData);
//        }
//        if (!mIsExist) {
//            itemData = new OrderDataProduct();
//            itemData.orderQuantity = 1;
//            itemData.dataProduct = item;
//            mListOrder.add(itemData);
//        }
//        ListOrderDataProduct listOrderDataProduct = new ListOrderDataProduct();
//        listOrderDataProduct.orderList = mListOrder;
//        String orderList = mGson.toJson(listOrderDataProduct, ListOrderDataProduct.class);
//        PrefManager.putJsonObjectOrderProduct(getActivity(), orderList);
        if (mListOrder.size() > 0) {
            if (!item.isChecked) {
                Toast.makeText(getActivity(), getString(R.string.toast_remove_product), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < mListOrder.size(); i++) {
                    if (item.id.equalsIgnoreCase(mListOrder.get(i).productId)) {
                        mListOrder.remove(i);
                    }
                }
            } else {
                Toast.makeText(getActivity(), getString(R.string.toast_order_product), Toast.LENGTH_SHORT).show();
                OrderProduct orderProduct = new OrderProduct(item);
                mListOrder.add(0,orderProduct);
            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.toast_order_product), Toast.LENGTH_SHORT).show();
            OrderProduct orderProduct = new OrderProduct(item);
            mListOrder.add(0,orderProduct);
        }
        OrderLines orderLines = new OrderLines();
        orderLines.orderList = mListOrder;
        String orderList = mGson.toJson(orderLines);
        PrefManager.putJsonObjectOrderProduct(getActivity(), orderList);

    }

    @Override
    public void onClickDetail(DataProduct item) {

    }

    @Override
    public void OnActionSearch(String input) {
        mSearch = input;
        mListData.clear();
        mCurrentPage = 1;
        callApiProducts();
    }

    @Override
    public void OnTextChanged(CharSequence charSequence) {
        mSearch = charSequence.toString();
        mCurrentPage = 1;
        mListData.clear();
        callApiProducts();
    }

    @Override
    public void OnItemClick(DataProduct item) {
        mSearch = item.productName;
        mListData.clear();
        mCurrentPage = 1;
        callApiProducts();
    }
}

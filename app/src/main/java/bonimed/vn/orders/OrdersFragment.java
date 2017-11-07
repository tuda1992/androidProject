package bonimed.vn.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bonimed.vn.MainActivity;
import bonimed.vn.R;
import bonimed.vn.api.FastNetworking;
import bonimed.vn.base.BaseActivity;
import bonimed.vn.base.BaseFragment;
import bonimed.vn.cart.OrderLines;
import bonimed.vn.cart.OrderProduct;
import bonimed.vn.listener.JsonObjectCallBackListener;
import bonimed.vn.products.DataProduct;
import bonimed.vn.products.ProductsAdapter;
import bonimed.vn.products.ResultProduct;
import bonimed.vn.util.Constants;
import bonimed.vn.util.DialogUtil;
import bonimed.vn.util.Network;
import bonimed.vn.util.PrefManager;
import bonimed.vn.util.ProgressDialogUtils;
import bonimed.vn.widget.EndlessRecyclerViewScrollListener;
import bonimed.vn.widget.SearchLayout;

/**
 * Created by mac on 10/24/17.
 */

public class OrdersFragment extends BaseFragment implements SearchLayout.SearchCallBackListener, OrdersAdapter.ItemClickCallBackListener {

    private SearchLayout mSearchLayout;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRv;
    private OrdersAdapter mOrdersAdapter;
    private Gson mGson;
    private LinearLayoutManager mLinearLayoutManager;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private int mCurrentPage = 1;
    private List<OrdersList> mListData = new ArrayList<>();
    private ProgressDialogUtils mProgress;

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_orders;
    }

    @Override
    protected void initViews(View view) {
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mRv = (RecyclerView) view.findViewById(R.id.rv_data);
        mSearchLayout = (SearchLayout) view.findViewById(R.id.search_layout);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mSearchLayout.setResourceForOrderFragment();
        mProgress = new ProgressDialogUtils(getActivity());
    }

    @Override
    protected void initListeners() {
        mSearchLayout.setListener(this);
        mScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                callApi(false);
            }
        };

        mRv.addOnScrollListener(mScrollListener);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mScrollListener.resetState();
                mListData.clear();
                mCurrentPage = 1;
                callApi(false);
            }
        });
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        mGson = new Gson();
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(mLinearLayoutManager);
        mOrdersAdapter = new OrdersAdapter(getActivity(), mListData, this);
        mRv.setAdapter(mOrdersAdapter);
        callApi(true);
    }

    @Override
    public void OnActionSearch(String input) {

    }

    @Override
    public void OnTextChanged(CharSequence charSequence) {

    }

    @Override
    public void OnItemClick(DataProduct item) {

    }

    @Override
    public void onClickItemDetail(OrdersList item) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ORDER_ID, item.id);
        ((MainActivity) getActivity()).startActivityAnim(DetailOrderActivity.class, bundle);
    }

    private void callApi(boolean isShowProgress) {
        if (!Network.isOnline(getActivity())) {
            DialogUtil.showAlertDialogOneButtonClicked(getActivity(), getString(R.string.title_no_connection)
                    , getString(R.string.message_no_connection), getString(R.string.text_positive), null);
            return;
        }

        if (isShowProgress) {
            mProgress.showDialog();
        }

        OrdersListPost ordersListPost = new OrdersListPost();
        ordersListPost.startTime = -1;
        ordersListPost.endTime = -1;
        ordersListPost.buyerId = "";
        ordersListPost.assistantId = "";
        ordersListPost.moderatorId = "";
        ordersListPost.repId = "";
        ordersListPost.pageIndex = mCurrentPage;
        ordersListPost.pageSize = 20;
        String json = mGson.toJson(ordersListPost);

        try {
            JSONObject jsonObject = new JSONObject(json);
            FastNetworking fastNetworking = new FastNetworking(getActivity(), new JsonObjectCallBackListener() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    mSwipe.setRefreshing(false);
                    mProgress.hideDialog();
                    ResultOrdersList resultOrdersList = mGson.fromJson(jsonObject.toString(), ResultOrdersList.class);

                    if (resultOrdersList.data != null && resultOrdersList.data.size() > 0) {
                        if (mCurrentPage <= resultOrdersList.pagination.pageCount) {
                            mCurrentPage++;
                            mListData.addAll(resultOrdersList.data);
                            mOrdersAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onError(String messageError) {
                    mSwipe.setRefreshing(false);
                    mProgress.hideDialog();
                }
            });
            fastNetworking.callApiOrderList(jsonObject, ((MainActivity) getActivity()).getSecurityToken());
        } catch (JSONException e) {
            mSwipe.setRefreshing(false);
            mProgress.hideDialog();
            e.printStackTrace();
        }
    }
}

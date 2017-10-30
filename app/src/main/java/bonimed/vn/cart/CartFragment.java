package bonimed.vn.cart;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bonimed.vn.R;
import bonimed.vn.base.BaseFragment;
import bonimed.vn.products.ProductsAdapter;
import bonimed.vn.widget.EndlessRecyclerViewScrollListener;
import bonimed.vn.widget.SearchLayout;

/**
 * Created by mac on 10/24/17.
 */

public class CartFragment extends BaseFragment implements SearchLayout.SearchCallBackListener, View.OnClickListener,CartAdapter.ItemClickCallBackListener {

    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRv;
    private TextView mTvProductMoney, mTvServiceMoney, mTvTotalMoney;
    private RelativeLayout mRlSave, mRlCancel;
    private SearchLayout mSearchLayout;
    private CartAdapter mCartAdapter;
    private List<String> mListData;
    private LinearLayoutManager mLinearLayoutManager;
    private EndlessRecyclerViewScrollListener mScrollListener;

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void initViews(View view) {
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
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
        mScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page) {

            }
        };

        mRv.addOnScrollListener(mScrollListener);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mScrollListener.resetState();
                mSwipe.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(mLinearLayoutManager);
        mListData = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            mListData.add("" + i);
        }

        mCartAdapter = new CartAdapter(getActivity(), mListData, this);
        mRv.setAdapter(mCartAdapter);
    }

    @Override
    public void OnActionSearch(String input) {

    }

    @Override
    public void OnTextChanged(CharSequence charSequence) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_save:
                break;
            case R.id.rl_cancel:
                break;
        }
    }

    @Override
    public void onClickItemCancel(String input) {

    }
}

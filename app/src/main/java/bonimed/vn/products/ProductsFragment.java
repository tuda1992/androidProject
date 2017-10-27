package bonimed.vn.products;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bonimed.vn.R;
import bonimed.vn.base.BaseFragment;
import bonimed.vn.widget.EndlessRecyclerViewScrollListener;
import bonimed.vn.widget.SearchLayout;

/**
 * Created by mac on 10/24/17.
 */

public class ProductsFragment extends BaseFragment implements ProductsAdapter.ItemClickCallBackListener {

    private SearchLayout mSearchLayout;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRv;
    private TextView mTvTotal;
    private ProductsAdapter mProductsAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private List<String> mListData;
    private int mCurrentPage;

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

        mProductsAdapter = new ProductsAdapter(getActivity(), mListData, this);
        mRv.setAdapter(mProductsAdapter);
    }


    @Override
    public void onClickPurchase(String item) {

    }

    @Override
    public void onClickDetail(String item) {

    }
}

package bonimed.vn.cart;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bonimed.vn.R;
import bonimed.vn.base.BaseFragment;
import bonimed.vn.widget.SearchLayout;

/**
 * Created by mac on 10/24/17.
 */

public class CartFragment extends BaseFragment implements SearchLayout.SearchCallBackListener, View.OnClickListener {

    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRv;
    private TextView mTvProductMoney, mTvServiceMoney, mTvTotalMoney;
    private RelativeLayout mRlSave, mRlCancel;
    private SearchLayout mSearchLayout;

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
    }

    @Override
    protected void initListeners() {
        mSearchLayout.setListener(this);
        mRlSave.setOnClickListener(this);
        mRlCancel.setOnClickListener(this);
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressFragment() {
        super.onBackPressFragment();
        finishFragment();
    }

    @Override
    public void OnActionSearch(String input) {

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
}

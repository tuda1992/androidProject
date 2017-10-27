package bonimed.vn.orders;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import bonimed.vn.R;
import bonimed.vn.base.BaseActivity;
import bonimed.vn.base.BaseFragment;
import bonimed.vn.widget.SearchLayout;

/**
 * Created by mac on 10/24/17.
 */

public class OrdersFragment extends BaseFragment implements SearchLayout.SearchCallBackListener{

    private SearchLayout mSearchLayout;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRv;

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_orders;
    }

    @Override
    protected void initViews(View view) {
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mRv = (RecyclerView) view.findViewById(R.id.rv_data);
        mSearchLayout = (SearchLayout) view.findViewById(R.id.search_layout);
    }

    @Override
    protected void initListeners() {
        mSearchLayout.setListener(this);
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {

    }


    @Override
    public void OnActionSearch(String input) {

    }
}

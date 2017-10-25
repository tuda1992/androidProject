package bonimed.vn;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import bonimed.vn.base.BaseActivity;
import bonimed.vn.cart.CartFragment;
import bonimed.vn.navigationdrawer.FragmentDrawer;
import bonimed.vn.orders.OrdersFragment;
import bonimed.vn.products.ProductsFragment;
import bonimed.vn.util.Constants;

public class MainActivity extends BaseActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer mDrawerFragment;
    private int mCurrentTab = -1;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        displayView(0);
    }

    @Override
    protected void initListeners() {
        mDrawerFragment.setDrawerListener(this);
    }

    @Override
    protected void initDatas(Bundle saveInstanceStatte) {
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        if (mCurrentTab == position) {
            return;
        }
        mCurrentTab = position;
        Fragment fragment = null;
        String nameFragment = "";
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new ProductsFragment();
                nameFragment = Constants.PRODUCTS_FRAGMENT;
                title = getString(R.string.title_products);
                break;
            case 1:
                fragment = new CartFragment();
                nameFragment = Constants.CART_FRAGMENT;
                title = getString(R.string.title_cart);
                break;
            case 2:
                fragment = new OrdersFragment();
                nameFragment = Constants.ORDERS_FRAGMENT;
                title = getString(R.string.title_orders);
                break;
            default:
                break;
        }

        if (fragment != null) {
            // set the toolbar title
            startFragment(fragment, nameFragment);
            getSupportActionBar().setTitle(title);
        }
    }

}

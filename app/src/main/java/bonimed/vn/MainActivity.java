package bonimed.vn;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;

import bonimed.vn.base.BaseActivity;
import bonimed.vn.cart.CartFragment;
import bonimed.vn.cart.OrderLines;
import bonimed.vn.login.UserLogin;
import bonimed.vn.navigationdrawer.FragmentDrawer;
import bonimed.vn.orders.OrdersFragment;
import bonimed.vn.products.ProductsFragment;
import bonimed.vn.util.Constants;
import bonimed.vn.util.PrefManager;

public class MainActivity extends BaseActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer mDrawerFragment;
    private int mCurrentTab = -1;
    private Menu mMenu;
    private UserLogin mUserLogin;

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
        String jsonUserLogin = PrefManager.getJsonObjectUserLogin(this);
        if (!TextUtils.isEmpty(jsonUserLogin)) {
            Gson gson = new Gson();
            mUserLogin = gson.fromJson(jsonUserLogin, UserLogin.class);
            mDrawerFragment.setNameUser(mUserLogin.fullName);
        }
    }

    public String getSecurityToken() {
        return mUserLogin == null ? "" : mUserLogin.securityToken;
    }
    public String getUserId() {
        return mUserLogin == null ? "" : mUserLogin.id;
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
                setVisibleActionSearch(true);
                fragment = new ProductsFragment();
                nameFragment = Constants.PRODUCTS_FRAGMENT;
                title = getString(R.string.title_products);
                break;
            case 1:
                int quantity = 0;
                String orderProduct = PrefManager.getJsonObjectOrderProduct(this);
                if (!TextUtils.isEmpty(orderProduct)) {
                    OrderLines orderLines = new Gson().fromJson(orderProduct, OrderLines.class);
                    if (orderLines != null && orderLines.orderList != null) {
                        for (int i = 0; i < orderLines.orderList.size(); i++) {
                            quantity += orderLines.orderList.get(i).quantity;
                        }
                    }
                }
                setVisibleActionSearch(false);
                fragment = new CartFragment();
                nameFragment = Constants.CART_FRAGMENT;
                setTitleForCart(quantity);
                break;
            case 2:
                setVisibleActionSearch(false);
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
            if (position != 1)
                getSupportActionBar().setTitle(title);
        }
    }

    public void setTitleForCart(int quantity) {
      if (quantity <= 0) {
            getSupportActionBar().setTitle(getString(R.string.title_cart));
        } else {
            getSupportActionBar().setTitle(getString(R.string.title_cart) + "( " + quantity + " sản phẩm )");
        }
    }

    private void setVisibleActionSearch(boolean isShow) {
        if (mMenu != null) {
            MenuItem actionCart = mMenu.findItem(R.id.action_cart);
            actionCart.setVisible(isShow);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (mCurrentTab == 0) {
            setVisibleActionSearch(true);
        } else {
            setVisibleActionSearch(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            displayView(1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }


}

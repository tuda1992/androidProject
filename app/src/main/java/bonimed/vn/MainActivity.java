package bonimed.vn;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import bonimed.vn.base.BaseActivity;
import bonimed.vn.navigationdrawer.FragmentDrawer;

public class MainActivity extends BaseActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer mDrawerFragment;

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
        
    }
}

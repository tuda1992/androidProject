package bonimed.vn.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import bonimed.vn.R;

/**
 * Created by mac on 10/24/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());
        initViews();
        initListeners();
        initDatas(savedInstanceState);
    }

    protected abstract int getLayoutView();

    protected abstract void initViews();
    protected abstract void initListeners();
    protected abstract void initDatas(Bundle saveInstanceStatte);

    protected void startFragment(Fragment fragment,String tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

}

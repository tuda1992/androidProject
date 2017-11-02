package bonimed.vn.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

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

    protected void startFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void finishFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
    }

    protected void startActivityAnim(Class activity, Bundle b) {
        Intent i = new Intent(this, activity);
        if (b != null) {
            i.putExtras(b);
        }
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    protected void finishActivityAnim() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }


}

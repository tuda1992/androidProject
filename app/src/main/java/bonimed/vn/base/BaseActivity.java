package bonimed.vn.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mac on 10/24/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());
        initViews();
        initDatas(savedInstanceState);
    }

    protected abstract int getLayoutView();

    protected abstract void initViews();
    protected abstract void initListeners();
    protected abstract void initDatas(Bundle saveInstanceStatte);

}

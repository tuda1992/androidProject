package bonimed.vn.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mac on 10/24/17.
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutView(),container,false);
        initViews(view);
        initListeners();
        initDatas(savedInstanceState);
        return view;
    }

    protected abstract int getLayoutView();
    protected abstract void initViews(View view);
    protected abstract void initListeners();
    protected abstract void initDatas(Bundle savedInstanceState);

}

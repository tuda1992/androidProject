package bonimed.vn.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * Created by acv-tuda on 7/11/2017.
 */

public abstract class BaseCustomLayout extends RelativeLayout {

    public BaseCustomLayout(Context context) {
        super(context);
        setLayout();
        initCompoundView();
        initData();
        initListener();
    }

    public BaseCustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        setLayout();
        initCompoundView();
        initData();
        initListener();
    }

    protected int[] getStyleableId() {
        return null;
    }

    protected void initDataFromStyleable(TypedArray a) {
    }

    protected abstract int getLayoutId();

    protected abstract void initCompoundView();

    protected abstract void initData();

    protected abstract void initListener();

    private void init(AttributeSet attrs) {
        if (getStyleableId() != null && getStyleableId().length > 0) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, getStyleableId(), 0, 0);
            initDataFromStyleable(a);
            a.recycle();
        }
    }

    private void setLayout() {
        if (getLayoutId() != 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(getLayoutId(), this, true);
        }
    }

}

package bonimed.vn.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import bonimed.vn.R;
import bonimed.vn.adapter.AutoCompleteAdapter;
import bonimed.vn.base.BaseCustomLayout;

/**
 * Created by acv on 10/25/17.
 */

public class SearchLayout extends BaseCustomLayout implements View.OnClickListener {

    private AutoCompleteTextView mEdtInput;
    private RelativeLayout mRlSearch;
    private SearchCallBackListener mSearchCallBackListener;
    private AutoCompleteAdapter mAutoCompleteAdapter;
    private Context mContext;
    private ImageView mIvSearch;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_search:
                String input = mEdtInput.getText().toString().trim();
                if (mSearchCallBackListener != null)
                    mSearchCallBackListener.OnActionSearch(input);
                break;
        }
    }

    public interface SearchCallBackListener {
        void OnActionSearch(String input);
    }

    public void setListener(SearchCallBackListener searchCallBackListener) {
        this.mSearchCallBackListener = searchCallBackListener;
    }

    public SearchLayout(Context context) {
        super(context);
        this.mContext = context;
    }

    public SearchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.search_layout;
    }

    @Override
    protected void initCompoundView() {
        mEdtInput = (AutoCompleteTextView) findViewById(R.id.edt_input);
        mRlSearch = (RelativeLayout) findViewById(R.id.rl_search);
        mIvSearch = (ImageView) findViewById(R.id.iv_search);
    }

    @Override
    protected void initData() {
        mEdtInput.setThreshold(2);
    }

    public void setDataForAutoComplete(List<String> listData) {
        mAutoCompleteAdapter = new AutoCompleteAdapter(mContext, listData);
        mEdtInput.setAdapter(mAutoCompleteAdapter);
    }

    @Override
    protected void initListener() {
        mRlSearch.setOnClickListener(this);
    }

    public void setResourceForBtnSearch() {
        mIvSearch.setImageResource(R.drawable.ic_cart);
    }
}

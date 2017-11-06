package bonimed.vn.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import bonimed.vn.R;
import bonimed.vn.adapter.AutoCompleteAdapter;
import bonimed.vn.base.BaseCustomLayout;
import bonimed.vn.products.DataProduct;

/**
 * Created by acv on 10/25/17.
 */

public class SearchLayout extends BaseCustomLayout implements View.OnClickListener, AutoCompleteAdapter.ItemClicKCallBackListener {

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

    @Override
    public void onItemClick(DataProduct item) {
        if (mSearchCallBackListener != null) {
            mEdtInput.setText(item.productName);
            mEdtInput.setSelection(mEdtInput.getText().length());
            mEdtInput.dismissDropDown();
            mSearchCallBackListener.OnItemClick(item);
        }
    }

    public interface SearchCallBackListener {
        void OnActionSearch(String input);

        void OnTextChanged(CharSequence charSequence);

        void OnItemClick(DataProduct item);
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
        mEdtInput.setThreshold(1);
    }

    public void setDataForAutoComplete(ArrayList<DataProduct> listData) {
        mAutoCompleteAdapter = new AutoCompleteAdapter(mContext, listData);
        mEdtInput.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.setListener(this);
    }

    @Override
    protected void initListener() {
        mRlSearch.setOnClickListener(this);
        mEdtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mSearchCallBackListener != null) {
                    mSearchCallBackListener.OnTextChanged(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setResourceForBtnSearch() {
        mIvSearch.setImageResource(R.drawable.ic_cart);
        mEdtInput.setHint(getResources().getString(R.string.input_text_hint_cart));
    }


    public void setResourceForOrderFragment() {
        mEdtInput.setHint(getResources().getString(R.string.input_text_hint_order));
    }
}

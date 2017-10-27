package bonimed.vn.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import bonimed.vn.R;

/**
 * Created by acv on 10/27/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context mContext;
    private ItemClickCallBackListener mItemClickCallBackListener;
    private List<String> mResultData;
    private String[] mDataset = new String[20];

    public interface ItemClickCallBackListener {
        void onClickItemCancel(String input);
    }


    public CartAdapter(Context context, List<String> resultData, ItemClickCallBackListener listener) {
        this.mContext = context;
        this.mItemClickCallBackListener = listener;
        this.mResultData = resultData;
    }

    @Override
    public int getItemCount() {
        return mResultData != null ? mResultData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout mRlCancel;
        private ImageView mIvProduct;
        private TextView mTvProductName, mTvProductUnit, mTvPrice, mTvTotalPrice;
        private EditText mEdtNumber;
        private MyCustomEditTextListener mMyCustomEditTextListener;

        public ViewHolder(View v) {
            super(v);
            mRlCancel = (RelativeLayout) v.findViewById(R.id.rl_cancel);
            mIvProduct = (ImageView) v.findViewById(R.id.iv_product);
            mTvProductName = (TextView) v.findViewById(R.id.tv_product_name);
            mTvProductUnit = (TextView) v.findViewById(R.id.tv_product_unit);
            mTvPrice = (TextView) v.findViewById(R.id.tv_price);
            mTvTotalPrice = (TextView) v.findViewById(R.id.tv_total_price);
            mEdtNumber = (EditText) v.findViewById(R.id.edt_input_number);
            mRlCancel.setOnClickListener(this);
            mMyCustomEditTextListener = new MyCustomEditTextListener();
            mEdtNumber.addTextChangedListener(mMyCustomEditTextListener);
        }

        public void setData(String item, int position) {
            mTvProductName.setText(item);
            mMyCustomEditTextListener.updatePosition(position);
            mEdtNumber.setText(mDataset[position]);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_cancel:
                    if (mItemClickCallBackListener != null) {
                        mItemClickCallBackListener.onClickItemCancel("");
                    }
                    break;

            }
        }
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mResultData.get(position), position);
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mDataset[position] = charSequence.toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

}
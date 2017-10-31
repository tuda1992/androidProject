package bonimed.vn.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bonimed.vn.R;
import bonimed.vn.products.OrderDataProduct;
import bonimed.vn.util.Constants;
import bonimed.vn.util.Utils;

/**
 * Created by acv on 10/27/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context mContext;
    private ItemClickCallBackListener mItemClickCallBackListener;
    private List<OrderDataProduct> mResultData;
//    private String[] mDataset = new String[20];

    public interface ItemClickCallBackListener {
        void onClickItemCancel(String input);
    }


    public CartAdapter(Context context, List<OrderDataProduct> resultData, ItemClickCallBackListener listener) {
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

        public void setData(OrderDataProduct item, int position) {
            mTvProductName.setText(item.dataProduct.productName);
            Picasso.with(mContext).load(Constants.URL_BONIMED + item.dataProduct.imageFullPath).error(R.drawable.ic_updating).into(mIvProduct);
            mTvProductUnit.setText(item.dataProduct.description);
            mMyCustomEditTextListener.updatePosition(position, mTvTotalPrice, item.dataProduct.salePrice.intValue());
            mEdtNumber.setText(item.orderQuantity.intValue() + "");
            mTvTotalPrice.setText(Utils.convertToCurrencyStr(item.orderQuantity.intValue() * item.dataProduct.salePrice.intValue()));
            mTvPrice.setText(Utils.convertToCurrencyStr(item.dataProduct.salePrice));
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
        private TextView mTvTotal;
        private int mPrice;

        public void updatePosition(int position, TextView tvTotal, int price) {
            this.position = position;
            this.mTvTotal = tvTotal;
            this.mPrice = price;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!TextUtils.isEmpty(charSequence.toString())) {
                mResultData.get(position).orderQuantity = Integer.valueOf(charSequence.toString());
                mTvTotal.setText(Utils.convertToCurrencyStr(mResultData.get(position).orderQuantity * mPrice));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

}
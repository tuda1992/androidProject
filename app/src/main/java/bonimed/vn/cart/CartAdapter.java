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
import bonimed.vn.products.DataProduct;
import bonimed.vn.products.OrderDataProduct;
import bonimed.vn.util.Constants;
import bonimed.vn.util.Utils;

/**
 * Created by acv on 10/27/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context mContext;
    private ItemClickCallBackListener mItemClickCallBackListener;
    //    private List<OrderDataProduct> mResultData;
    private List<OrderProduct> mResultData;

    public interface ItemClickCallBackListener {
        void onClickItemCancel(OrderProduct item);

        void onInputQuantityChanged();
    }

    public CartAdapter(Context context, List<OrderProduct> resultData, ItemClickCallBackListener listener) {
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
        private ImageView mIvProduct, mIvAdd, mIvRemove;
        ;
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
            mIvAdd = (ImageView) v.findViewById(R.id.iv_add);
            mIvRemove = (ImageView) v.findViewById(R.id.iv_remove);
            mEdtNumber = (EditText) v.findViewById(R.id.edt_input_number);
            mRlCancel.setOnClickListener(this);
            mMyCustomEditTextListener = new MyCustomEditTextListener();
            mEdtNumber.addTextChangedListener(mMyCustomEditTextListener);
        }

        public void setData(final OrderProduct item, final int position) {
            mTvProductName.setText(item.productName);
            Picasso.with(mContext).load(Constants.URL_BONIMED + item.imageFullPath).error(R.drawable.ic_updating).into(mIvProduct);
            mTvProductUnit.setText(item.description);
            mMyCustomEditTextListener.updatePosition(position);
            mEdtNumber.setText(item.quantity.intValue() + "");
            if (item.salePrice.intValue() != 0) {
                mTvTotalPrice.setText(Utils.convertToCurrencyStr(item.quantity.intValue() * item.salePrice.intValue()));
                mTvPrice.setText("Đơn giá : " + Utils.convertToCurrencyStr(item.salePrice.intValue()));
            }

            mEdtNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        if (item.quota.intValue() != 0){
                            if (item.quantity.intValue() >= item.quota.intValue()) {
                                mEdtNumber.setText(item.quota.intValue() + "");
                                if (item.salePrice != null) {
                                    mTvTotalPrice.setText(Utils.convertToCurrencyStr(item.quota.intValue() * item.salePrice.intValue()));
                                }
                            } else {
                                mEdtNumber.setText(item.quantity.intValue() + "");
                                if (item.salePrice != null) {
                                    mTvTotalPrice.setText(Utils.convertToCurrencyStr(item.quantity.intValue() * item.salePrice.intValue()));
                                }
                            }
                        }else {
                            mEdtNumber.setText(item.quantity.intValue() + "");
                        }
                        if (mItemClickCallBackListener != null)
                            mItemClickCallBackListener.onInputQuantityChanged();
                    }
                }
            });

            mIvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mResultData.get(position).quota.intValue() != 0) {
                        if (mResultData.get(position).quantity < mResultData.get(position).quota) {
                            mResultData.get(position).quantity++;
                            mEdtNumber.setText(mResultData.get(position).quantity.intValue() + "");
                            if (mResultData.get(position).salePrice != null) {
                                mTvTotalPrice.setText(Utils.convertToCurrencyStr(mResultData.get(position).quantity.intValue() * mResultData.get(position).salePrice.intValue()));
                            }
                        }
                    } else {
                        mResultData.get(position).quantity++;
                        mEdtNumber.setText(mResultData.get(position).quantity.intValue() + "");
                    }
                    if (mItemClickCallBackListener != null)
                        mItemClickCallBackListener.onInputQuantityChanged();
                }
            });

            mIvRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mResultData.get(position).quantity >= 2) {
                        mResultData.get(position).quantity--;
                        mEdtNumber.setText(mResultData.get(position).quantity.intValue() + "");
                        if (mResultData.get(position).salePrice.intValue() != 0) {
                            mTvTotalPrice.setText(Utils.convertToCurrencyStr(mResultData.get(position).quantity.intValue() * mResultData.get(position).salePrice.intValue()));
                        }
                        if (mItemClickCallBackListener != null)
                            mItemClickCallBackListener.onInputQuantityChanged();
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_cancel:
                    if (mItemClickCallBackListener != null) {
                        mItemClickCallBackListener.onClickItemCancel(mResultData.get(getAdapterPosition()));
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
            if (!TextUtils.isEmpty(charSequence.toString())) {
                mResultData.get(position).quantity = Integer.valueOf(charSequence.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
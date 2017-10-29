package bonimed.vn.orders;

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
import bonimed.vn.cart.CartAdapter;

/**
 * Created by acv on 10/27/17.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private Context mContext;
    private ItemClickCallBackListener mItemClickCallBackListener;
    private List<String> mResultData;
    private String[] mDataset = new String[20];

    public interface ItemClickCallBackListener {
        void onClickItemCancel(String input);
    }


    public OrdersAdapter(Context context, List<String> resultData, ItemClickCallBackListener listener) {
        this.mContext = context;
        this.mItemClickCallBackListener = listener;
        this.mResultData = resultData;
    }

    @Override
    public int getItemCount() {
        return mResultData != null ? mResultData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTvBillCode,mTvBillDate,mTvBillMoney,mTvBillDetail;

        public ViewHolder(View v) {
            super(v);
            mTvBillCode = (TextView) v.findViewById(R.id.tv_bill_code);
            mTvBillDate = (TextView) v.findViewById(R.id.tv_bill_date);
            mTvBillMoney = (TextView) v.findViewById(R.id.tv_bill_money);
            mTvBillDetail = (TextView) v.findViewById(R.id.tv_bill_detail);
        }

        public void setData(String item, int position) {

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
    public OrdersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_order, parent, false);
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

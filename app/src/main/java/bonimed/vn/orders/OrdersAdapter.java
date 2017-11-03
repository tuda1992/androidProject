package bonimed.vn.orders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bonimed.vn.R;
import bonimed.vn.util.Utils;

/**
 * Created by acv on 10/27/17.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private Context mContext;
    private ItemClickCallBackListener mItemClickCallBackListener;
    private List<OrdersList> mResultData;

    public interface ItemClickCallBackListener {
        void onClickItemDetail(OrdersList item);
    }

    public OrdersAdapter(Context context, List<OrdersList> resultData, ItemClickCallBackListener listener) {
        this.mContext = context;
        this.mItemClickCallBackListener = listener;
        this.mResultData = resultData;
    }

    @Override
    public int getItemCount() {
        return mResultData != null ? mResultData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTvBillCode, mTvBillDate, mTvBillMoney, mTvBillDetail;

        public ViewHolder(View v) {
            super(v);
            mTvBillCode = (TextView) v.findViewById(R.id.tv_bill_code);
            mTvBillDate = (TextView) v.findViewById(R.id.tv_bill_date);
            mTvBillMoney = (TextView) v.findViewById(R.id.tv_bill_money);
            mTvBillDetail = (TextView) v.findViewById(R.id.tv_bill_detail);
            mTvBillDetail.setOnClickListener(this);
        }

        public void setData(OrdersList item) {
            mTvBillCode.setText(item.billCode);
            mTvBillDate.setText(Utils.convertStringDateToString(item.createdDate));
            mTvBillMoney.setText(Utils.convertToCurrencyStr(item.totalPrice.intValue()));
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_bill_detail:
                    if (mItemClickCallBackListener != null) {
                        mItemClickCallBackListener.onClickItemDetail(mResultData.get(getAdapterPosition()));
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
        holder.setData(mResultData.get(position));
    }

}

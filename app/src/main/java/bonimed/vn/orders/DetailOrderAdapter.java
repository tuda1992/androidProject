package bonimed.vn.orders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bonimed.vn.R;
import bonimed.vn.cart.OrderProduct;
import bonimed.vn.util.Utils;

/**
 * Created by acv on 11/7/17.
 */

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.ViewHolder> {

    private Context mContext;
    private List<DetailOrder> mResultData;


    public DetailOrderAdapter(Context context, List<DetailOrder> resultData) {
        this.mContext = context;
        this.mResultData = resultData;
    }

    @Override
    public int getItemCount() {
        return mResultData != null ? mResultData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvProductName, mTvProductCompany, mTvProductUnit, mTvQuantity, mTvPrice, mTvTotalPrice;

        public ViewHolder(View v) {
            super(v);
            mTvProductName = (TextView) v.findViewById(R.id.tv_product_name);
            mTvProductCompany = (TextView) v.findViewById(R.id.tv_product_company);
            mTvProductUnit = (TextView) v.findViewById(R.id.tv_product_unit);
            mTvQuantity = (TextView) v.findViewById(R.id.tv_quantity);
            mTvPrice = (TextView) v.findViewById(R.id.tv_price);
            mTvTotalPrice = (TextView) v.findViewById(R.id.tv_total_price);
        }

        public void setData(DetailOrder item) {
            if (!TextUtils.isEmpty(item.productName)) {
                mTvProductName.setText(item.productName);
            }

            if (!TextUtils.isEmpty(item.company)) {
                mTvProductCompany.setText(item.company);
            }

            if (!TextUtils.isEmpty(item.description)) {
                mTvProductUnit.setText(item.description);
            }
            mTvQuantity.setText(item.quantity + "");
            mTvPrice.setText(Utils.convertToCurrencyStr(item.salePrice));
            mTvTotalPrice.setText(Utils.convertToCurrencyStr(item.totalPrice));

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_detail_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mResultData.get(position));
    }

}

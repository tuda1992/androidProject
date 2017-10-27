package bonimed.vn.products;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import bonimed.vn.R;

/**
 * Created by acv on 10/27/17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context mContext;
    private ItemClickCallBackListener mItemClickCallBackListener;
    private List<String> mResultData;

    public interface ItemClickCallBackListener {
        void onClickPurchase(String item);

        void onClickDetail(String item);
    }


    public ProductsAdapter(Context context, List<String> resultData, ItemClickCallBackListener listener) {
        this.mContext = context;
        this.mItemClickCallBackListener = listener;
        this.mResultData = resultData;
    }

    @Override
    public int getItemCount() {
        return mResultData != null ? mResultData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout mRlPurchase, mRlDetail;
        private ImageView mIvProduct;
        private TextView mTvProductName, mTvProductUnit, mTvProductMoney;

        public ViewHolder(View v) {
            super(v);
            mRlPurchase = (RelativeLayout) v.findViewById(R.id.rl_purchase);
            mRlDetail = (RelativeLayout) v.findViewById(R.id.rl_detail);
            mIvProduct = (ImageView) v.findViewById(R.id.iv_product);
            mTvProductName = (TextView) v.findViewById(R.id.tv_product_name);
            mTvProductUnit = (TextView) v.findViewById(R.id.tv_product_unit);
            mTvProductMoney = (TextView) v.findViewById(R.id.tv_product_money);

            mRlPurchase.setOnClickListener(this);
            mRlDetail.setOnClickListener(this);

        }

        public void setData(String item) {
            mTvProductName.setText(item);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_purchase:
                    if (mItemClickCallBackListener != null) {
                        mItemClickCallBackListener.onClickPurchase("");
                    }
                    break;
                case R.id.rl_detail:
                    if (mItemClickCallBackListener != null) {
                        mItemClickCallBackListener.onClickDetail("");
                    }
                    break;
            }
        }
    }

    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mResultData.get(position));
    }

}
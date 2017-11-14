package bonimed.vn.products;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bonimed.vn.R;
import bonimed.vn.util.Constants;
import bonimed.vn.util.Utils;

/**
 * Created by acv on 10/27/17.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context mContext;
    private ItemClickCallBackListener mItemClickCallBackListener;
    private List<DataProduct> mResultData;

    public interface ItemClickCallBackListener {
        void onClickPurchase(DataProduct item);

        void onClickDetail(DataProduct item);
    }


    public ProductsAdapter(Context context, List<DataProduct> resultData, ItemClickCallBackListener listener) {
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
        private ImageView mIvProduct, mIvPurchase;
        private TextView mTvProductName, mTvProductUnit, mTvProductMoney, mTvDetail;
        private CheckBox mCheckBox;

        public ViewHolder(View v) {
            super(v);
            mRlPurchase = (RelativeLayout) v.findViewById(R.id.rl_purchase);
            mRlDetail = (RelativeLayout) v.findViewById(R.id.rl_detail);
            mIvProduct = (ImageView) v.findViewById(R.id.iv_product);
            mIvPurchase = (ImageView) v.findViewById(R.id.iv_purchase);
            mTvProductName = (TextView) v.findViewById(R.id.tv_product_name);
            mTvProductUnit = (TextView) v.findViewById(R.id.tv_product_unit);
            mTvProductMoney = (TextView) v.findViewById(R.id.tv_product_money);
            mTvDetail = (TextView) v.findViewById(R.id.tv_detail);
            mCheckBox = (CheckBox) v.findViewById(R.id.cb_purchase);

            mIvPurchase.setOnClickListener(this);
            mTvDetail.setOnClickListener(this);


        }

        public void setData(final DataProduct item, final int position) {
            mTvProductName.setText(item.productName);
            mTvProductUnit.setText(item.description);
            mTvProductMoney.setText(Utils.convertToCurrencyStr(item.salePrice.intValue()));
            Picasso.with(mContext).load(Constants.URL_BONIMED + item.imageFullPath).error(R.drawable.ic_updating).into(mIvProduct);

            //in some cases, it will prevent unwanted situations
            mCheckBox.setOnCheckedChangeListener(null);

            //if true, your checkbox will be selected, else unselected
            mCheckBox.setChecked(mResultData.get(position).isChecked);

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mResultData.get(position).isChecked = isChecked;
                    if (mItemClickCallBackListener != null) {
                        mItemClickCallBackListener.onClickPurchase(mResultData.get(getAdapterPosition()));
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_purchase:
                    if (mItemClickCallBackListener != null) {
                        mItemClickCallBackListener.onClickPurchase(mResultData.get(getAdapterPosition()));
                    }
                    break;
                case R.id.tv_detail:
                    if (mItemClickCallBackListener != null) {
                        mItemClickCallBackListener.onClickDetail(mResultData.get(getAdapterPosition()));
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
        holder.setData(mResultData.get(position), position);
    }

    public void notifyData(List<DataProduct> poiItemList) {
        if (poiItemList != null) {
            mResultData.clear();
            mResultData.addAll(poiItemList);
            notifyItemRangeChanged(0, poiItemList.size());
        }
    }

}
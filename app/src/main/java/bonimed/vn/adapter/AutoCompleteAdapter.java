package bonimed.vn.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bonimed.vn.R;
import bonimed.vn.products.DataProduct;
import bonimed.vn.util.Utils;

/**
 * Created by acv on 10/25/17.
 */

public class AutoCompleteAdapter extends ArrayAdapter<DataProduct> {

    private ArrayList<DataProduct> items;
    private ArrayList<DataProduct> itemsAll;
    private ArrayList<DataProduct> suggestions;
    private int viewResourceId;
    private ItemClicKCallBackListener mListener;
    private TextView mNameProduct;

    public AutoCompleteAdapter(Context context, ArrayList<DataProduct> items) {
        super(context, R.layout.row_item_autocomplete, items);
        this.items = items;
        this.itemsAll = (ArrayList<DataProduct>) items.clone();
        this.suggestions = new ArrayList<DataProduct>();
        this.viewResourceId = R.layout.row_item_autocomplete;
    }

    public void setListener(ItemClicKCallBackListener listener) {
        this.mListener = listener;
    }

    public interface ItemClicKCallBackListener {
        void onItemClick(DataProduct item);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        mNameProduct = (TextView) v.findViewById(R.id.tv_product_name);
        final DataProduct customer = items.get(position);
        if (customer != null) {
            if (mNameProduct != null) {
                mNameProduct.setText(customer.productName + " | Công ty : " + customer.company + " | " + Utils.convertToCurrencyStr(customer.salePrice.intValue()));
            }
        }
        mNameProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(customer);
                }
            }
        });
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((DataProduct) (resultValue)).productName;
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (DataProduct customer : itemsAll) {
                    suggestions.add(customer);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<DataProduct> filteredList = (ArrayList<DataProduct>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (DataProduct c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}

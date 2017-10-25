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

/**
 * Created by acv on 10/25/17.
 */

public class AutoCompleteAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mListData, mListSuggestion, mListTemple;
    private int mViewResourceId;

    public AutoCompleteAdapter(Context context, List<String> listData) {
        super(context, R.layout.row_item_autocomplete, listData);
        this.mContext = context;
        this.mListData = listData;
        this.mViewResourceId = R.layout.row_item_autocomplete;
        this.mListSuggestion = new ArrayList<>();
        this.mListTemple = new ArrayList<>(mListData);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(mViewResourceId, null);
        }
        String product = mListData.get(position);
        if (product != null) {
            TextView tvProductName = (TextView) v.findViewById(R.id.tv_product_name);
            if (tvProductName != null) {
                tvProductName.setText(product);
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return mProductFilter;
    }

    Filter mProductFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = resultValue.toString();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                mListSuggestion.clear();
                for (String product : mListTemple) {
                    if (product.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        mListSuggestion.add(product);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListSuggestion;
                filterResults.count = mListSuggestion.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<String> filteredList = (ArrayList<String>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (String c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}

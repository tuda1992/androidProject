package bonimed.vn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bonimed.vn.R;
import bonimed.vn.products.DataProduct;

/**
 * Created by acv on 10/30/17.
 */

public class SearchAutoCompleteData extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<DataProduct> resultList = new ArrayList<DataProduct>();
    private List<DataProduct>  mListSuggestion;

    public SearchAutoCompleteData(Context context,List<DataProduct> resultList) {
        this.mContext = context;
        this.resultList = resultList;
        mListSuggestion = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index).productName;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_item_autocomplete, parent, false);
        }
        TextView tvProductName = (TextView) convertView.findViewById(R.id.tv_product_name);
        tvProductName.setText(resultList.get(position) != null ? resultList.get(position).productName : "");
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    mListSuggestion.clear();
                    for (DataProduct product : resultList) {
                        if (product.productName.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            mListSuggestion.add(product);
                        }
                    }

                    // Assign the data to the FilterResults
                    filterResults.values = mListSuggestion;
                    filterResults.count = mListSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<DataProduct>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
        return filter;
    }

}
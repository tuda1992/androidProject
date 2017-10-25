package bonimed.vn.navigationdrawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import bonimed.vn.R;

/**
 * Created by mac on 10/24/17.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    private List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater mInflater;
    private Context mContext;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.mTvTitle.setText(current.getTitle());
        if (position == 0){
            holder.mIvIcon.setImageResource(R.drawable.ic_product);
        }else if (position == 1){
            holder.mIvIcon.setImageResource(R.drawable.ic_cart);
        }else {
            holder.mIvIcon.setImageResource(R.drawable.icon_order);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        ImageView mIvIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mIvIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }
}

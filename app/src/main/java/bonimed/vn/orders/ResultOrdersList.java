package bonimed.vn.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bonimed.vn.base.Pagination;
import bonimed.vn.cart.OrderLines;

/**
 * Created by acv on 11/3/17.
 */

public class ResultOrdersList {

    @SerializedName("Data")
    @Expose
    public List<OrdersList> data;
    @SerializedName("Pagination")
    @Expose
    public Pagination pagination;

}

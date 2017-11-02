package bonimed.vn.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by acv on 11/1/17.
 */

public class OrderLines {

    @SerializedName("OrderLines")
    @Expose
    public List<OrderProduct> orderList;
    @SerializedName("BuyerId")
    @Expose
    public String buyerId;

}

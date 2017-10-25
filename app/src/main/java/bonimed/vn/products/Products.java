package bonimed.vn.products;

import com.google.gson.annotations.SerializedName;

/**
 * Created by acv on 10/25/17.
 */

public class Products {

    @SerializedName("SearchText")
    public String searchText;
    @SerializedName("ProductType")
    public Integer productType;
    @SerializedName("PageIndex")
    public Integer pageIndex;
    @SerializedName("PageSize")
    public Integer pageSize;
    @SerializedName("SellerId")
    public Integer sellerId;
    @SerializedName("ProductStatus")
    public Integer productStatus;
    @SerializedName("IsSpecial")
    public boolean isSpecial;

}

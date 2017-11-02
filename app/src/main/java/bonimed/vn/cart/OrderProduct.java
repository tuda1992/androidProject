package bonimed.vn.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acv on 11/1/17.
 */

public class OrderProduct {

    @SerializedName("Id")
    @Expose
    public Object id;
    @SerializedName("ProductId")
    @Expose
    public String productId;
    @SerializedName("ProductName")
    @Expose
    public String productName;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("SalePrice")
    @Expose
    public Double salePrice;
    @SerializedName("SellerSalePrice")
    @Expose
    public Double sellerSalePrice;
    @SerializedName("Quantity")
    @Expose
    public Integer quantity;
    @SerializedName("DatePicturePath")
    @Expose
    public Object datePicturePath;
    @SerializedName("ImageFullPath")
    @Expose
    public String imageFullPath;
    @SerializedName("ProductType")
    @Expose
    public Integer productType;
    @SerializedName("SellerId")
    @Expose
    public String sellerId;
    @SerializedName("SellerName")
    @Expose
    public Object sellerName;
    @SerializedName("TotalPrice")
    @Expose
    public Double totalPrice;
    @SerializedName("TotalSellerPrice")
    @Expose
    public Double totalSellerPrice;
    @SerializedName("OrderId")
    @Expose
    public Object orderId;
    @SerializedName("SellerRole")
    @Expose
    public Integer sellerRole;
    @SerializedName("IsExternalProduct")
    @Expose
    public Boolean isExternalProduct;
    @SerializedName("Company")
    @Expose
    public String company;
    @SerializedName("Quota")
    @Expose
    public Integer quota;
    @SerializedName("IsVAT")
    @Expose
    public Boolean isVAT;
    @SerializedName("SortOrder")
    @Expose
    public Integer sortOrder;
    @SerializedName("OriginalOlCnt")
    @Expose
    public Integer originalOlCnt;
    @SerializedName("Status")
    @Expose
    public Integer status;
    @SerializedName("TotalReturnQuantity")
    @Expose
    public Integer totalReturnQuantity;
    @SerializedName("ReturnQty")
    @Expose
    public Integer returnQty;

}

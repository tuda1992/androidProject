package bonimed.vn.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acv on 11/7/17.
 */

public class DetailOrder {

    @SerializedName("ProductId")
    @Expose
    public String productId;
    @SerializedName("OrderId")
    @Expose
    public String orderId;
    @SerializedName("Quantity")
    @Expose
    public Integer quantity;
    @SerializedName("ReturnQty")
    @Expose
    public Integer returnQty;
    @SerializedName("SalePrice")
    @Expose
    public Integer salePrice;
    @SerializedName("SellerSalePrice")
    @Expose
    public Integer sellerSalePrice;
    @SerializedName("SellerId")
    @Expose
    public String sellerId;
    @SerializedName("IsExternalProduct")
    @Expose
    public Boolean isExternalProduct;
    @SerializedName("SortOrder")
    @Expose
    public Integer sortOrder;
    @SerializedName("OriginalOlCnt")
    @Expose
    public Integer originalOlCnt;
    @SerializedName("TotalReturnQuantity")
    @Expose
    public Integer totalReturnQuantity;
    @SerializedName("TotalPrice")
    @Expose
    public Integer totalPrice;
    @SerializedName("TotalSellerPrice")
    @Expose
    public Integer totalSellerPrice;
    @SerializedName("ProductName")
    @Expose
    public String productName;
    @SerializedName("ProductType")
    @Expose
    public Integer productType;
    @SerializedName("SellerName")
    @Expose
    public String sellerName;
    @SerializedName("SellerRole")
    @Expose
    public Integer sellerRole;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("Company")
    @Expose
    public String company;
    @SerializedName("IsVAT")
    @Expose
    public Boolean isVAT;
    @SerializedName("Id")
    @Expose
    public String id;
    @SerializedName("CreatedDate")
    @Expose
    public String createdDate;
    @SerializedName("CreatedBy")
    @Expose
    public String createdBy;
    @SerializedName("LastModifiedDate")
    @Expose
    public String lastModifiedDate;
    @SerializedName("LastModifiedBy")
    @Expose
    public String lastModifiedBy;
    @SerializedName("Message")
    @Expose
    public Object message;
    @SerializedName("Status")
    @Expose
    public Integer status;
    @SerializedName("SecurityToken")
    @Expose
    public String securityToken;

}

package bonimed.vn.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acv on 10/30/17.
 */

public class DataProduct {

    @SerializedName("ProductName")
    @Expose
    public String productName;
    @SerializedName("ProductType")
    @Expose
    public Integer productType;
    @SerializedName("BarCode")
    @Expose
    public Object barCode;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("IsVAT")
    @Expose
    public Boolean isVAT;
    @SerializedName("Company")
    @Expose
    public String company;
    @SerializedName("MadeIn")
    @Expose
    public String madeIn;
    @SerializedName("IsSpecial")
    @Expose
    public Boolean isSpecial;
    @SerializedName("PicturePath")
    @Expose
    public String picturePath;
    @SerializedName("MinPrice")
    @Expose
    public Integer minPrice;
    @SerializedName("MaxPrice")
    @Expose
    public Integer maxPrice;
    @SerializedName("SalePrice")
    @Expose
    public Integer salePrice;
    @SerializedName("ImageFullPath")
    @Expose
    public String imageFullPath;
    @SerializedName("SellerId")
    @Expose
    public String sellerId;
    @SerializedName("SellerName")
    @Expose
    public String sellerName;
    @SerializedName("Quantity")
    @Expose
    public Integer quantity;
    @SerializedName("TotalQuantity")
    @Expose
    public Integer totalQuantity;
    @SerializedName("TotalSaleQuantity")
    @Expose
    public Integer totalSaleQuantity;
    @SerializedName("SellerCode")
    @Expose
    public Object sellerCode;
    @SerializedName("Image")
    @Expose
    public Object image;
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

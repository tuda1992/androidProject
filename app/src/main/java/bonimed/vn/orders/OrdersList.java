package bonimed.vn.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by acv on 11/3/17.
 */

public class OrdersList {

    @SerializedName("BuyerId")
    @Expose
    public String buyerId;
    @SerializedName("AssistantId")
    @Expose
    public String assistantId;
    @SerializedName("ModeratorId")
    @Expose
    public String moderatorId;
    @SerializedName("RepId")
    @Expose
    public String repId;
    @SerializedName("CallSaleId")
    @Expose
    public Object callSaleId;
    @SerializedName("MaxSortOrder")
    @Expose
    public Integer maxSortOrder;
    @SerializedName("ShipFee")
    @Expose
    public Integer shipFee;
    @SerializedName("Number")
    @Expose
    public Integer number;
    @SerializedName("OriginalId")
    @Expose
    public Object originalId;
    @SerializedName("AssistantChangedDate")
    @Expose
    public Object assistantChangedDate;
    @SerializedName("AssistantConfirmedDate")
    @Expose
    public Object assistantConfirmedDate;
    @SerializedName("CallSaleChangedDate")
    @Expose
    public Object callSaleChangedDate;
    @SerializedName("AssistantPrintedDate")
    @Expose
    public Object assistantPrintedDate;
    @SerializedName("IsCallSaleConfirmed")
    @Expose
    public Boolean isCallSaleConfirmed;
    @SerializedName("OrderLines")
    @Expose
    public Object orderLines;
    @SerializedName("TotalPrice")
    @Expose
    public Integer totalPrice;
    @SerializedName("TotalSellerPrice")
    @Expose
    public Integer totalSellerPrice;
    @SerializedName("BuyerName")
    @Expose
    public String buyerName;
    @SerializedName("AssistantName")
    @Expose
    public String assistantName;
    @SerializedName("BuyerPhone")
    @Expose
    public String buyerPhone;
    @SerializedName("RepPhone")
    @Expose
    public String repPhone;
    @SerializedName("BuyerAddress")
    @Expose
    public String buyerAddress;
    @SerializedName("ModeratorName")
    @Expose
    public Object moderatorName;
    @SerializedName("RepName")
    @Expose
    public Object repName;
    @SerializedName("CallSaleName")
    @Expose
    public String callSaleName;
    @SerializedName("BillCode")
    @Expose
    public String billCode;
    @SerializedName("Id")
    @Expose
    public String id;
    @SerializedName("CreatedDate")
    @Expose
    public String createdDate;
    @SerializedName("CreatedBy")
    @Expose
    public Object createdBy;
    @SerializedName("LastModifiedDate")
    @Expose
    public Object lastModifiedDate;
    @SerializedName("LastModifiedBy")
    @Expose
    public Object lastModifiedBy;
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

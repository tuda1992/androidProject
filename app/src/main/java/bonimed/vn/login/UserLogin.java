package bonimed.vn.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acv on 10/25/17.
 */

public class UserLogin {

    @SerializedName("UserName")
    @Expose
    public String userName;
    @SerializedName("FullName")
    @Expose
    public String fullName;
    @SerializedName("PhoneNumber")
    @Expose
    public Object phoneNumber;
    @SerializedName("Address")
    @Expose
    public Object address;
    @SerializedName("Password")
    @Expose
    public String password;
    @SerializedName("Role")
    @Expose
    public Integer role;
    @SerializedName("AssistantId")
    @Expose
    public String assistantId;
    @SerializedName("ModeratorId")
    @Expose
    public Object moderatorId;
    @SerializedName("RepId")
    @Expose
    public Object repId;
    @SerializedName("CallSaleId")
    @Expose
    public Object callSaleId;
    @SerializedName("SellerId")
    @Expose
    public Object sellerId;
    @SerializedName("IsFareConfig")
    @Expose
    public Boolean isFareConfig;
    @SerializedName("LastLoginAt")
    @Expose
    public Object lastLoginAt;
    @SerializedName("CanImport")
    @Expose
    public Boolean canImport;
    @SerializedName("AssistantName")
    @Expose
    public Object assistantName;
    @SerializedName("ModeratorName")
    @Expose
    public Object moderatorName;
    @SerializedName("RepName")
    @Expose
    public Object repName;
    @SerializedName("CallSaleName")
    @Expose
    public Object callSaleName;
    @SerializedName("SellerName")
    @Expose
    public Object sellerName;
    @SerializedName("RoleGroupId")
    @Expose
    public String roleGroupId;
    @SerializedName("LastLoginAtTicks")
    @Expose
    public Integer lastLoginAtTicks;
    @SerializedName("LastModifiedDateTicks")
    @Expose
    public Integer lastModifiedDateTicks;
    @SerializedName("IsSpecialLocked")
    @Expose
    public Boolean isSpecialLocked;
    @SerializedName("HardDiskSerialNumber")
    @Expose
    public Object hardDiskSerialNumber;
    @SerializedName("Functions")
    @Expose
    public Object functions;
    @SerializedName("Id")
    @Expose
    public String id;
    @SerializedName("CreatedDate")
    @Expose
    public Object createdDate;
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
    @SerializedName("ShipFee")
    @Expose
    public Integer shipFee;

    public String versionApp;

}

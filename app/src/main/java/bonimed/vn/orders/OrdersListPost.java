package bonimed.vn.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acv on 11/3/17.
 */

public class OrdersListPost {

    @SerializedName("StartTime")
    @Expose
    public Integer startTime;
    @SerializedName("EndTime")
    @Expose
    public Integer endTime;
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
    @SerializedName("PageIndex")
    @Expose
    public Integer pageIndex;
    @SerializedName("PageSize")
    @Expose
    public Integer pageSize;

}

package bonimed.vn.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by acv on 10/30/17.
 */

public class Pagination {

    @SerializedName("RowCount")
    @Expose
    public Integer rowCount;
    @SerializedName("PageCount")
    @Expose
    public Integer pageCount;
    @SerializedName("PageIndex")
    @Expose
    public Integer pageIndex;
    @SerializedName("FormId")
    @Expose
    public Object formId;

}

package bonimed.vn.products;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import bonimed.vn.base.Pagination;

/**
 * Created by acv on 10/30/17.
 */

public class ResultProduct {

    @SerializedName("Data")
    public List<DataProduct> data;
    @SerializedName("Pagination")
    public Pagination pagination;

}

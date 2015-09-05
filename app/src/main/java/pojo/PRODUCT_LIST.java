package pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samir on 10/02/15.
 */
public class PRODUCT_LIST {

    @SerializedName("id")
    public String id ;

    @SerializedName("name")
    public String name ;


    @SerializedName("code")
    public String code ;

    @SerializedName("price")
    public String price ;

    @SerializedName("saleprice")
    public String saleprice ;


    @SerializedName("disount_price")
    public String disount_price ;


    @SerializedName("unit")
    public String unit ;

    @SerializedName("unit_type")
    public String unit_type ;


    @SerializedName("sale_or_not")
    public String sale_or_not ;


    @SerializedName("description")
    public String description ;

    @SerializedName("image")
    public String image ;


}

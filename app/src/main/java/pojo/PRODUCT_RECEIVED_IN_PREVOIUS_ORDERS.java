package pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samir on 20/03/15.
 */
public class PRODUCT_RECEIVED_IN_PREVOIUS_ORDERS {

    @SerializedName("product_id")
    public String product_id ;

    @SerializedName("no_of_unit_booked")
    public String no_of_unit_booked ;


    @SerializedName("price")
    public String price ;


    @SerializedName("product_name")
    public String product_name ;

    @SerializedName("product_image")
    public String product_image ;


    @SerializedName("unit_type")
    public String unit_type ;


    @SerializedName("per_unit")
    public String per_unit ;

}

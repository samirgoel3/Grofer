package pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 20/03/15.
 */
public class PREVIOUS_ORDERS {

    @SerializedName("order_id")
    public String order_id ;

    @SerializedName("user_id")
    public String user_id ;

    @SerializedName("user_name")
    public String user_name ;

    @SerializedName("phone")
    public String phone ;

    @SerializedName("alternate_phone")
    public String alternate_phone ;

    @SerializedName("state")
    public String state ;

    @SerializedName("area_name")
    public String area_name ;


    @SerializedName("house_number")
    public String house_number ;

    @SerializedName("street_number")
    public String street_number ;


    @SerializedName("pin")
    public String pin ;

    @SerializedName("address")
    public String address ;

    @SerializedName("landmark")
    public String landmark ;


    @SerializedName("time_of_delivery")
    public String time_of_delivery ;

    @SerializedName("date_of_delivery")
    public String date_of_delivery ;





    @SerializedName("ordering_date")
    public String ordering_date ;


    @SerializedName("ordering_time")
    public String ordering_time ;

    @SerializedName("guest_or_login")
    public String guest_or_login ;


    public List<PRODUCT_RECEIVED_IN_PREVOIUS_ORDERS> product_detail = new ArrayList<>();


    @SerializedName("order_status")
    public String order_status ;


    @SerializedName("status")
    public String status ;

}

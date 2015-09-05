package pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samir on 20/02/15.
 */
public class MESSAGE_AFTER_LOGIN {

    @SerializedName("id")
    public String id ;


    @SerializedName("first_name")
    public String first_name ;

    @SerializedName("last_name")
    public String last_name ;


    @SerializedName("email")
    public String email ;

    @SerializedName("phone")
    public String phone ;
}

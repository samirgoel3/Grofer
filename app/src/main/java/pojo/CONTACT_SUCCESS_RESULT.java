package pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samir on 09/06/15.
 */
public class CONTACT_SUCCESS_RESULT {
    @SerializedName("result")
    public String result ;

    @SerializedName("message")
    public CONTACT_ATTRIBUTES ca = new CONTACT_ATTRIBUTES();

}

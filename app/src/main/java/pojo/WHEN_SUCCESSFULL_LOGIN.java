package pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samir on 20/02/15.
 */
public class WHEN_SUCCESSFULL_LOGIN {

    @SerializedName("result")
    public String result ;

    @SerializedName("message")
    public MESSAGE_AFTER_LOGIN maf = new MESSAGE_AFTER_LOGIN();

}

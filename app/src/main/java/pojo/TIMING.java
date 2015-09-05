package pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samir on 03/08/15.
 */
public class TIMING {

    @SerializedName("result")
    public String result ;

    @SerializedName("message")
    public  TIMINGPARAMATER tp = new TIMINGPARAMATER() ;

}

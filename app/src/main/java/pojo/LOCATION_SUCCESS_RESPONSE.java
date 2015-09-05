package pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 18/05/15.
 */
public class LOCATION_SUCCESS_RESPONSE {

    @SerializedName("result")
    public String result ;

    @SerializedName("message")
    public List<LOCATION> lm = new ArrayList<LOCATION>();
}

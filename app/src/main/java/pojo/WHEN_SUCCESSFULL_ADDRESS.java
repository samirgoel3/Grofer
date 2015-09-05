package pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 10/03/15.
 */
public class WHEN_SUCCESSFULL_ADDRESS {

    @SerializedName("result")
    public String result ;

    @SerializedName("message")
    public List<VIEW_ADDRESS> mav = new ArrayList<VIEW_ADDRESS>();

}

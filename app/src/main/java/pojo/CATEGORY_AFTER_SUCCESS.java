package pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 18/05/15.
 */
public class CATEGORY_AFTER_SUCCESS {


    @SerializedName("result")
    public String result ;

    @SerializedName("message")
    public List<CATEGORY>   mac = new ArrayList<CATEGORY>();
}

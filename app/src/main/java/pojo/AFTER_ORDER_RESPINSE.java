package pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 20/03/15.
 */
public class AFTER_ORDER_RESPINSE {

    @SerializedName("result")
    public String result ;

   public  List<PREVIOUS_ORDERS> message = new ArrayList<>();

}

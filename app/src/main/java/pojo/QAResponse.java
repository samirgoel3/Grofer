package pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samir on 27/07/15.
 */
public class QAResponse {

    @SerializedName("result")
    public String result ;

    @SerializedName("message")
    public QuestionAnswer qa= new QuestionAnswer();
}

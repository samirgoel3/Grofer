package database;

import android.content.Context;
import android.util.Log;

import com.spinno.buzczar.Producttable;

import java.util.List;

/**
 * Created by samir on 16/02/15.
 */
public class IDChecker {







    Context con ;
    boolean checkid = false ;

    public  boolean chekid_if_already_exsist(String id ) {

        try {
            List<Producttable> templist = Producttable.find(Producttable.class, "Product_Id = ?", id);
            Log.d("Size of templist ", "" + templist.size());
            if (templist.size() == 1) {
                checkid = true;
            } else {
                checkid = false;
            }

        } catch (Exception e) {
            Log.d("Product Id is not saved in Databse ", "Id is not saved in Databse ");
        }

        return checkid;
         }




    public  void addidtothelist(String id){
         MyListOfId.listofid.add(id);
        Log.d("List of ids after addingelements into it ", MyListOfId.listofid.toString());

    }

}





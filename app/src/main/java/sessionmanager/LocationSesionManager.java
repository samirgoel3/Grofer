package sessionmanager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by samir on 22/05/15.
 */
public class LocationSesionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "LocationPrefrences";



    public static final  String KEY_LOCATION_ID = "location_id";
    public static final String KEY_LOCATION_NAME =  "location_name";
    public static final String KEY_CITY_ID = "city_id";
    public static final String KEY_CITY_NAME =  "city_name";

    public static final  String KEY_CONTACT_PERSON_NAME = "contact_person_name";
    public static final String KEY_EMAIL =  "email";
    public static final String KEY_MOBILE_No =  "mobile_no";
    public static final  String KEY_PHONE_NO = "phone_no";
    public static final String KEY_ADDRESS =  "address";


    // Constructor
    public LocationSesionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createcitystore(String city , String cityid){
        editor.putString(KEY_CITY_ID , cityid );
        editor.putString(KEY_CITY_NAME,city);
        editor.commit();

    }

    public void createlocationstore(String locationid , String locationname ){

        editor.putString(KEY_LOCATION_ID, locationid);
        editor.putString(KEY_LOCATION_NAME,locationname);
        editor.commit();
    }

    public void createadminaddress(String contact , String email , String mobile , String phone , String address){
        editor.putString(KEY_CONTACT_PERSON_NAME, contact);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_MOBILE_No, mobile);
        editor.putString(KEY_PHONE_NO,phone);
        editor.putString(KEY_ADDRESS, address);
        editor.commit();
    }






    public HashMap<String , String > getlocation(){
        HashMap<String, String> loc = new HashMap<String, String>();
        loc.put(KEY_LOCATION_ID, pref.getString(KEY_LOCATION_ID, ""));
        loc.put(KEY_LOCATION_NAME, pref.getString(KEY_LOCATION_NAME, ""));
        return  loc ;
    }


    public HashMap<String , String > getcity(){
        HashMap<String, String> loc = new HashMap<String, String>();
        loc.put(KEY_CITY_NAME, pref.getString(KEY_CITY_NAME, ""));
        loc.put(KEY_CITY_ID ,pref.getString(KEY_CITY_ID,""));

        return  loc ;
    }

    public HashMap<String , String > getadminaddress(){
        HashMap<String, String> loc = new HashMap<String, String>();
        loc.put(KEY_CONTACT_PERSON_NAME, pref.getString(KEY_CONTACT_PERSON_NAME, ""));
        loc.put(KEY_EMAIL, pref.getString(KEY_EMAIL, ""));
        loc.put(KEY_MOBILE_No, pref.getString(KEY_MOBILE_No, ""));
        loc.put(KEY_PHONE_NO, pref.getString(KEY_PHONE_NO, ""));
        loc.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, ""));
        return  loc ;
    }


}


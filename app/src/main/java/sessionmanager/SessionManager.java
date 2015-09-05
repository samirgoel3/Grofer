package sessionmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by samir on 19/02/15.
 */
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "LoginPrefrences";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";



    // Email address (make variable public to access from outside)
    public static final String KEY_UserId= "user_id";
    public static final String KEY_UserName= "user_name";
    public static final String KEY_UsephoneNumber= "user_phone_number";
    public static final String KEY_UserEmail= "user_email";
    public static final  String KEY_SLIDE_TUTORIAL = "slide_tute";




    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */


     public void createLoginSession(String userid , String username , String userphone ,String useremail){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        Log.d("ye value shared prefrences me dal rahi hai " , userid);
        Log.d("ye value shared prefrences me dal rahi hai " , username);
        Log.d("ye value shared prefrences me dal rahi hai " , userphone);
        Log.d("ye value shared prefrences me dal rahi hai " , useremail);

        // Storing name in pref
        editor.putString(KEY_UserId, userid);
        editor.putString(KEY_UserName,username);
        editor.putString(KEY_UsephoneNumber , userphone);
        editor.putString(KEY_UserEmail , useremail);

        // commit changes
        editor.commit();
    }



    public void createtuteone(String tute ){

        editor.putString(KEY_SLIDE_TUTORIAL, tute);
        editor.commit();
    }

    public HashMap<String, String> getTuteone(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_SLIDE_TUTORIAL, pref.getString(KEY_SLIDE_TUTORIAL, "guest_user"));

        return user;
    }




    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
        }

    }

      public boolean cheskUseroginStatus(){
             if(!this.isLoggedIn()){
                return false;
             }else
                 return true;
      }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_UserId, pref.getString(KEY_UserId, "guest user"));
        user.put(KEY_UserName, pref.getString(KEY_UserName, "guest user"));
        user.put(KEY_UsephoneNumber, pref.getString(KEY_UsephoneNumber, "guest user"));
        user.put(KEY_UserEmail, pref.getString(KEY_UserEmail, "guest user"));
        // return user
        return user;
    }


    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Toast.makeText(_context , "You are now Logged Out " , Toast.LENGTH_LONG).show();


    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}

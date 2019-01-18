package diegofeder.testskill.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefsUtil {

    private static final String SIGNED_IN_USER = "SIGNED_IN_USER";

    public SharedPrefsUtil() {
        super();
    }


    public String getSignedInUserId(Context context) {
        SharedPreferences sharedPrefs;
        String user_name_id;
        try {
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            user_name_id = sharedPrefs.getString(SIGNED_IN_USER, "");
        } catch (Exception e) {
            e.getStackTrace();
            user_name_id = "";
        }
        return user_name_id;
    }

    public void setSignedInUserId(Context context, String user_name_id) {
        SharedPreferences sharedPrefs;
        try {
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(SIGNED_IN_USER, user_name_id);
            editor.apply();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}

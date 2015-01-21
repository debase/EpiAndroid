package epiandroid.eu.epitech.epiandroid.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by debas on 20/01/15.
 */
public class UserPreference {
    public static String USER_PREFS = "UserPrefs";
    public static String USER_LOGEDIN = "user_logedin";
    public static String USER_TOKEN = "user_token";

    public static boolean isUserLogedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFS, 0);
        return sharedPreferences.getBoolean(USER_LOGEDIN, false);
    }

    public static void setUserLogedIn(Context context, boolean logedIn) {
        SharedPreferences settings = context.getSharedPreferences(USER_PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(USER_LOGEDIN, logedIn);
        editor.commit();
    }

    public static String getUserToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFS, 0);
        return sharedPreferences.getString(USER_TOKEN, null);
    }

    public static void setUserToken(Context context, String token) {
        SharedPreferences settings = context.getSharedPreferences(USER_PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_TOKEN, token);
        editor.commit();
    }
}

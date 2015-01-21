package epiandroid.eu.epitech.epiandroid.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by debas on 20/01/15.
 */
public class UserPreferenceHelper {
    public static String USER_PREFS = "UserPrefs";
    public static String USER_LOGEDIN = "user_logedin";
    public static String USER_LOGIN = "user_login";
    public static String USER_PASSWD = "user_passwd";

    public static boolean isUserLogedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFS, 0);
        return sharedPreferences.getBoolean(USER_LOGEDIN, false);
    }

    public static void setUserLogedIn(Context context, boolean logedIn) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS, 0).edit();
        editor.putBoolean(USER_LOGEDIN, logedIn);
        editor.commit();
    }

    public static String getLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFS, 0);
        return sharedPreferences.getString(USER_LOGIN, null);
    }

    public static String getPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFS, 0);
        return sharedPreferences.getString(USER_PASSWD, null);
    }

    public static void loginInfo(Context context, String login, String pwd) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS, 0).edit();
        editor.putString(USER_LOGIN, login);
        editor.putString(USER_PASSWD, pwd);
        editor.commit();
    }

    public static void logout(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS, 0).edit();
        editor.remove(USER_LOGIN);
        editor.remove(USER_PASSWD);
        editor.commit();
    }
}

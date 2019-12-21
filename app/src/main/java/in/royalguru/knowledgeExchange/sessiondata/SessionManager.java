package in.royalguru.knowledgeExchange.sessiondata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import in.kalmesh.projectbase.SharedPreferencesUtility;
import in.royalguru.knowledgeExchange.R;

/**
 * Created by Kalmeshwar on 12 May 2018 at 14:22.
 */
public class SessionManager {
    // Shared setPreferenceName to store and retrieve values
    private SharedPreferencesUtility appSp = null;
    static SessionManager session = null;

    public static SessionManager getInstance(Context mContext) {
        return session == null ? (session = new SessionManager(mContext)) : session;
    }


    // Context
    private Context mContext = null;

    /**
     * @param mContext Constructor with Activity Context
     */
    public SessionManager(Context mContext) {
        this.mContext = mContext;
        appSp = new SharedPreferencesUtility(mContext);
    }


    /*
     * create user login session
     * */

    public void userLoginSession(String user_id, String fullName, String email, boolean loginFlag) {
        appSp.setString(SharedPreferenceKeys.getInstance().user_id, user_id);
        appSp.setString(SharedPreferenceKeys.getInstance().fullName, fullName);
        appSp.setString(SharedPreferenceKeys.getInstance().emailAddress, email);
        appSp.setBoolean(SharedPreferenceKeys.getInstance().isLoggedIn, loginFlag);
    }


    /*
     * Clear customer session
     * */
    public void clearUserSession() {
        appSp.removeValue(SharedPreferenceKeys.getInstance().user_id);
        appSp.removeValue(SharedPreferenceKeys.getInstance().emailAddress);
        appSp.removeValue(SharedPreferenceKeys.getInstance().fullName);
        appSp.removeValue(SharedPreferenceKeys.getInstance().isLoggedIn);

    }

    /**
     * Set login flag
     */
    public void setLoginFlag(boolean loginFlag) {
        appSp.setBoolean(SharedPreferenceKeys.getInstance().isLoggedIn, loginFlag);
    }


    public boolean isLoggedIn() {
        return appSp.getBoolean(SharedPreferenceKeys.getInstance().isLoggedIn, false);
    }

    public void saveDeviceToken(String deviceToken) {
        appSp.setString(SharedPreferenceKeys.getInstance().deviceToken, deviceToken);
    }

    public void setPreferenceName() {
        SharedPreferencesUtility.ZWING_APP_NAME = mContext.getString(R.string.app_name);
    }

    public String getUserId() {
        return appSp.getString(SharedPreferenceKeys.getInstance().user_id, "");
    }
}
package in.royalguru.knowledgeExchange.sessiondata;

import in.kalmesh.projectbase.SharedPreferencesUtility;
import in.royalguru.knowledgeExchange.application.ApplicationDetails;
import in.royalguru.knowledgeExchange.utils.AppOperationHandler;

/**
 * Created by Kalmeshwar on 04 Jul 2019 at 15:06.
 */
public class AppSessionData {
    private static AppSessionData appSessionData;

    private AppSessionData() {

    }


    public static AppSessionData getInstance() {
        return appSessionData == null ? (appSessionData = new AppSessionData()) : appSessionData;
    }


    public String getUserName() {
        return new SharedPreferencesUtility(ApplicationDetails.getInstance().getContext()).getString(SharedPreferenceKeys.getInstance().fullName, "");
    }

    public String getUserId() {
        return new SharedPreferencesUtility(ApplicationDetails.getInstance().getContext()).getString(SharedPreferenceKeys.getInstance().user_id, "");
    }

    public String getUseremail() {
        return new SharedPreferencesUtility(ApplicationDetails.getInstance().getContext()).getString(SharedPreferenceKeys.getInstance().emailAddress, "");
    }

}
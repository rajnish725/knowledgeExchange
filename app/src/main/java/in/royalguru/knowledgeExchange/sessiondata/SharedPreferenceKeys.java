package in.royalguru.knowledgeExchange.sessiondata;

/**
 * Created by rishabhshukla on 20/03/18.
 */

public class SharedPreferenceKeys {
    private SharedPreferenceKeys() {

    }

    private static SharedPreferenceKeys sharedPreferenceKeys = null;

    public static SharedPreferenceKeys getInstance() {
        return sharedPreferenceKeys == null ? (sharedPreferenceKeys = new SharedPreferenceKeys()) : sharedPreferenceKeys;
    }

    /*
     * Keys for vendor session
     * */
    public final String user_id = "user_id";
    public final String apiToken = "apiToken";
    public final String mobileNumber = "mobileNumber";
    final String isLoggedIn = "isLoggedIn";
    public final String fullName = "fullName";
    public final String emailAddress = "emailAddress";
    public final String deviceToken = "deviceToken";
    public final String storeId = "storeId";
    public final String approvedStatus = "approvedStatus";
    public final String gender = "gender";

    /*
     * Keys for customer session
     * */
    public final String c_id = "c_id";
    public final String apiTokenCust = "apiTokenCust";
    public final String mobileNumberCust = "mobileNumberCust";
    public final String emailAddressCust = "emailAddressCust";
    public final String customerName = "customerName";
    public final String groupCode = "customer_group_code";

    /*
     * Keys for dummy session
     * */
    public final String dummy_c_id = "dummy_c_id";
    public final String dummy_apiTokenCust = "dummy_apiTokenCust";
    public final String dummy_mobileNumberCust = "dummy_mobileNumberCust";
    public final String dummy_emailAddressCust = "dummy_emailAddressCust";
    public final String dummy_customerName = "dummy_customerName";
}
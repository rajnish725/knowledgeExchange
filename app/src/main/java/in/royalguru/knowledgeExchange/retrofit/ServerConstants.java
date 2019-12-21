package in.royalguru.knowledgeExchange.retrofit;

/**
 * Created by Kalmeshwar on 12 May 2018 at 13:39.
 */
public class ServerConstants {
    /*vendor/register-mobile*/
    public static final String LOGIN_REDIRECT = "login_redirect";
    public static final String OTP_SENT = "otp_sent";
    public static final String ACC_NOT_ACTIVE = "account_not_active";

    /*vendor/login*/
    public static final String INVALID_CREDENTIAL = "invalid_credentials";

    /*forgot-pin*/
    public static final String MOBILE_NOT_FOUND = "mobile_not_found";

    public static final String SUCCESS_RESPONSE = "success";
    public static final String FAILURE_RESPONSE = "fail";
    public static final String WARNING_RESPONSE = "warning";

    /*change-pin*/
    public static final String CHANGE_PIN = "pin_change";

    /*vendor/verify-mobile*/
    public static final String OTP_VERIFIED = "otp_verified";
    public static final String INCORRECT_OTP = "incorrect_otp";

    /*register-user-details*/
    public static final String REGISTERED = "registed";
    public static final String EMAIL_EXIST = "email_already";

    /*get-vendor-details AND get-store-data*/
    public static final String VENDOR_DETAILS = "verify_details";
    public static final String STORE_DETAILS = "store_details";

    /*verify-order*/
    public static final String VERIFY_SUCCESS = "verify_details";

    /*get-profile*/
    public static final String PROFILE_GET = "profile_data";

    /*profile-update*/
    public static final String PROFILE_UPDATE = "profile_update";

    /*verify-order*/
    public static final String ORDER_DETAILS = "order_details";
    public static final String ORDER_VERIFIED = "order_verified";

    /*cart-details*/
    public static final String CART_DETAILS = "cart_details";

    /*product-details*/
    public static final String PRODUCT_DETAILS = "get_product_details";

    /*add-to-wishlist*/
    public static final String ADD_TO_WISHLIST = "add-to-wishlist";

    /*add-to-cart AND product-qty-update*/
    public static final String ADD_TO_CART = "add_to_cart";
    public static final String UPDATE_TO_CART = "product_qty_update";
    public static final String SAVE_CART = "save_cart";

    /*save-carry-bags*/
    public static final String CARRY_BAG_ADDED = "add_carry_bags";

    /*get-carry-bags*/
    public static final String GET_CARRY_BAGS = "get_carry_bags_by_store";

    /*remove-product-from-cart*/
    public static final String REMOVE_PRODUCT = "remove_product";

    /*process-to-payment*/
    public static final String PAYMENT_PROCESS = "proceed_to_payment";

    /*save-payment*/
    public static final String PAYMENT_SAVE = "payment_save";

    /*order-verify-status*/
    public static final String VERIFICATION_RESP = "verification";

    /*my-orders*/
    public static final String MY_ORDERS = "my_order";

    /*get-feedback-questions*/
    public static final String GET_QUESTIONS = "get_feedback_questions";

    /*submit-feedback-answer*/
    public static final String SUBMIT_FEEDBACK = "submit_feedback";

    /*process-to-payment*/
    public static final String SESSION_NOT_OPENED = "add_opening_balance";

    /*get-offers*/
    public static final String OFFERS_GET = "offers";

    /*save-catalog*/
    public static final String SAVE_CATALOG = "save_catalogs";

    /*vendor/save-item*/
    public static final String SAVE_ITEMS = "save_items";
}
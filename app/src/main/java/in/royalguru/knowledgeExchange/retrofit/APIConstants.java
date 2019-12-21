package in.royalguru.knowledgeExchange.retrofit;

import in.royalguru.knowledgeExchange.BuildConfig;

/**
 * Created by Kalmeshwar on 26 Jun 2019 at 11:49.
 */
public class APIConstants {
    private static APIConstants apiConstants = null;

    private APIConstants() {
    }

    public static APIConstants getInstance() {
        return (apiConstants == null) ? apiConstants = new APIConstants() : apiConstants;
    }

    public String getBaseUrl() {
        return BuildConfig.base_url;
    }

    public String TRANS_FROM = "CLOUD_TAB_ANDROID";

    /*
     * Controller names for the barcode screen
     * */
    public String controllerAddToCart = "add-to-cart";
    public String controllerUpdateToCart = "product-qty-update";

    /*
     * Controller names for the Setup pin screen
     * */
    public String controllerSetUpPin = "vendor/setup-pin";
    public String controllerChangePin = "vendor/change-pin";

    /*
     * Controller names for the payment session
     * */
    public String controllerOpenSession = "vendor/opening-balance";
    public String controllerCloseSession = "vendor/closing-balance";

    /*
     * Controller names for the apply and remove voucher
     * */
    public String controllerApplyVoucher = "apply-voucher";
    public String controllerRemoveVoucher = "remove-voucher";

    public final String controllerInvoice = "order-receipt";
}
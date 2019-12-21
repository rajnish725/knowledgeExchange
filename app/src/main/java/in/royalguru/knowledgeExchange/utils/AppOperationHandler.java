package in.royalguru.knowledgeExchange.utils;

/**
 * Created by Kalmeshwar on 04 Jul 2019 at 15:06.
 */
public class AppOperationHandler {
    public boolean isCustomerTagged = false;
    private static AppOperationHandler operationHandler;

    private AppOperationHandler() {

    }

    public static AppOperationHandler getInstance() {
        return operationHandler == null ? (operationHandler = new AppOperationHandler()) : operationHandler;
    }
}
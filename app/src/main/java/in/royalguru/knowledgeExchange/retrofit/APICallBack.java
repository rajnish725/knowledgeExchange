package in.royalguru.knowledgeExchange.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.SocketTimeoutException;

import in.kalmesh.projectbase.Debug;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by Kalmeshwar on 03 Jul 2017 at 12:43.
 */

/**
 * Custom API Callback class to handle response and failure from APIs
 *
 * @param <M> Custom Model class which are created for the handle response of the API
 */
public abstract class APICallBack<M> implements Callback<M> {
    private final String TAG = APICallBack.class.getSimpleName();

    /**
     * @param call     CAll for the API.
     * @param response response of the API
     */
    @Override
    public void onResponse(@NonNull Call<M> call, @NonNull final Response<M> response) {
        M model = response.body();

        Debug.printLogError(TAG, "URL: " + response.raw().request().url());

//        if (!response.isSuccessful()) {
//            JSONObject jsonObject = null;
//            try {
//                if (response.errorBody() != null) {
//                    jsonObject = new JSONObject(response.errorBody().string());
//                    String msg = jsonObject.getString("message");
//                    Debug.printLogError(TAG, "errorResp_msg: " + msg);
//                    Debug.printLogError(TAG, "errorResp: " + jsonObject.toString());
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        if (response.code() == 200) {
            if (model != null) success(model, call);
            else failure("Oops! Something went wrong. Please try again later.", -1);
        } else if (response.code() == 401) {
            closeProgressDialog();
            sessionTimeout();
        } else
            failure("Oops! Something went wrong. Please try again later.", response.code());
    }

    /**
     * @param call      call for API.
     * @param throwable throw an error
     */
    @Override
    public void onFailure(@NonNull Call<M> call, @NonNull Throwable throwable) {
        String errorType = "";
        String errorDesc = "";
        if (throwable instanceof SocketTimeoutException) {
            errorType = "Timeout";
            errorDesc = String.valueOf(throwable.getCause());
        } else if (throwable instanceof IOException) {
            errorType = "IOException";
            errorDesc = String.valueOf(throwable.getCause());
        } else if (throwable instanceof IllegalStateException) {
            errorType = "ConversionError";
            errorDesc = String.valueOf(throwable.getCause());
        } else {
            errorType = "Other Error";
            errorDesc = String.valueOf(throwable.getLocalizedMessage());
        }
        Debug.printLogError(TAG, "onFailure: " + "ErrorType- " + errorType + "\n ErrorDesc- " + errorDesc);
        onFailure(errorType);
    }

    /**
     * @param model   if not null then sends model class to the activity
     * @param request Request for API.
     */
    protected abstract void success(M model, Call<M> request);

    /**
     * @param errorMsg     if model class is null then send error message
     * @param responseCode response code of the API.
     */
    protected abstract void failure(String errorMsg, int responseCode);

    /**
     * @param failureReason issues from throwable errors above
     */
    protected abstract void onFailure(String failureReason);

    protected abstract void closeProgressDialog();

    //    /**
//     * call when API token is changed/mismatched
//     */
    protected abstract void sessionTimeout();
}
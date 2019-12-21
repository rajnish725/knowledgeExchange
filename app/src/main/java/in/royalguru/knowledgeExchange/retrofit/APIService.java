package in.royalguru.knowledgeExchange.retrofit;

/**
 * Created by Kalmeshwar on 3/14/2018.
 */

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import in.kalmesh.projectbase.Debug;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {
    private final long HTTP_TIMEOUT = 60;

    public String WebHost = "http://kalmesh.in/knowledge_exchange/public/";
    private APIService() {
    }

    private static APIService apiService = null;

    public static APIService getInstance() {
        return (apiService == null) ? apiService = new APIService() : apiService;
    }

    private Retrofit retrofit = null;

    public APIInterface init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(APIConstants.getInstance().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient(true))
                .build();

        return retrofit.create(APIInterface.class);
    }
//
//    public APIInterface getBaseUrlSyncData() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(APIConstants.getInstance().getBaseUrl())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(getClient(false))
//                .build();
//
//        return retrofit.create(APIInterface.class);
//    }

    private OkHttpClient getClient(boolean printLog) {
        final OkHttpClient.Builder okHttpClientBuilder = new
                OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
//        okHttpClientBuilder.addNetworkInterceptor(new AddHeaderInterceptor());
        if (printLog)
            okHttpClientBuilder.addInterceptor(logging);
        return okHttpClientBuilder.build();
    }

    public class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("header", "2");

            return chain.proceed(builder.build());
        }
    }

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
            new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NonNull String message) {
                    Debug.printLogW("api", "Requests: " + message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);
}
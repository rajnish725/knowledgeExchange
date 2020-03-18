package in.royalguru.knowledgeExchange.sessiondata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import in.kalmesh.projectbase.Debug;

/**
 * Created by Kalmeshwar on 09 Oct 2019 at 16:40.
 */
public class GoogleLogin implements GoogleApiClient.OnConnectionFailedListener {


    public static GoogleLogin getInstance(Context nContext) {
        return new GoogleLogin();
    }

    private final static String TAG = GoogleLogin.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    static GoogleApiClient mGoogleApiClient;
    boolean isLogout = false;
    GoogleSignInClient mgooglSignClient;

    public void init(Context mContext, Activity mActivity) {


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void signIn(Activity mActivity) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public boolean signOut(Activity mActivity) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                status -> {
                    Debug.printLogError(TAG, "logout: " + status.toString());
//                        updateUI(false);
                    revokeAccess(mActivity);
                });

        return true;
    }


   /* private void signOut1() {
        GoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }*/

  /*  public static void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient)
                .setResultCallback(status -> {


                });*/


    private void revokeAccess(Activity mActivity) {
        mgooglSignClient.revokeAccess()
                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public boolean logout(Activity mActivity) {

        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {


                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                                Log.d(TAG, "User Logged out");
                                isLogout = true;
                                revokeAccess(mActivity);
                            }
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d(TAG, "Google API Client Connection Suspended");
                isLogout = false;
            }
        });
        return isLogout;
    }

}

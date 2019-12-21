//package in.royalguru.knowledgeExchange.googleLogin;
//
//import android.app.Activity;
//import android.app.Person;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.common.Scopes;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Scope;
//import com.google.android.gms.common.api.Status;
//

//import in.royalguru.knowledgeExchange.listeners.AppOperationsListener;
//
//import static in.royalguru.knowledgeExchange.enums.ScreenTypes.GOOGLE;
//
//
///**
// * Created by Kalmeshwar on 21/2/17.
// */
//
//public class GPlusLoginActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
//    private Context mContext = null;
//    private AppOperationsListener listener = null;
//
//    // google plus related constants
//    private static final int RC_SIGN_IN = 1225;
//
//    /* Client used to interact with Google APIs. */
//    private GoogleApiClient mGoogleApiClient = null;
//
//    private boolean mIntentInProgress = false;
//
//    private boolean mSignInClicked = false;
//
//    private ConnectionResult mConnectionResult = null;
//
//    public GPlusLoginActivity(Context mContext, AppOperationsListener listener) {
//        this.mContext = mContext;
//        this.listener = listener;
//    }
//
//    public void onStart() {
//        mGoogleApiClient.connect();
//    }
//
//    public void onStop() {
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
//    }
//
//    public void onCreate() {
//        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this).addApi(Auth.GOOGLE_SIGN_IN_API)
//                .addScope(new Scope(Scopes.PROFILE))
//                .addScope(new Scope(Scopes.PLUS_LOGIN))
//                .build();
//    }
//
//    /**
//     * Sign-in into google
//     */
//    public void gPlusLogin() {
//        if (!mGoogleApiClient.isConnecting()) {
//            mSignInClicked = true;
//            resolveSignInError();
//        }
//    }
//
//    @Override
//    public void onConnected(Bundle arg0) {
//        mSignInClicked = false;
//        getProfileInformation();
//        updateUI(true);
//    }
//
//    @Override
//    public void onConnectionSuspended(int arg0) {
//        mGoogleApiClient.connect();
//        UserDetailsModel userdetailsmodel = new UserDetailsModel();
//        userdetailsmodel.setMessage("connectionSuspended");
//        listener.onListenOperation(GOOGLE, userdetailsmodel, null, null, false);
//        updateUI(false);
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        if (!connectionResult.hasResolution()) {
//            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), (Activity) mContext, 0).show();
//            return;
//        }
//
//        if (!mIntentInProgress) {
//            mConnectionResult = connectionResult;
//
//            if (mSignInClicked) {
//                resolveSignInError();
//            }
//        }
//    }
//
//    /**
//     * Updating the UI, showing/hiding buttons and profile layout
//     */
//    private void updateUI(boolean isSignedIn) {
//        if (isSignedIn) {
//            signOutFromGplus();
//        }
//    }
//
//    /**
//     * Method to resolve any signin errors
//     */
//    private void resolveSignInError() {
//        if (mConnectionResult != null) {
//            if (mConnectionResult.hasResolution()) {
//                try {
//                    mIntentInProgress = true;
//                    mConnectionResult.startResolutionForResult((Activity) mContext, RC_SIGN_IN);
//                } catch (IntentSender.SendIntentException e) {
//                    mIntentInProgress = false;
//                    mGoogleApiClient.connect();
//                }
//            }
//        } else {
//            UserDetailsModel UserDetailsModel = new UserDetailsModel();
//            UserDetailsModel.setMessage("AlreadyLogin");
//            listener.onListenOperation(GOOGLE, UserDetailsModel, null, null, false);
//        }
//    }
//
//    /**
//     * Fetching user's information name, email, profile pic
//     */
//   /* private void getProfileInformation() {
//        UserDetailsModel UserDetailsModel = new UserDetailsModel();
//        String firstName = "", lastName = "", fullName = "", fullNameWithMiddle = "", birthDate = "";
//        if (mGoogleApiClient != null) {
//            try {
//                if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
//                    Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
//
//                    fullName = currentPerson.getDisplayName();
//                    String personPhotoUrl = currentPerson.getImage().getUrl();
//                    String personGooglePlusProfile = currentPerson.getUrl();
//                    String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
//                    int gender = currentPerson.getGender();
//                    String id = currentPerson.getId();
//
//                    if (currentPerson.hasName()) {
//                        Person.Name name = currentPerson.getName();
//
//                        if (name.hasGivenName())
//                            firstName = name.getGivenName();
//
//                        if (name.hasFamilyName())
//                            lastName = name.getFamilyName();
//
//                        if (name.hasFormatted())
//                            fullNameWithMiddle = name.getFormatted();
//                    }
//
//                    if (currentPerson.hasBirthday())
//                        birthDate = currentPerson.getBirthday();
//
//                    if (currentPerson.hasAgeRange()) {
//                        UserDetailsModel.setUserAgeRange(currentPerson.getAgeRange().toString());
//                    }
//
//                    if (!personPhotoUrl.isEmpty()) {
//                        int findSzTezt = personPhotoUrl.indexOf("sz=");
//                        personPhotoUrl = personPhotoUrl.substring(0, findSzTezt).concat("sz=400");
//                    }
//
//                    UserDetailsModel.setUserId(id);
//                    UserDetailsModel.setUserFullName(fullName);
//                    UserDetailsModel.setUserFirstName(firstName);
//                    UserDetailsModel.setUserLastName(lastName);
//                    UserDetailsModel.setUserFullNameWithMiddle(fullNameWithMiddle);
//                    UserDetailsModel.setUserBirthDate(birthDate);
//                    UserDetailsModel.setUserEmailAddress(email);
//                    UserDetailsModel.setUserProfilePicture(personPhotoUrl);
//                    UserDetailsModel.setUserProfileUrl(personGooglePlusProfile);
//
//                    if (gender == 0) {
//                        UserDetailsModel.setUserGender("male");
//                    } else if (gender == 1) {
//                        UserDetailsModel.setUserGender("female");
//                    }
//
//                    listener.getUserDetails(loginType, UserDetailsModel);
//                } else {
//                    UserDetailsModel.setErrorMsg("NullInformation");
//                    listener.getUserDetails(loginType, UserDetailsModel);
//                }
//            } catch (Exception e) {
//                mGoogleApiClient = new GoogleApiClient.Builder(mContext)
//                        .addConnectionCallbacks(this)
//                        .addOnConnectionFailedListener(this).addApi(Plus.API)
//                        .addScope(Plus.SCOPE_PLUS_LOGIN).build();
//
//                e.printStackTrace();
//                UserDetailsModel.setErrorMsg("something went wrong");
//                listener.getUserDetails(loginType, UserDetailsModel);
//            }
//        }
//    }*/
//
//    private void signOutFromGplus() {
//        if (mGoogleApiClient.isConnected()) {
//            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//            mGoogleApiClient.disconnect();
//            mGoogleApiClient.connect();
//            updateUI(false);
//        }
//    }
//
//    /**
//     * Revoking access from google
//     */
//    private void revokeGplusAccess() {
//        if (mGoogleApiClient.isConnected()) {
//            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
//                    .setResultCallback(new ResultCallback<Status>() {
//                        @Override
//                        public void onResult(@NonNull Status arg0) {
//                            mGoogleApiClient.connect();
//                            updateUI(false);
//                        }
//                    });
//        }
//    }
//
//    public void onActivityResult(int requestCode, int responseCode, Intent data) {
//        mGoogleApiClient.connect();
//        if (requestCode == RC_SIGN_IN) {
//            if (responseCode != Activity.RESULT_OK) {
//                mSignInClicked = false;
//            }
//            mIntentInProgress = false;
//            if (!mGoogleApiClient.isConnecting()) {
//                mGoogleApiClient.connect();
//            }
//        }
//    }
//}
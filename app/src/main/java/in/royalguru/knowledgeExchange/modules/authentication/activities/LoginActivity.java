package in.royalguru.knowledgeExchange.modules.authentication.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import in.kalmesh.projectbase.AppActivity;
import in.kalmesh.projectbase.Debug;
import in.royalguru.knowledgeExchange.R;
import in.royalguru.knowledgeExchange.modules.Dashboard.DashboardActivity;
import in.royalguru.knowledgeExchange.retrofit.APICallBack;
import in.royalguru.knowledgeExchange.retrofit.APIService;
import in.royalguru.knowledgeExchange.retrofit.ServerConstants;
import in.royalguru.knowledgeExchange.sessiondata.GoogleLogin;
import in.royalguru.knowledgeExchange.sessiondata.SessionManager;
import in.royalguru.knowledgeExchange.utils.Utility;
import retrofit2.Call;

/**
 * Created by Kalmeshwar on 04 Oct 2019 at 14:18.
 */
public class LoginActivity extends AppActivity implements GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    private static GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private ProgressDialog pDialog;


    private SignInButton btn_sign_in;
    Button btn_logout;
    ImageView img_logo;
    Context mContext;


    @Override
    public int onCreateView() {
        return R.layout.app_login_screen;
    }

    @Override
    protected void preInitializeMethod() {
        mContext = this;


    }

    @Override
    protected void initUI() {

        btn_sign_in = findViewById(R.id.btn_sign_in);
        img_logo = findViewById(R.id.img_logo);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        btn_sign_in.setSize(SignInButton.SIZE_STANDARD);
        btn_sign_in.setScopes(gso.getScopeArray());


        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    protected void postInitializeMethod() {

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoogleLogin.getInstance(mContext).init(mContext, LoginActivity.this);
                if (new SessionManager(mContext).isLoggedIn()) {
                    signOut();

                } else {

                    signIn();
                }
            }
        });

      /*  btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "hii", Toast.LENGTH_SHORT).show();
                GoogleLogin.getInstance(mContext).init(mContext, LoginActivity.this);
                GoogleLogin.getInstance(mContext).signIn(LoginActivity.this);
            }
        });*/


    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void signOut() {
       /* Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Debug.printLogError(TAG, "logout: " + status.toString());
//                        updateUI(false);
                        GoogleLogin.getInstance(LoginActivity.this).revokeAccess();
                    }
                });*/

        GoogleLogin.getInstance(mContext).init(mContext, LoginActivity.this);
        GoogleLogin.getInstance(mContext).signIn(LoginActivity.this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(mContext, "connection Faild", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            try {
                Log.e(TAG, "display name: " + acct.getDisplayName());

                String personName = acct.getDisplayName();
                String email = acct.getEmail();

//                String personPhotoUrl = acct.getPhotoUrl().toString();

                gmailLoginApi(personName, email, "1");
                Log.e(TAG, "Name: " + personName + ", email: " + email
                );

/*
                img_logo.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(personPhotoUrl)
                        .thumbnail(0.5f)
                        .into(img_logo);*/
            } catch (Exception e) {
                Debug.printLogError(TAG, e.getMessage());
            }

//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);

            Debug.printLogError(TAG, "error somthing");
        }
    }


    void gmailLoginApi(String name, String email, String login_type) {

        if (Utility.getInstance().checkInternetConnection(mContext)) {

            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setCancelable(true);
            pDialog.show();
            APIService.getInstance().init().loginUser(name, email, login_type).enqueue(new APICallBack<LoginUserModel>() {
                @Override
                protected void success(LoginUserModel model, Call<LoginUserModel> request) {
                    closeprogressDialog();
                    if (model.getStatus().equalsIgnoreCase(ServerConstants.SUCCESS_RESPONSE)) {

                        SessionManager manager = new SessionManager(mContext);

                        manager.userLoginSession(model.getLoginDataModel().getUser_id(), model.getLoginDataModel().getUserName(), model.getLoginDataModel().getEmail(), true);
                        Intent intent = new Intent(mContext, DashboardActivity.class);
                        intent.putExtra("name", "login");
                        startActivity(intent);
                        finishAffinity();
                        Toast.makeText(mContext, "Login", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(mContext, "SOmething Went wrong", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                protected void failure(String errorMsg, int responseCode) {
                    if (pDialog != null) {
                        pDialog.dismiss();
                        pDialog = null;
                    }

                }

                @Override
                protected void onFailure(String failureReason) {
                    closeprogressDialog();


                }

                @Override
                protected void closeProgressDialog() {
                    closeprogressDialog();
                }

                @Override
                protected void sessionTimeout() {
                    closeprogressDialog();

                }
            });
        }
    }

    void closeprogressDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}


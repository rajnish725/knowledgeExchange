package in.royalguru.knowledgeExchange.modules.authentication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import in.kalmesh.projectbase.Debug;
import in.royalguru.knowledgeExchange.BuildConfig;
import in.royalguru.knowledgeExchange.modules.Dashboard.DashboardActivity;
import in.royalguru.knowledgeExchange.sessiondata.SessionManager;

/**
 * Created by Kalmeshwar on 30 Jan 2019 at 18:59.
 */
public class SplashActivity extends AppCompatActivity {
    private final String TAG = SplashActivity.class.getSimpleName();
    private Context mContext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        Debug.LOG = BuildConfig.DEBUG;
        new SessionManager(mContext).setPreferenceName();
        checkConditions();
    }

    private void checkConditions() {
        Intent intent = null;
        if (new SessionManager(mContext).isLoggedIn()) {
            intent = new Intent(mContext, DashboardActivity.class);
        } else {
            intent = new Intent(mContext, DashboardActivity.class);
        }
        startActivity(intent);
        finish();
    }
}

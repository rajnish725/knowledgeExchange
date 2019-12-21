package in.royalguru.knowledgeExchange.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.tapadoo.alerter.Alerter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.kalmesh.projectbase.Validator;
import in.royalguru.knowledgeExchange.R;

/**
 * Created by Kalmeshwar on 12 May 2018 at 12:57.
 */
public class Utility {
    private final String TAG = Utility.class.getSimpleName();

    private Utility() {
    }

    private static Utility utility = null;

    public static Utility getInstance() {
        return utility == null ? (utility = new Utility()) : utility;
    }

    private boolean isNetworkAvailable(Context mContext) {
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
                ConnectivityManager.TYPE_WIFI,
                ConnectivityManager.TYPE_BLUETOOTH};
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null &&
                        activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean checkInternetConnection(Context mContext) {
        if (isNetworkAvailable(mContext)) {
            return true;
        } else {
            Utility.getInstance().showToast(mContext, mContext.getString(R.string.noInterAvailable_text));
            return false;
        }
    }

    public void hideKeyboardActivity(Activity activity) {
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    public void hideKeyboardFragment(FragmentActivity activity, View rootView) {
        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);

        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void showAlert(Context mContext, String errorMsg) {
        Alerter.create((Activity) mContext)
                .setIcon(R.drawable.icon_alerter_vector)
                .setText(errorMsg)
                .setBackgroundColorRes(R.color.green_clr)
                .setTextAppearance(R.style.AlertTextAppearanceStyle)
                .show();
    }

    public void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public boolean checkPermissions(Activity act, String[] permissionName, int request_code) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissionName) {
            result = ContextCompat.checkSelfPermission(act, p);
            if (result != PackageManager.PERMISSION_GRANTED)
                listPermissionsNeeded.add(p);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(act, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), request_code);
            return false;
        }
        return true;
    }

    public String checkNullString(String stringValue) {
        if (!Validator.getInstance().isNotEmpty(stringValue))
            return "";
        return stringValue;
    }

    public void playSound(Context context) throws IllegalArgumentException,
            SecurityException,
            IllegalStateException,
            IOException {
        try {
            MediaPlayer player = MediaPlayer.create(context, R.raw.beep);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            player.setLooping(false);
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void vibrateDevice(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null)
            v.vibrate(100);
    }
}
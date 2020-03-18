package in.royalguru.knowledgeExchange.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import in.kalmesh.projectbase.Debug;
import in.kalmesh.projectbase.Validator;
import in.royalguru.knowledgeExchange.BuildConfig;
import in.royalguru.knowledgeExchange.application.ApplicationDetails;
import in.royalguru.knowledgeExchange.utils.DateTimeHelper;

/**
 * Created by Kalmeshwar on 12 May 2018 at 16:12.
 */
public class DeviceDetails {
    private final String TAG = DeviceDetails.class.getSimpleName();

    private DeviceDetails() {
    }

    private static DeviceDetails deviceDetails = null;

    public static DeviceDetails getInstance() {
        return (deviceDetails == null) ? deviceDetails = new DeviceDetails() : deviceDetails;
    }

    @SuppressLint("HardwareIds")
    public String getDeviceInfo() {
        JSONObject object = new JSONObject();
        try {
            object.put("MANUFACTURER", Utility.getInstance().checkNullString(Build.MANUFACTURER));
            object.put("BRAND", Utility.getInstance().checkNullString(Build.BRAND));
            object.put("MODEL", Utility.getInstance().checkNullString(Build.MODEL));
            object.put("OS_VERSION", Utility.getInstance().checkNullString(Build.VERSION.RELEASE));
            object.put("SDK_VERSION", Utility.getInstance().checkNullString(Build.VERSION.SDK));
            object.put("SDK_NUMBER", Utility.getInstance().checkNullString(Build.VERSION.SDK_INT + ""));
            object.put("ID", Utility.getInstance().checkNullString(Build.ID));
            object.put("SERIAL_NUMBER", Utility.getInstance().checkNullString(Build.SERIAL));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    public String getManufacturer() {
        if (Utility.getInstance().checkNullString(Build.MANUFACTURER)
                .equalsIgnoreCase("unknown")) {
            return Utility.getInstance().checkNullString(Build.MANUFACTURER)
                    + "_" + getBrand();
        } else {
            return Utility.getInstance().checkNullString(Build.MANUFACTURER);
        }
    }

    public String getBrand() {
        return Utility.getInstance().checkNullString(Build.BRAND);
    }

    public String getDeviceId() {
        Debug.printLogError(TAG, "temp_device_id: " + new DeviceIdentifier().findDeviceUniqueID());
        return new DeviceIdentifier().findDeviceUniqueID();
    }

    public String getModelNumber() {
        return Utility.getInstance().checkNullString(Build.MODEL);
    }

    public String getOSVersion() {
        return Utility.getInstance().checkNullString(Build.VERSION.RELEASE);
    }

    public String getOSName() {
        return Utility.getInstance().checkNullString(Build.VERSION.SDK_INT + "");
    }

    @SuppressLint("HardwareIds")
    public String getIMEINumber(Context mContext) {
        return Utility.getInstance().checkNullString(Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID));
    }

    public String getBuildDate() {
        Date date = new Date(BuildConfig.BUILD_TIME);
        String buildStatus = "D";
        if (!BuildConfig.DEBUG)
            buildStatus = "P";
        return DateTimeHelper.getInstance().getBuildTime(date) + " " + buildStatus;
    }

    class DeviceIdentifier {
        @SuppressLint("HardwareIds")
        String findDeviceUniqueID() {
            Context mContext = ApplicationDetails.getInstance().getContext();

            if (Validator.getInstance().isNotEmpty(Build.SERIAL)) {
                if (!Build.SERIAL.equalsIgnoreCase("unknown"))
                    return "id_serial_" + Build.SERIAL + "_" + getManufacturer();
            }

            String androidId = Settings.Secure.getString(
                    mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (Validator.getInstance().isNotEmpty(androidId))
                return "id_android_" + androidId + "_" + getManufacturer();

            return "id_unknown_device_" + getManufacturer();
        }
    }
}
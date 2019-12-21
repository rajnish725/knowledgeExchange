package in.royalguru.knowledgeExchange.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import in.kalmesh.projectbase.Debug;
import in.kalmesh.projectbase.Validator;

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

    public String getDeviceName() {
        return Utility.getInstance().checkNullString(Build.BRAND);
    }

    public String getManufacturer() {
        return Utility.getInstance().checkNullString(Build.MANUFACTURER);
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

    public String getIMEINumber(Context mContext) {
        return Utility.getInstance().checkNullString(android.provider.Settings.System.getString(mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId() {
        if (Validator.getInstance().isNotEmpty(Build.SERIAL)) {
            Debug.printLogError(TAG, "device_id: " + Build.SERIAL);
            return "id_" + Build.SERIAL + "_" + getManufacturer();
        }
        return "id_unknown_serial" + getManufacturer();
    }
}
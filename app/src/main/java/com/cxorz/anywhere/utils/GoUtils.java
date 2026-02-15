package com.cxorz.anywhere.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.location.provider.ProviderProperties;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;

public class GoUtils {
    public interface LocationCallback {
        void onSuccess(double lat, double lng);

        void onError(String msg);
    }

    // Check if WIFI is available
    public static boolean isWifiConnected(Context context) {
        // Starting from API 29, NetworkInfo is deprecated, using new method here
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Network nw = connectivityManager.getActiveNetwork();
        if (nw == null) {
            return false;
        }
        NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
        return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
    }

    public static boolean isWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    // Check if MOBILE network is available
    public static boolean isMobileConnected(Context context) {
        // Starting from API 29, NetworkInfo is deprecated, using new method here
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Network nw = connectivityManager.getActiveNetwork();
        if (nw == null) {
            return false;
        }
        NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
        return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
    }

    // Check if network is connected, but returns true even if connection has no internet access
    public static boolean isNetworkConnected(Context context) {
        // Starting from API 29, NetworkInfo is deprecated, using new method here
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Network nw = connectivityManager.getActiveNetwork();
        if (nw == null)
            return false;
        NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
        return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
    }

    // Check if network is available
    public static boolean isNetworkAvailable(Context context) {
        return ((isWifiConnected(context) || isMobileConnected(context)) && isNetworkConnected(context));
    }

    // Check if GPS is enabled
    public static boolean isGpsOpened(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // Check if mock location permission is enabled in developer options
    // Note: @SuppressLint("wrongconstant") added to handle lint errors for addTestProvider parameter values
    @SuppressLint("wrongconstant")
    public static boolean isAllowMockLocation(Context context) {
        boolean canMockPosition = false;
        int index;

        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);// Get LocationManager reference

            List<String> list = locationManager.getAllProviders();
            for (index = 0; index < list.size(); index++) {
                if (list.get(index).equals(LocationManager.GPS_PROVIDER)) {
                    break;
                }
            }

            if (index < list.size()) {
                // Note: Due to Android API issues, parameters below may show errors (these are real GPS parameters obtained through API, not arbitrary)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, true, false,
                            false, true, true, true, ProviderProperties.POWER_USAGE_HIGH,
                            ProviderProperties.ACCURACY_FINE);
                } else {
                    locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, true, false,
                            false, true, true, true, Criteria.POWER_HIGH, Criteria.ACCURACY_FINE);
                }
                canMockPosition = true;
            }

            // Mock location available
            if (canMockPosition) {
                // remove test provider
                locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, false);
                locationManager.removeTestProvider(LocationManager.GPS_PROVIDER);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return canMockPosition;
    }

    /**
     * Get application version name
     * 
     * @param context context
     * @return Current application version name
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();

            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);

            return packageInfo.versionName;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get App name
     * 
     * @param context Context
     * @return Name
     */
    public static String getAppName(Context context) {
        PackageManager pm = context.getPackageManager();
        // Get package info
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            int labelRes = applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String timeStamp2Date(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        return sdf.format(new Date(Long.parseLong(seconds + "000")));
    }

    // Dialog to prompt enabling mock location
    public static void showEnableMockLocationDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Enable Mock Location")
                .setMessage("Please set in \"Developer options → Select mock location app\"")
                .setPositiveButton("Settings", (dialog, which) -> {
                    try {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                })
                .show();
    }

    // Dialog to prompt enabling float window
    public static void showEnableFloatWindowDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Enable Float Window")
                .setMessage("For stability of location simulation, it is recommended to enable \"Show float window\" option")
                .setPositiveButton("Settings", (dialog, which) -> {
                    try {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + context.getPackageName()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .show();
    }

    // Show dialog to enable GPS
    public static void showEnableGpsDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Enable Location Service")
                .setMessage("Do you want to enable GPS location service?")
                .setPositiveButton("OK", (dialog, which) -> {
                    try {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .show();
    }

    // Toast to remind closing WIFI
    public static void showWifiWarningToast(Context context) {
        DisplayToast(context, "It is recommended to turn off WiFi and use mobile network to avoid location jumping");
    }

    public static void DisplayToast(Context context, String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }

    /* Timer class */
    public static class TimeCount extends CountDownTimer {
        private TimeCountListener mListener;

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// Parameters are total duration and interval time
        }

        @Override
        public void onFinish() {// Triggered when timer completes
            mListener.onFinish();
        }

        @Override
        public void onTick(long millisUntilFinished) { // Display during countdown
            mListener.onTick(millisUntilFinished);
        }

        public void setListener(TimeCountListener mListener) {
            this.mListener = mListener;
        }

        public interface TimeCountListener {
            void onTick(long millisUntilFinished);

            void onFinish();
        }
    }

    /**
     * Get location from IP address
     * 
     * @param ip       IP address (optional, empty for current IP)
     * @param callback Callback for result
     */
    public static void getIpLocation(String ip, final LocationCallback callback) {
        String url = "https://ipwho.is/" + (ip == null ? "" : ip);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onError(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (callback != null) {
                        callback.onError("Unexpected code " + response);
                    }
                    return;
                }

                try {
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    if ("fail".equals(jsonObject.optString("status"))) {
                        if (callback != null) {
                            callback.onError(jsonObject.optString("message"));
                        }
                        return;
                    }

                    double lat = jsonObject.getDouble("latitude");
                    double lon = jsonObject.getDouble("longitude");
                    if (callback != null) {
                        callback.onSuccess(lat, lon);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onError(e.getMessage());
                    }
                }
            }
        });
    }

}

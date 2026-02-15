package com.example.detector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView logTv;
    private ScrollView scrollView;
    private LocationManager locationManager;
    private WifiManager wifiManager;
    private TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Dynamically create layout
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        Button btnCheck = new Button(this);
        btnCheck.setText("Start Full Detection");
        layout.addView(btnCheck);

        Button btnClear = new Button(this);
        btnClear.setText("Clear Log");
        layout.addView(btnClear);

        scrollView = new ScrollView(this);
        logTv = new TextView(this);
        logTv.setText("Click the button above to start detection...\nMake sure this app is checked in LSPosed!\n");
        scrollView.addView(logTv);
        layout.addView(scrollView);

        setContentView(layout);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        btnCheck.setOnClickListener(v -> startDetection());
        btnClear.setOnClickListener(v -> logTv.setText(""));

        checkPermissions();
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.READ_PHONE_STATE
            }, 100);
        }
    }

    private void log(String msg) {
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        runOnUiThread(() -> {
            logTv.append("\n[" + time + "] " + msg);
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        });
    }

    @SuppressLint("MissingPermission")
    private void startDetection() {
        log("=== Start Detection ===");

        // 1. Check Provider list
        List<String> providers = locationManager.getAllProviders();
        log("Provider list: " + providers.toString());
        if (providers.contains("gps_test") || providers.contains("mock")) {
            log("❌ Warning: Detected Mock Provider!");
        } else {
            log("✅ Provider list looks normal.");
        }

        // 2. Check location information (GPS)
        log("Requesting GPS location...");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                boolean isMock = false;
                if (Build.VERSION.SDK_INT >= 18) isMock = location.isFromMockProvider();
                if (Build.VERSION.SDK_INT >= 31) isMock = isMock || location.isMock();
                
                Bundle extras = location.getExtras();
                int sats = -1;
                if (extras != null) {
                    sats = extras.getInt("satellites", -1);
                }

                log("📍 Location update: " + location.getLatitude() + ", " + location.getLongitude());
                if (isMock) {
                    log("❌ Exposed: Detected isFromMockProvider=true");
                } else {
                    log("✅ Masked successfully: isFromMockProvider=false");
                }
                
                if (sats >= 0) {
                    log("✅ extras.satellites = " + sats);
                } else {
                    log("❓ No satellites in extras");
                }
                locationManager.removeUpdates(this);
            }
            @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override public void onProviderEnabled(@NonNull String provider) {}
            @Override public void onProviderDisabled(@NonNull String provider) {}
        });

        // 3. Check GpsStatus (legacy)
        log("Checking GpsStatus (API < 24)...");
        try {
            // Note: New Hook modules no longer simulate the deprecated GpsStatus, so data may be unavailable here
            locationManager.addGpsStatusListener(event -> {
                if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                    GpsStatus status = locationManager.getGpsStatus(null);
                    int count = 0;
                    if (status != null) {
                        for (Object s : status.getSatellites()) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        log("⚠️ GpsStatus captured satellites: " + count + " (legacy API)");
                    } else {
                        log("ℹ️ GpsStatus satellite count is 0 (expected, deprecated)");
                    }
                    locationManager.removeGpsStatusListener(this::onGpsStatusChanged);
                }
            });
            GpsStatus status = locationManager.getGpsStatus(null);
        } catch (Exception e) {
            log("Skipped GpsStatus detection: " + e.getMessage());
        }

        // 4. Check GnssStatus (new version)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            log("Checking GnssStatus (API 24+)...");
            locationManager.registerGnssStatusCallback(new GnssStatus.Callback() {
                @Override
                public void onSatelliteStatusChanged(@NonNull GnssStatus status) {
                    int count = status.getSatelliteCount();
                    if (count > 0) {
                        log("✅ GnssStatus captured satellites: " + count + " satellites");
                        // Check signal-to-noise ratio
                        float cn0 = status.getCn0DbHz(0);
                        log("ℹ️ Satellite #1 signal strength: " + cn0);
                    } else {
                        log("❌ GnssStatus satellite count is 0!");
                    }
                    locationManager.unregisterGnssStatusCallback(this);
                }
            }, new Handler(Looper.getMainLooper()));
        }

        // 5. Check Wi-Fi
        log("Checking Wi-Fi...");
        List<ScanResult> wifiList = wifiManager.getScanResults();
        if (wifiList == null || wifiList.isEmpty()) {
            log("✅ Wi-Fi list is empty (Hook effective)");
        } else {
            log("❌ Warning: Scanned " + wifiList.size() + " Wi-Fi access points! (Hook failed)");
        }

        // 6. Check cell towers
        log("Checking cell towers...");
        List<CellInfo> cellList = telephonyManager.getAllCellInfo();
        if (cellList == null || cellList.isEmpty()) {
            log("✅ Cell tower list is empty (Hook effective)");
        } else {
            log("❌ Warning: Scanned " + cellList.size() + " cell towers! (Hook failed)");
        }
    }

    private void onGpsStatusChanged(int event) {}
}

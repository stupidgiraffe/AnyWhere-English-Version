package com.cxorz.anywhere;

import android.app.Application;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.ConsolePrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy;
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy;
import com.elvishew.xlog.printer.file.naming.ChangelessFileNameGenerator;

import java.io.File;

import androidx.preference.PreferenceManager;
import org.osmdroid.config.Configuration;

public class GoApplication extends Application {
    public static final String APP_NAME = "AnyWhere";
    public static final String LOG_FILE_NAME = APP_NAME + ".log";
    private static final long MAX_TIME = 1000 * 60 * 60 * 24 * 3; // 3 days

    @Override
    public void onCreate() {
        super.onCreate();

        initXlog();
        
        // OSMDroid configuration
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        Configuration.getInstance().setUserAgentValue(getPackageName());
    }

    /**
     * Initialize XLog.
     */
    private void initXlog() {
        File logPath = getExternalFilesDir("Logs");
        if (logPath != null) {
            LogConfiguration config = new LogConfiguration.Builder()
                    .logLevel(LogLevel.ALL)
                    .tag(APP_NAME)                                         // Specify TAG, default is "X-LOG"
                    .enableThreadInfo()                                    // Enable printing thread info, disabled by default
                    .enableStackTrace(2)                                   // Enable printing call stack info with depth 2, disabled by default
                    .enableBorder()                                        // Enable printing log borders, disabled by default
                    .build();

            Printer consolePrinter = new ConsolePrinter();                  // Printer that prints logs to console via System.out
            Printer filePrinter = new FilePrinter                           // Printer that prints logs to file
                    .Builder(logPath.getPath())                             // Specify path to save log files
                    .fileNameGenerator(new ChangelessFileNameGenerator(LOG_FILE_NAME))         // Specify log file name generator, default is ChangelessFileNameGenerator("log")
                    .backupStrategy(new NeverBackupStrategy())              // Specify log file backup strategy, default is FileSizeBackupStrategy(1024 * 1024)
                    .cleanStrategy(new FileLastModifiedCleanStrategy(MAX_TIME))     // Specify log file cleanup strategy, default is NeverCleanStrategy()
                    .build();
            XLog.init(config, consolePrinter, filePrinter);
        }
    }
}

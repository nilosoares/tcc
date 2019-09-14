import java.io.IOException;

import java.lang.SecurityException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class LoggerHelper {

    public static Map<String, Logger> loggers = new HashMap<String, Logger>();

    /**
     *
     * @param entry
     * @return
     */
    private static String getFileName(String entry) {
        return "var/logs/" + entry + ".log";
    }

    /**
     *
     * @param entry
     * @return
     */
    public static Logger getInstance(String entry) {
        Logger logger = loggers.get(entry);

        if (logger == null) {
            // Create a new Logger
            try {
                logger = Logger.getLogger(entry);

                FileHandler fh = new FileHandler(getFileName(entry));
                logger.addHandler(fh);

                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);

                loggers.put(entry, logger);

            } catch (SecurityException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return logger;
    }

    /**
     *
     * @param String log
     */
    public static void addLog(String entry, String message) {
        Logger logger = getInstance(entry);
        logger.info(message);
    }

    /**
     *
     * @param entry
     */
    public static void save(String entry) {
        String timestamp = DateHelper.format("yyyyMMdd_Hms_S");
        String currentFileName = getFileName(entry);
        String newFileName = getFileName(entry + "_" + timestamp);

        FileSystemHelper.copyFile(currentFileName, newFileName);

        FileSystemHelper.deleteFile(currentFileName);
    }

}
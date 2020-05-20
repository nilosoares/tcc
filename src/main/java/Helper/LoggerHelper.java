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
    public static Logger getInstance(String entry) {
        Logger logger = loggers.get(entry);

        if (logger == null) {
            // Create a new Logger
            try {
                logger = Logger.getLogger(entry);

                String fileName = entry + "_" + DateHelper.format("yyyyMMdd_Hms_S");
                FileHandler fh = new FileHandler("output/logs/" + fileName + ".log");
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

}
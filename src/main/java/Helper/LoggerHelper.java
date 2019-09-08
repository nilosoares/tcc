import java.io.IOException;
import java.lang.SecurityException;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class LoggerHelper {

    /**
     *
     */
    private static final LoggerHelper INSTANCE = new LoggerHelper();

    /**
     *
     */
    private final Logger logger = Logger.getLogger("myLogger");

    /**
     *
     */
    private LoggerHelper() {
        try {
            FileHandler fh = new FileHandler("var/logs/tpch.log");

            logger.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     * @return
     */
    public static Logger getInstance() {
        return INSTANCE.logger;
    }

    /**
     *
     * @param String log
     */
    public static void info(String log) {
        INSTANCE.logger.info(log);
    }

    /**
     *
     * @param String log
     */
    public static void warning(String log) {
        INSTANCE.logger.warning(log);
    }

    /**
     *
     * @param String log
     */
    public static void severe(String log) {
        INSTANCE.logger.severe(log);
    }

}
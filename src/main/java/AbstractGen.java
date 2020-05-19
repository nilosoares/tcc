import java.lang.System;
import java.nio.file.Path;

/**
 * @author Nilo Soares
 */
public abstract class AbstractGen {

    protected Path getTemplate(String templateName, String sourceFolder, String outputFolder) {
        Path path = null;

        try {
            String templateFilePath = "resources/tpc-h-mongo/" + sourceFolder + "/" + templateName + ".js";

            String destinationFileName = templateName + "_" + DateHelper.format("yyyyMMdd_Hms_S");
            String destinationFilePath = "output/" + outputFolder + "/" + destinationFileName + ".js";

            path = FileSystemHelper.copyFile(templateFilePath, destinationFilePath);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return path;
    }

}
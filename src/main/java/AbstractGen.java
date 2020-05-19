import java.lang.System;
import java.nio.file.Path;

/**
 * @author Nilo Soares
 */
public abstract class AbstractGen {

    protected Path getTemplate(AbstractQuery query, String sourceFolder, String outputFolder) {
        Path path = null;

        try {
            String templateFilePath = "resources/tpc-h-mongo/" + sourceFolder + "/" + query.getName() + ".js";

            String destinationFileName = query.getName() + "_" + DateHelper.format("yyyyMMdd_Hms_S");
            String destinationFilePath = "output/" + outputFolder + "/" + destinationFileName + ".js";

            path = FileSystemHelper.copyFile(templateFilePath, destinationFilePath);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return path;
    }

}
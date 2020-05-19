import java.nio.file.Path;

/**
 * @author Nilo Soares
 */
public class IndexGen extends AbstractGen {

    public Path generateIndex(String indexName) {
        Path indexPath = this.getTemplate(indexName, "index_templates", "mongo_index");

        return indexPath;
    }

}
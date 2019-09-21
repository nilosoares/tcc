import java.lang.System;

/**
 * @author Nilo Soares
 */
public class Run {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) {
        String[] queries = {
            "Q1",
            "Q8",
            "Q15",
            "Q20",
            "Q22",
            "Q21",
        };

        int nbQueries = queries.length;
        int nbTests = 5;

        for (int i = 0; i < nbQueries; i++) {
            for (int j = 1; j <= nbTests; j++) {
                QueryExec.query(queries[i]);
            }
        }
    }

}
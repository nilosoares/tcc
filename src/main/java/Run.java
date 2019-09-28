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
            "Q21",
            "Q22"
        };
        int nbQueries = queries.length;
        int nbOfTests = 5;

        for (int i = 0; i < nbQueries; i++) {
            QueryExec queryExec = new QueryExec(queries[i]);
            queryExec.execute(nbOfTests);
        }
    }

}
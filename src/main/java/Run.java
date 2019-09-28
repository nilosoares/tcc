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
//            "Q8",
//            "Q15",
//            "Q20",
//            "Q22",
//            "Q21",
        };
        int nbQueries = queries.length;
        int nbOfTests = 2;

        for (int i = 0; i < nbQueries; i++) {
            QueryExec queryExec = new QueryExec(queries[i]);
            queryExec.execute(nbOfTests);
        }
    }

}
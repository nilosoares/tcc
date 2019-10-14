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
            // Alain's queries
            "Q1",
            //"Q3",
            //"Q5",
            //"Q6",
            //"Q10",
            //"Q12",
            //"Q14",
            //"Q17",

             // Nico's queries
            //"Q1",
            //"Q3",
            //"Q8",
            //"Q15",
            //"Q20",
            //"Q21",
            //"Q22"
        };
        int nbQueries = queries.length;

        for (int i = 0; i < nbQueries; i++) {
            AbstractQuery query = QueryFactory.createFromName(queries[i]);

            QueryExec queryExec = new QueryExec();
            queryExec.execute(query, true);
        }
    }

}
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
            "Q1_Mod",
            "Q3_Mod",
            "Q5_Mod",
            "Q6_Mod",
            "Q10_Mod",
            "Q12_Mod",
            "Q14_Mod",
            "Q19_Mod"

             // Nico's queries
            //"Q1",
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
            queryExec.execute(query, false);
        }
    }

}
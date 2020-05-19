import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;

class Q10 extends AbstractQuery {

    public int getNumber() {
        return 10;
    }

    public String getName() {
        return "Q10";
    }

    public int getNbOfTests() {
        return 13;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        Calendar startDate = RandomHelper.getRandomDate(93, 0, 1, 95, 9, 1);
        startDate.set(Calendar.DATE, 1);
        parameters.put("__PARAM_START_DATE__", startDate);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.MONTH, 3);
        parameters.put("__PARAM_END_DATE__", endDate);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        names.add("P_8");
        names.add("C_104");

        return names;
    }

}
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;

class Q15 extends AbstractQuery {

    public int getNumber() {
        return 15;
    }

    public String getName() {
        return "Q15";
    }

    public int getNbOfTests() {
        return 5;
    }

    public QueryParameters getParameters() {
        QueryParameters parameters = new QueryParameters();

        Calendar startDate = RandomHelper.getRandomDate(93, 0, 1, 97, 9, 1);
        startDate.set(Calendar.DATE, 1);
        parameters.put("__PARAM_START_DATE__", startDate);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.MONTH, 3);
        parameters.put("__PARAM_END_DATE__", endDate);

        return parameters;
    }

    protected ArrayList<String> getIndexes() {
        ArrayList<String> names = new ArrayList();

        return names;
    }

}
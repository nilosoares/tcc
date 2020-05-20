import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

class QueryParameters extends HashMap<String, Object> {

    public Map<String, String> getAllAsStrings() {
        Map<String, String> stringParameters = new HashMap<String, String>();

        for (Map.Entry<String, Object> parameter : this.entrySet()) {
            String parameterKey = parameter.getKey();

            String parameterValue;
            if (parameter.getValue() instanceof Calendar) {
                parameterValue = DateHelper.jsFormat((Calendar) parameter.getValue());
            } else {
                parameterValue = parameter.getValue().toString();
            }

            stringParameters.put(parameterKey, parameterValue);
        }

        return stringParameters;
    }

}
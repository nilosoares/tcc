import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractQuery {

    public abstract int getNumber();

    public abstract String getName();

    public abstract int getNbOfTests();

    public abstract Map<String, Object> getParameters();

    protected abstract ArrayList<String> getIndexes();

}
import java.util.ArrayList;

abstract class AbstractQuery {

    public abstract int getNumber();

    public abstract String getName();

    public abstract int getNbOfTests();

    public abstract QueryParameters getParameters();

    protected abstract ArrayList<String> getIndexes();

}
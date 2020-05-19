abstract class AbstractQuery {

    public abstract int getNumber();

    public abstract String getName();

    public abstract int getNbOfTests();

    public abstract QueryParameters getParameters();

    protected abstract QueryIndexes getIndexes();

}
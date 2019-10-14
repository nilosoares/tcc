import java.lang.Class;

class QueryFactory {

    /**
     *
     * @param name
     * @return
     */
    public static AbstractQuery createFromName(String name) {
        AbstractQuery query = null;

        try {
            query = (AbstractQuery) Class.forName(name).newInstance();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return query;
    }

}
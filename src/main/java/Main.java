import org.apache.log4j.Logger;

public class Main
{
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(final String[] args)
    {
        log.info("------------- Starting Migration -------------");
        new ThesisMigration().executeMigrations();
        log.info("------------- Migration Finished -------------");
    }
}

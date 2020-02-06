package utils;

import migration.ThesisMigration;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class ConnectionHelper
{
    private static Logger log = Logger.getLogger(ConnectionHelper.class.getName());

    private final DatabaseType databaseType;
    private final String user;
    private final String password;
    private URI uri;

    public ConnectionHelper(final DatabaseType databaseType, final DatabaseInformation databaseInformation)
    {
        this.databaseType = databaseType;

        try
        {
            this.uri = new URI(this.databaseType.getScheme(),
                    null,
                    databaseInformation.getHost(),
                    databaseInformation.getPort(),
                    String.format("/%s", databaseInformation.getDatabaseName()),
                    null,
                    null);
        }
        catch (final URISyntaxException e)
        {
            e.printStackTrace();
        }

        this.user = databaseInformation.getUser();
        this.password = databaseInformation.getPassword();
    }

    public Connection createConnection()
    {
        try
        {
            Class.forName(this.databaseType.getDriver());
        }
        catch (final ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        Connection connection = null;

        try
        {
            log.info(String.format("Trying to connect to %s", this.uri.toString()));
            connection = DriverManager.getConnection(this.uri.toString(), this.user, this.password);
        }
        catch (final SQLException e)
        {
            log.error(String.format("""
                    SQLException: %s;
                    SQLState: %s;
                    VendorError:%s""",
                    e.getMessage(), e.getSQLState(), e.getErrorCode()));
            e.printStackTrace();
        }

        return connection;
    }
}

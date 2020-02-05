package utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Properties;

public class ConnectionHelper
{
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

        Connection c = null;

        try
        {
            c = DriverManager.getConnection(this.uri.toString(), this.user, this.password);
        }
        catch (final SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            e.printStackTrace();
        }

        return c;
    }
}

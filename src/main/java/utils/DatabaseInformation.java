package utils;

import com.mysql.cj.exceptions.NumberOutOfRange;

public class DatabaseInformation
{
    private final String host;
    private final String databaseName;
    private final String user;
    private final String password;

    private int port;

    public DatabaseInformation(final String host, final String databaseName, final String user, final String password, final int port)
    {
        this.host = host;
        this.databaseName = databaseName;
        this.user = user;
        this.password = password;

        if (port < 0 || port > 65535)
            throw new NumberOutOfRange("A port must be between 0 and 65535");
        else
            this.port = port;
    }

    public String getHost()
    {
        return host;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }

    public String getUser()
    {
        return user;
    }

    public String getPassword()
    {
        return password;
    }

    public int getPort()
    {
        return port;
    }
}
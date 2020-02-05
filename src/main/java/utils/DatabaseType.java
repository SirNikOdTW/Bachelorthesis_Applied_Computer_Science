package utils;

public enum DatabaseType
{
    MARIADB("org.mariadb.jdbc.Driver", "jdbc:mariadb"),
    MYSQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql"),
    POSTGRESQL("org.postgresql.Driver", "jdbc:postgresql");

    private final String driver;
    private final String scheme;

    DatabaseType(final String driver, final String scheme)
    {
        this.driver = driver;
        this.scheme = scheme;
    }

    public String getDriver()
    {
        return driver;
    }

    public String getScheme()
    {
        return scheme;
    }
}
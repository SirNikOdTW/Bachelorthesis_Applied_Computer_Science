package migration;

import org.apache.log4j.Logger;
import utils.ConnectionHelper;
import utils.DatabaseInformation;
import utils.DatabaseType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThesisMigration
{
    private static Logger log = Logger.getLogger(ThesisMigration.class.getName());

    private List<ETL> migrations;

    private Connection mariadb;
    private Connection mysql;
    private Connection postgresql;

    public ThesisMigration()
    {
        log.info("\n--- Creating connections ---");
        final var mariaInfo = new DatabaseInformation("localhost", "sourcedb1", "test", "test", 25003);
        this.mariadb = new ConnectionHelper(DatabaseType.MARIADB, mariaInfo).createConnection();

        final var mysqlInfo = new DatabaseInformation("localhost", "sourcedb2", "test", "test", 25002);
        this.mysql = new ConnectionHelper(DatabaseType.MYSQL, mysqlInfo).createConnection();

        final var postgresInfo = new DatabaseInformation("localhost", "targetdb", "test", "test", 25001);
        this.postgresql= new ConnectionHelper(DatabaseType.POSTGRESQL, postgresInfo).createConnection();

        this.migrations = new ArrayList<>();
        this.migrations.add(new PlayerMigration(null, this.postgresql));
        this.migrations.add(new AbilityMigration(this.mysql, this.postgresql));
        this.migrations.add(new PlayerAbilitiesMigration(this.postgresql, this.postgresql));
        this.migrations.add(new CharacterMigration(this.mariadb, this.postgresql));
    }

    public void executeMigrations()
    {
        log.info("\n----- Starting migrations for each migration step -----\n");
        this.migrations.forEach(ETL::migrate);

        try
        {
            log.info("\n--- Closing connections ---\n");
            this.mariadb.close();
            this.mysql.close();
            this.postgresql.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}

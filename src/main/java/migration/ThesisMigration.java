package migration;

import org.apache.log4j.Logger;
import utils.ConnectionHelper;
import utils.DatabaseInformation;
import utils.DatabaseType;
import utils.ETL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThesisMigration
{
    private static final Logger log = Logger.getLogger(ThesisMigration.class.getName());

    private final List<ETL> migrations;

    private final Connection mariadb;
    private final Connection mysql;
    private final Connection postgresql;

    public ThesisMigration()
    {
        log.info("\n--- Creating connections ---");
        final var mariaInfo = new DatabaseInformation("localhost", "sourcedb1", "test", "test", 25003);
        this.mariadb = new ConnectionHelper(DatabaseType.MARIADB, mariaInfo).createConnection();

        final var mysqlInfo = new DatabaseInformation("localhost", "sourcedb2", "test", "test", 25002);
        this.mysql = new ConnectionHelper(DatabaseType.MYSQL, mysqlInfo).createConnection();

        final var postgresInfo = new DatabaseInformation("localhost", "targetdb", "test", "test", 25001);
        this.postgresql= new ConnectionHelper(DatabaseType.POSTGRESQL, postgresInfo).createConnection();

        try
        {
            this.mariadb.setAutoCommit(false);
            this.mysql.setAutoCommit(false);
            this.postgresql.setAutoCommit(false);
        }
        catch (final SQLException e)
        {
            e.printStackTrace();
        }

        this.migrations = new ArrayList<>();
        this.migrations.add(new PlayerMigration(null, this.postgresql));
        this.migrations.add(new AbilityMigration(this.mysql, this.postgresql));
        this.migrations.add(new PlayerAbilitiesMigration(this.postgresql, this.postgresql));
        this.migrations.add(new CharacterMigration(this.mariadb, this.postgresql));
        this.migrations.add(new GameobjectMigration(this.mariadb, this.postgresql));
        this.migrations.add(new QuestMigration(this.mariadb, this.postgresql));
        this.migrations.add(new ModMigration(this.mariadb, this.postgresql));
        this.migrations.add(new CharacterInventoryMigration(this.mariadb, this.postgresql));
        this.migrations.add(new RelationshipMigration(this.mysql, this.postgresql));
        this.migrations.add(new ActiveQuestsMigration(this.mysql, this.postgresql));
        this.migrations.add(new InventoryMigration(this.mysql, this.postgresql));
    }

    public void executeMigrations()
    {
        log.info("""

                ----- Starting migrations for each migration step -----
                """);
        this.migrations.forEach(ETL::migrate);

        try
        {
            log.info("""

                    --- Closing connections ---
                    """);
            this.mariadb.close();
            this.mysql.close();
            this.postgresql.close();
        }
        catch (final SQLException e)
        {
            e.printStackTrace();
        }
    }
}

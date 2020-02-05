import data.source.PersonSource;
import data.target.PersonTarget;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(final String[] args) throws SQLException
    {
//        mysqlDb();
//        postgresqlDb();
//        mariaDb();

        var p = testExtract();
        var pt = testTransform(p);
        testLoad(pt);
    }

    private static List<PersonSource> testExtract() throws SQLException
    {
        final var dbInfo = new DatabaseInformation("localhost", "sourcedb1", "test", "test", 5435);
        final var connection = new ConnectionHelper(DatabaseType.MARIADB, dbInfo).createConnection();

        DataStorer<PersonSource> personSourceDataStorer = (rs) -> {
            var persons = new ArrayList<PersonSource>();
            while (rs.next())
            {
                persons.add(new PersonSource(rs.getInt("personId"),
                        rs.getString("name"),
                        rs.getBoolean("mortal")));
            }
            return persons;
        };

        var sql = "select * from person;";

        return new Extractor<>(connection, personSourceDataStorer, sql).doExtract();
    }

    private static List<PersonTarget> testTransform(List<PersonSource> persons)
    {
        DataTransformer<PersonSource, PersonTarget> personTransformer = (personSource) -> {
            return new PersonTarget(personSource.getPersonId(),
                    personSource.getName(),
                    personSource.isMortal());
        };

        return new Transformer<>(personTransformer, persons).doTransform();
    }

    private static void testLoad(List<PersonTarget> transformedData)
    {
        final var dbInfo = new DatabaseInformation("localhost", "targetdb", "test", "test", 5432);
        final var connection = new ConnectionHelper(DatabaseType.POSTGRESQL, dbInfo).createConnection();

        StatementPreparer<PersonTarget> statementPreparer = (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getPersonId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setBoolean(3, data.isMortal());
        };

        var sql = "insert into person values (?, ?, ?)";

        new Loader<>(connection, statementPreparer, transformedData, sql).doLoad();
    }
}

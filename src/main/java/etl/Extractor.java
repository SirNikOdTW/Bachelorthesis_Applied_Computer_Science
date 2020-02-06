package etl;

import data.SourceDataset;
import org.apache.log4j.Logger;
import utils.DataStorer;
import utils.StatementPreparerExtractor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Extractor<T extends SourceDataset>
{
    private static Logger log = Logger.getLogger(Extractor.class.getName());

    private Connection sourceDatabase;
    private DataStorer<T> dataStorer;
    private StatementPreparerExtractor statementPreparer;
    private String sql;

    public Extractor(Connection sourceDatabase, DataStorer<T> dataStorer,
                     StatementPreparerExtractor statementPreparer, String sql)
    {
        this.sourceDatabase = sourceDatabase;
        this.dataStorer = dataStorer;
        this.statementPreparer = statementPreparer;
        this.sql = sql;
    }

    public List<T> doExtract()
    {
        try
        {
            var preparedStatement = this.sourceDatabase.prepareStatement(sql);
            this.statementPreparer.doPrepare(preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            log.info(String.format("--- Data extracted with '%s' ---", this.sql));
            return this.dataStorer.doStore(resultSet);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}

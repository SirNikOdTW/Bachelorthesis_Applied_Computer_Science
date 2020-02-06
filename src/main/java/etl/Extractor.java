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
    private static final Logger log = Logger.getLogger(Extractor.class.getName());

    private final Connection sourceDatabase;
    private final DataStorer<T> dataStorer;
    private final StatementPreparerExtractor statementPreparer;
    private final String sql;

    public Extractor(final Connection sourceDatabase, final DataStorer<T> dataStorer,
                     final StatementPreparerExtractor statementPreparer, final String sql)
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
            final var preparedStatement = this.sourceDatabase.prepareStatement(sql);
            this.statementPreparer.doPrepare(preparedStatement);
            final var resultSet = preparedStatement.executeQuery();
            log.info(String.format("--- Data extracted with '%s' ---", this.sql));
            return this.dataStorer.doStore(resultSet);
        }
        catch (final SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}

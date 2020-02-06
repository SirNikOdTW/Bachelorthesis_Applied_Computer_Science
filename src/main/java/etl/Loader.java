package etl;

import data.TargetDataset;
import org.apache.log4j.Logger;
import utils.StatementPreparerLoader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Loader<T extends TargetDataset>
{
    private static final Logger log = Logger.getLogger(Loader.class.getName());

    private final Connection targetDatabase;
    private final StatementPreparerLoader<T> statementPreparerLoader;
    private final List<T> transformedData;
    private final String sql;

    public Loader(final Connection targetDatabase, final StatementPreparerLoader<T> statementPreparerLoader, final List<T> transformedData,
                  final String sql)
    {
        this.targetDatabase = targetDatabase;
        this.statementPreparerLoader = statementPreparerLoader;
        this.transformedData = transformedData;
        this.sql = sql;
    }

    public void doLoad()
    {
        try
        {
            for (final T transformedDatum : this.transformedData)
            {
                log.info(String.format("Load data into target: %s", transformedDatum));
                final var preparedStatement = this.targetDatabase.prepareStatement(this.sql);
                this.statementPreparerLoader.doPrepare(preparedStatement, transformedDatum);
                preparedStatement.executeUpdate();
            }
            log.info(String.format("--- Data loaded into target with '%s' ---", this.sql));
        }
        catch (final SQLException e)
        {
            e.printStackTrace();
        }
    }
}

package etl;

import data.SourceDataset;
import data.TargetDataset;
import org.apache.log4j.Logger;
import utils.StatementPreparerLoader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Loader<T extends TargetDataset>
{
    private static Logger log = Logger.getLogger(Loader.class.getName());

    private Connection targetDatabase;
    private StatementPreparerLoader<T> statementPreparerLoader;
    private List<T> transformedData;
    private String sql;

    public Loader(Connection targetDatabase, StatementPreparerLoader<T> statementPreparerLoader, List<T> transformedData,
                  String sql)
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
            for (T transformedDatum : this.transformedData)
            {
                log.info(String.format("Load data into target: %s", transformedDatum));
                var preparedStatement = this.targetDatabase.prepareStatement(this.sql);
                this.statementPreparerLoader.doPrepare(preparedStatement, transformedDatum);
                preparedStatement.executeUpdate();
            }
            log.info(String.format("--- Data loaded into target with '%s' ---", this.sql));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}

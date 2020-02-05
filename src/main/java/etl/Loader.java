package etl;

import data.Dataset;
import utils.StatementPreparer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Loader<T extends Dataset>
{
    private Connection targetDatabase;
    private StatementPreparer<T> statementPreparer;
    private List<T> transformedData;
    private String sql;

    public Loader(Connection targetDatabase, StatementPreparer<T> statementPreparer, List<T> transformedData,
                  String sql)
    {
        this.targetDatabase = targetDatabase;
        this.statementPreparer = statementPreparer;
        this.transformedData = transformedData;
        this.sql = sql;
    }

    public void doLoad()
    {
        try
        {
            for (T transformedDatum : this.transformedData)
            {
                var preparedStatement = this.targetDatabase.prepareStatement(this.sql);
                this.statementPreparer.doPrepare(preparedStatement, transformedDatum);
                preparedStatement.executeUpdate();
            }

            this.targetDatabase.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}

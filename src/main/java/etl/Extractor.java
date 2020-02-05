package etl;

import data.Dataset;
import utils.DataStorer;
import utils.StatementPreparer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Extractor<T extends Dataset>
{
    private Connection sourceDatabase;
    private DataStorer<T> dataStorer;
    private ResultSet resultSet;
    private String sql;

    public Extractor(Connection sourceDatabase, DataStorer<T> dataStorer,
                     String sql)
    {
        this.sourceDatabase = sourceDatabase;
        this.dataStorer = dataStorer;
        this.sql = sql;
    }

    public List<T> doExtract()
    {
        try
        {
            var preparedStatement = this.sourceDatabase.prepareStatement(sql);
            this.resultSet = preparedStatement.executeQuery();
            this.sourceDatabase.close();
            return this.dataStorer.doStore(this.resultSet);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}

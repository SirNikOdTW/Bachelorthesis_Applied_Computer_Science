package utils;

import data.SourceDataset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DataStorer<T extends SourceDataset>
{
    List<T> doStore(ResultSet resultSet) throws SQLException;
}

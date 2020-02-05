package utils;

import data.Dataset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DataStorer<T extends Dataset>
{
    List<T> doStore(ResultSet resultSet) throws SQLException;
}

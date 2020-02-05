package utils;

import data.Dataset;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementPreparer<T extends Dataset>
{
    void doPrepare(PreparedStatement preparedStatement, T data) throws SQLException;
}

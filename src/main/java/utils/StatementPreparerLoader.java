package utils;

import data.SourceDataset;
import data.TargetDataset;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementPreparerLoader<T extends TargetDataset>
{
    void doPrepare(PreparedStatement preparedStatement, T data) throws SQLException;
}

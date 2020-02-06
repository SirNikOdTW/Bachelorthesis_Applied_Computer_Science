package utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementPreparerExtractor
{
    void doPrepare(PreparedStatement preparedStatement) throws SQLException;
}

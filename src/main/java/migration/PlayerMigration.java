package migration;

import data.SourceDataset;
import data.target.PlayerTarget;
import etl.Loader;
import etl.ETL;
import utils.StatementPreparerLoader;

import java.sql.Connection;
import java.util.List;

public class PlayerMigration extends ETL<SourceDataset, PlayerTarget>
{
    public PlayerMigration(final Connection source, final Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<SourceDataset> extract()
    {
        return null;
    }

    @Override
    protected List<PlayerTarget> transform(final List<SourceDataset> extractedData)
    {
        return null;
    }

    @Override
    protected void load(final List<PlayerTarget> transformedData)
    {
        final StatementPreparerLoader<PlayerTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getPlayerId());
            preparedStatement.setString(2, data.getPlayerName());
        };

        final var createdData = List.of(new PlayerTarget(0, "Dummy Name"));

        final var sql = "insert into player values (?, ?)";

        new Loader<>(this.target, statementPreparerLoader, createdData, sql).doLoad();
    }
}

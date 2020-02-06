package migration;

import data.SourceDataset;
import data.target.PlayerTarget;
import etl.Loader;
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
    public void migrate()
    {
        this.createPlayer();
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
    }

    private void createPlayer()
    {
        final var sql = "insert into player values (?, ?)";

        final StatementPreparerLoader<PlayerTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getPlayerId());
            preparedStatement.setString(2, data.getPlayerName());
        };

        final var transformedData = List.of(new PlayerTarget(0, "Dummy Name"));

        new Loader<>(this.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

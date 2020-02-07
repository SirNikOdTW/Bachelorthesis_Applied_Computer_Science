package migration;

import data.source.PlayerAbilitiesSource;
import data.target.PlayerAbilitiesTarget;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PlayerAbilitiesMigration extends ETL<PlayerAbilitiesSource, PlayerAbilitiesTarget>
{
    public PlayerAbilitiesMigration(final Connection source, final Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<PlayerAbilitiesSource> extract()
    {
        final DataStorer<PlayerAbilitiesSource> dataStorer = (resultSet) -> {
            final var extractedData = new ArrayList<PlayerAbilitiesSource>();
            while (resultSet.next())
            {
                extractedData.add(new PlayerAbilitiesSource(
                        resultSet.getInt("playerId"),
                        resultSet.getInt("abilityId")
                ));
            }
            return extractedData;
        };

        final var sql = "select * from player p join ability a on p.playerId = ?;";
        final StatementPreparerExtractor statementPreparer = (preparedStatement) -> preparedStatement.setInt(1, 0);

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<PlayerAbilitiesTarget> transform(final List<PlayerAbilitiesSource> extractedData)
    {
        final DataTransformer<PlayerAbilitiesSource, PlayerAbilitiesTarget> transformer =
                (dataset) -> new PlayerAbilitiesTarget(dataset.getPlayerId(), dataset.getAbilityId());

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(final List<PlayerAbilitiesTarget> transformedData)
    {

        final StatementPreparerLoader<PlayerAbilitiesTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getPlayerId());
            preparedStatement.setInt(2, data.getAbilityId());
        };

        final var sql = "insert into playerAbilities values (?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

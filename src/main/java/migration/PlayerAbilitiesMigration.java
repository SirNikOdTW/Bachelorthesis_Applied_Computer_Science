package migration;

import data.SourceDataset;
import data.source.AbilitiesSource;
import data.source.PlayerAbilitiesSource;
import data.target.AbilityTarget;
import data.target.PlayerAbilitiesTarget;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.DataStorer;
import utils.DataTransformer;
import utils.StatementPreparerExtractor;
import utils.StatementPreparerLoader;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PlayerAbilitiesMigration extends ETL<PlayerAbilitiesSource, PlayerAbilitiesTarget>
{
    public PlayerAbilitiesMigration(Connection source, Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<PlayerAbilitiesSource> extract()
    {
        DataStorer<PlayerAbilitiesSource> dataStorer = (resultSet) -> {
            var extractedData = new ArrayList<PlayerAbilitiesSource>();
            while (resultSet.next())
            {
                extractedData.add(new PlayerAbilitiesSource(
                        resultSet.getInt("playerId"),
                        resultSet.getInt("abilityId")
                ));
            }
            return extractedData;
        };

        var sql = "select * from player p join ability a on p.playerId = ?;";
        StatementPreparerExtractor statementPreparer = (preparedStatement) -> {
            preparedStatement.setInt(1, 0);
        };

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<PlayerAbilitiesTarget> transform(List<PlayerAbilitiesSource> extractedData)
    {
        DataTransformer<PlayerAbilitiesSource, PlayerAbilitiesTarget> transformer =
                (dataset) -> new PlayerAbilitiesTarget(dataset.getPlayerId(), dataset.getAbilityId());

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(List<PlayerAbilitiesTarget> transformedData)
    {

        StatementPreparerLoader<PlayerAbilitiesTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getPlayerId());
            preparedStatement.setInt(2, data.getAbilityId());
        };

        var sql = "insert into playerAbilities values (?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

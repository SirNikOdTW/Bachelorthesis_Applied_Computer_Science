package migration;

import data.source.AbilitiesSource;
import data.source.PersonSource;
import data.target.AbilityTarget;
import data.target.CharacterTarget;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AbilityMigration extends ETL<AbilitiesSource, AbilityTarget>
{
    private int abilityId;

    public AbilityMigration(Connection source, Connection target)
    {
        super(source, target);
        this.abilityId = 0;
    }

    @Override
    protected List<AbilitiesSource> extract()
    {
        DataStorer<AbilitiesSource> dataStorer = (resultSet) -> {
            var extractedData = new ArrayList<AbilitiesSource>();
            while (resultSet.next())
            {
                extractedData.add(new AbilitiesSource(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("level")
                ));
            }
            return extractedData;
        };

        var sql = "select * from abilities;";
        StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<AbilityTarget> transform(List<AbilitiesSource> extractedData)
    {
        DataTransformer<AbilitiesSource, AbilityTarget> transformer =
                (dataset) -> new AbilityTarget(
                        abilityId++,
                        dataset.getName(),
                        dataset.getDescription(),
                        dataset.getLevel()
                );

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(List<AbilityTarget> transformedData)
    {
        StatementPreparerLoader<AbilityTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getAbilityId());
            preparedStatement.setString(2, data.getAbilityName());
            preparedStatement.setString(3, data.getAbilityDescription());
            preparedStatement.setInt(4, data.getAbilityLevel());
        };

        var sql = "insert into ability values (?, ?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

package migration;

import com.mysql.cj.exceptions.NumberOutOfRange;
import data.source.AbilitiesSource;
import data.target.AbilityTarget;
import etl.ETL;
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

    public AbilityMigration(final Connection source, final Connection target)
    {
        super(source, target);
        this.abilityId = 0;
    }

    @Override
    protected List<AbilitiesSource> extract()
    {
        final DataStorer<AbilitiesSource> dataStorer = (resultSet) -> {
            final var extractedData = new ArrayList<AbilitiesSource>();
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

        final var sql = "select * from abilities;";
        final StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<AbilityTarget> transform(final List<AbilitiesSource> extractedData)
    {
        final DataTransformer<AbilitiesSource, AbilityTarget> transformer = (dataset) -> {
            if (dataset.getLevel() < 0 || dataset.getLevel() > 100)
                log.error("level-value out of range",
                        new NumberOutOfRange("level must be within 0 an 100 (both inclusive)"));
            final var levelCorrection = 100f;
            return new AbilityTarget(
                    abilityId++,
                    dataset.getName(),
                    dataset.getDescription(),
                    dataset.getLevel() / levelCorrection
            );
        };

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(final List<AbilityTarget> transformedData)
    {
        final StatementPreparerLoader<AbilityTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getAbilityId());
            preparedStatement.setString(2, data.getAbilityName());
            preparedStatement.setString(3, data.getAbilityDescription());
            preparedStatement.setFloat(4, data.getAbilityLevel());
        };

        final var sql = "insert into ability values (?, ?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

package migration;

import data.source.PersonInventorySource;
import data.target.CharacterInventoryTarget;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CharacterInventoryMigration extends ETL<PersonInventorySource, CharacterInventoryTarget>
{
    public CharacterInventoryMigration(Connection source, Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<PersonInventorySource> extract()
    {
        final DataStorer<PersonInventorySource > dataStorer = (resultSet) -> {
        final var extractedData = new ArrayList<PersonInventorySource>();
        while (resultSet.next())
        {
            extractedData.add(new PersonInventorySource(
                    resultSet.getInt("personId"),
                    resultSet.getInt("objectId")
            ));
        }
        return extractedData;
    };

        final var sql = "select * from personInventory;";
        final StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<CharacterInventoryTarget> transform(List<PersonInventorySource> extractedData)
    {
        final DataTransformer<PersonInventorySource, CharacterInventoryTarget> transformer = (dataset) ->
                new CharacterInventoryTarget(
                    dataset.getPersonId(),
                    dataset.getObjectId()
        );

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(List<CharacterInventoryTarget> transformedData)
    {
        final StatementPreparerLoader<CharacterInventoryTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getPersonId());
            preparedStatement.setInt(2, data.getObjectId());
        };

        final var sql = "insert into characterInventory values (?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

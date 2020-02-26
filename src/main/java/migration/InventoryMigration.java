package migration;

import data.source.InventorySource;
import data.target.InventoryTarget;
import etl.ETL;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class InventoryMigration extends ETL<InventorySource, InventoryTarget>
{
    public InventoryMigration(Connection source, Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<InventorySource> extract()
    {
        final DataStorer<InventorySource> dataStorer = (resultSet) -> {
            final var extractedData = new ArrayList<InventorySource>();
            while (resultSet.next())
            {
                extractedData.add(new InventorySource(
                        resultSet.getInt("objectId")
                ));
            }
            return extractedData;
        };

        final var sql = "select * from inventory;";
        final StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<InventoryTarget> transform(List<InventorySource> extractedData)
    {
        final DataTransformer<InventorySource, InventoryTarget> transformer = (dataset) ->
                new InventoryTarget(
                    0,
                    dataset.getObjectId()
        );

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(List<InventoryTarget> transformedData)
    {
        final StatementPreparerLoader<InventoryTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getPlayerId());
            preparedStatement.setInt(2, data.getObjectId());
            preparedStatement.setBoolean(3, data.isStolen());
        };

        final var sql = "insert into inventory values (?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

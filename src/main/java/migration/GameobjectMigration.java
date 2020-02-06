package migration;

import data.source.GameobjectSource;
import data.target.GameobjectTarget;
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

public class GameobjectMigration extends ETL<GameobjectSource, GameobjectTarget>
{
    public GameobjectMigration(final Connection source, final Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<GameobjectSource> extract()
    {
        final DataStorer<GameobjectSource> dataStorer = (resultSet) -> {
            final var extractedData = new ArrayList<GameobjectSource>();
            while (resultSet.next())
            {
                extractedData.add(new GameobjectSource(
                        resultSet.getInt("objectId"),
                        resultSet.getString("name"),
                        resultSet.getString("description")
                ));
            }
            return extractedData;
        };

        final var sql = "select * from gameobject;";
        final StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<GameobjectTarget> transform(final List<GameobjectSource> extractedData)
    {
        final DataTransformer<GameobjectSource, GameobjectTarget> transformer = (dataset) -> new GameobjectTarget(
                dataset.getObjectId(),
                dataset.getName(),
                dataset.getDescription()
        );

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(final List<GameobjectTarget> transformedData)
    {
        final StatementPreparerLoader<GameobjectTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getObjectId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setString(3, data.getDescription());
        };

        final var sql = "insert into gameobject values (?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

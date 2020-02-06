package migration;

import com.mysql.cj.exceptions.NumberOutOfRange;
import data.source.AbilitiesSource;
import data.source.GameobjectSource;
import data.target.AbilityTarget;
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
    public GameobjectMigration(Connection source, Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<GameobjectSource> extract()
    {
        DataStorer<GameobjectSource> dataStorer = (resultSet) -> {
            var extractedData = new ArrayList<GameobjectSource>();
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

        var sql = "select * from gameobject;";
        StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<GameobjectTarget> transform(List<GameobjectSource> extractedData)
    {
        DataTransformer<GameobjectSource, GameobjectTarget> transformer = (dataset) -> new GameobjectTarget(
                dataset.getObjectId(),
                dataset.getName(),
                dataset.getDescription()
        );

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(List<GameobjectTarget> transformedData)
    {
        StatementPreparerLoader<GameobjectTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getObjectId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setString(3, data.getDescription());
        };

        var sql = "insert into gameobject values (?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

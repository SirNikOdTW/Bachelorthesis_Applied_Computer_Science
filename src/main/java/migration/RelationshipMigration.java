package migration;

import com.mysql.cj.exceptions.NumberOutOfRange;
import data.source.RelationshipsSource;
import data.target.RelationshipTarget;
import etl.ETL;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class RelationshipMigration extends ETL<RelationshipsSource, RelationshipTarget>
{
    public RelationshipMigration(Connection source, Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<RelationshipsSource> extract()
    {
        final DataStorer<RelationshipsSource> dataStorer = (resultSet) -> {
            final var extractedData = new ArrayList<RelationshipsSource>();
            while (resultSet.next())
            {
                extractedData.add(new RelationshipsSource(
                        resultSet.getInt("personId"),
                        resultSet.getInt("relationshipLevel")
                ));
            }
            return extractedData;
        };

        final var sql = "select * from relationships;";
        final StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<RelationshipTarget> transform(List<RelationshipsSource> extractedData)
    {
        final DataTransformer<RelationshipsSource, RelationshipTarget> transformer = (dataset) -> {
            if (dataset.getRelationshipLevel() < 0 || dataset.getRelationshipLevel() > 100)
                log.error("level-value out of range",
                        new NumberOutOfRange("level must be within 0 an 100 (both inclusive)"));
            final var levelCorrection = 100f;
            return new RelationshipTarget(
                    0,
                    dataset.getPersonId(),
                    dataset.getRelationshipLevel() / levelCorrection
            );
        };

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(List<RelationshipTarget> transformedData)
    {
        final StatementPreparerLoader<RelationshipTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getPlayerId());
            preparedStatement.setInt(2, data.getPersonId());
            preparedStatement.setFloat(3, data.getRelationshipLevel());
        };

        final var sql = "insert into relationship values (?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

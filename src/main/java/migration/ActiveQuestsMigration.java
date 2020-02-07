package migration;

import com.mysql.cj.exceptions.NumberOutOfRange;
import data.source.ActiveQuestsSource;
import data.target.ActiveQuestsTarget;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ActiveQuestsMigration extends ETL<ActiveQuestsSource, ActiveQuestsTarget>
{
    public ActiveQuestsMigration(Connection source, Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<ActiveQuestsSource> extract()
    {
        final DataStorer<ActiveQuestsSource> dataStorer = (resultSet) -> {
            final var extractedData = new ArrayList<ActiveQuestsSource>();
            while (resultSet.next())
            {
                extractedData.add(new ActiveQuestsSource(
                        resultSet.getInt("questId"),
                        resultSet.getInt("progress")
                ));
            }
            return extractedData;
        };

        final var sql = "select * from activeQuests;";
        final StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<ActiveQuestsTarget> transform(List<ActiveQuestsSource> extractedData)
    {
        final DataTransformer<ActiveQuestsSource, ActiveQuestsTarget> transformer = (dataset) -> {
            if (dataset.getProgress() < 0 || dataset.getProgress() > 100)
                log.error("level-value out of range",
                        new NumberOutOfRange("level must be within 0 an 100 (both inclusive)"));
            final var levelCorrection = 100f;
            return new ActiveQuestsTarget(
                    0,
                    dataset.getQuestId(),
                    dataset.getProgress() / levelCorrection
            );
        };

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(List<ActiveQuestsTarget> transformedData)
    {
        final StatementPreparerLoader<ActiveQuestsTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getPlayerId());
            preparedStatement.setInt(2, data.getQuestId());
            preparedStatement.setFloat(3, data.getQuestProgress());
        };

        final var sql = "insert into activeQuests values (?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

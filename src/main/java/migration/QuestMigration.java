package migration;

import data.source.QuestSource;
import data.target.QuestTarget;
import etl.ETL;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class QuestMigration extends ETL<QuestSource, QuestTarget>
{
    public QuestMigration(final Connection source, final Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<QuestSource> extract()
    {
        final DataStorer<QuestSource> dataStorer = (resultSet) -> {
            final var extractedData = new ArrayList<QuestSource>();
            while (resultSet.next())
            {
                extractedData.add(new QuestSource(
                        resultSet.getInt("questId"),
                        resultSet.getString("name"),
                        resultSet.getString("dialogue"),
                        resultSet.getInt("personId")));
            }
            return extractedData;
        };

        final var sql = "select * from quest q join questParticipation qp on q.questId  = qp.questId;";
        final StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<QuestTarget> transform(List<QuestSource> extractedData)
    {
        final var map = new HashMap<Integer, String>();

        for (final QuestSource extractedDatum : extractedData)
        {
            final var key = extractedDatum.getQuestId();
            if (!map.containsKey(key))
            {
                map.put(key, String.valueOf(extractedDatum.getPersonId()));
            }
            else
            {
                final var involvedPersons = String.format("%s, %s", map.get(key), extractedDatum.getPersonId());
                map.replace(key, involvedPersons);
            }
        }

        extractedData = extractedData.stream().distinct().collect(Collectors.toList());

        final DataTransformer<QuestSource, QuestTarget> transformer = (dataset) -> new QuestTarget(
                    dataset.getQuestId(),
                    dataset.getName(),
                    map.get(dataset.getQuestId()),
                    dataset.getDialogue()
        );

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(final List<QuestTarget> transformedData)
    {
        final StatementPreparerLoader<QuestTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getQuestId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setString(3, data.getInvolvedCharacters());
            preparedStatement.setString(4, data.getDialogue());
        };

        final var sql = "insert into quest values (?, ?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

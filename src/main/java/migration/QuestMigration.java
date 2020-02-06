package migration;

import data.source.QuestSource;
import data.target.QuestTarget;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.DataStorer;
import utils.DataTransformer;
import utils.StatementPreparerExtractor;
import utils.StatementPreparerLoader;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class QuestMigration extends ETL<QuestSource, QuestTarget>
{
    public QuestMigration(Connection source, Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<QuestSource> extract()
    {
        DataStorer<QuestSource> dataStorer = (resultSet) -> {
            var extractedData = new ArrayList<QuestSource>();
            while (resultSet.next())
            {
                extractedData.add(new QuestSource(
                        resultSet.getInt("questId"),
                        resultSet.getString("name"),
                        resultSet.getClob("dialogue"),
                        resultSet.getInt("personId")));
            }
            return extractedData;
        };

        var sql = "select * from quest q join questParticipation qp on q.questId  = qp.questId;";
        StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<QuestTarget> transform(List<QuestSource> extractedData)
    {
        var map = new HashMap<Integer, String>();

        for (QuestSource extractedDatum : extractedData)
        {
            var key = extractedDatum.getQuestId();
            if (!map.containsKey(key))
            {
                map.put(key, String.valueOf(extractedDatum.getPersonId()));
            }
            else
            {
                var involvedPersons = String.format("%s, %s", map.get(key), extractedDatum.getPersonId());
                map.replace(key, involvedPersons);
            }
        }

        extractedData = extractedData.stream().distinct().collect(Collectors.toList());

        DataTransformer<QuestSource, QuestTarget> transformer = (dataset) -> new QuestTarget(
                    dataset.getQuestId(),
                    dataset.getName(),
                    map.get(dataset.getQuestId()),
                    dataset.getDialogue()
        );

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(List<QuestTarget> transformedData)
    {
        StatementPreparerLoader<QuestTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getQuestId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setString(3, data.getInvolvedCharacters());
            preparedStatement.setClob(4, data.getDialogue());
        };

        var sql = "insert into quest values (?, ?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

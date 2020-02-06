package migration;

import data.source.AbilitiesSource;
import data.source.PersonSource;
import data.target.AbilityTarget;
import data.target.CharacterTarget;
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

public class CharacterMigration extends ETL<PersonSource, CharacterTarget>
{
    public CharacterMigration(Connection source, Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<PersonSource> extract()
    {
        DataStorer<PersonSource> dataStorer = (resultSet) -> {
            var extractedData = new ArrayList<PersonSource>();
            while (resultSet.next())
            {
                extractedData.add(new PersonSource(
                        resultSet.getInt("personId"),
                        resultSet.getString("name"),
                        resultSet.getBoolean("mortal")
                ));
            }
            return extractedData;
        };

        var sql = "select * from person;";
        StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<CharacterTarget> transform(List<PersonSource> extractedData)
    {
        DataTransformer<PersonSource, CharacterTarget> transformer =
                (dataset) -> new CharacterTarget(
                        dataset.getPersonId(),
                        dataset.getName(),
                        dataset.isMortal()
                );

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(List<CharacterTarget> transformedData)
    {
        StatementPreparerLoader<CharacterTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getPersonId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setBoolean(3, data.isMortal());
        };

        var sql = "insert into character values (?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

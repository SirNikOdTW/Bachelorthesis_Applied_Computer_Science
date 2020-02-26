package migration;

import data.source.PersonSource;
import data.target.CharacterTarget;
import etl.ETL;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CharacterMigration extends ETL<PersonSource, CharacterTarget>
{
    public CharacterMigration(final Connection source, final Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<PersonSource> extract()
    {
        final DataStorer<PersonSource> dataStorer = (resultSet) -> {
            final var extractedData = new ArrayList<PersonSource>();
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

        final var sql = "select * from person;";
        final StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<CharacterTarget> transform(final List<PersonSource> extractedData)
    {
        final DataTransformer<PersonSource, CharacterTarget> transformer =
                (dataset) -> new CharacterTarget(
                        dataset.getPersonId(),
                        dataset.getName(),
                        dataset.isMortal()
                );

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(final List<CharacterTarget> transformedData)
    {
        final StatementPreparerLoader<CharacterTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getPersonId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setBoolean(3, data.isMortal());
        };

        final var sql = "insert into character values (?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

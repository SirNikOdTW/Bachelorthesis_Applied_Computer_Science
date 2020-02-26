package migration;

import data.source.ModSource;
import data.target.ModTarget;
import etl.ETL;
import etl.Extractor;
import etl.Loader;
import etl.Transformer;
import utils.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ModMigration extends ETL<ModSource, ModTarget>
{
    public ModMigration(final Connection source, final Connection target)
    {
        super(source, target);
    }

    @Override
    protected List<ModSource> extract()
    {
        final DataStorer<ModSource> dataStorer = (resultSet) -> {
            final var extractedData = new ArrayList<ModSource>();
            while (resultSet.next())
            {
                extractedData.add(new ModSource(
                        resultSet.getInt("modId"),
                        resultSet.getString("name"),
                        resultSet.getDate("installationDate").toLocalDate(),
                        resultSet.getBlob("binary")
                ));
            }
            return extractedData;
        };

        final var sql = "select * from `mod`;";
        final StatementPreparerExtractor statementPreparer = (preparedStatement) -> {};

        return new Extractor<>(super.source, dataStorer, statementPreparer, sql).doExtract();
    }

    @Override
    protected List<ModTarget> transform(final List<ModSource> extractedData)
    {
        final DataTransformer<ModSource, ModTarget> transformer = (dataset) -> new ModTarget(
                dataset.getModId(),
                dataset.getName(),
                dataset.getInstallationDate(),
                dataset.getBinary()
        );

        return new Transformer<>(transformer, extractedData).doTransform();
    }

    @Override
    protected void load(final List<ModTarget> transformedData)
    {
        final StatementPreparerLoader<ModTarget> statementPreparerLoader =  (preparedStatement, data) -> {
            preparedStatement.setInt(1, data.getModId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setDate(3, Date.valueOf(data.getModInstallationDate()));
            preparedStatement.setBinaryStream(4, data.getModBinary().getBinaryStream());
        };

        final var sql = "insert into \"mod\" values (?, ?, ?, ?)";

        new Loader<>(super.target, statementPreparerLoader, transformedData, sql).doLoad();
    }
}

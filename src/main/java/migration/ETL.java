package migration;

import data.SourceDataset;
import data.TargetDataset;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class ETL<T extends SourceDataset, E extends TargetDataset>
{
    protected static final Logger log = Logger.getLogger(ETL.class.getName());

    protected final Connection source;
    protected final Connection target;

    public ETL(final Connection source, final Connection target)
    {
        this.source = source;
        this.target = target;
    }

    public void migrate()
    {
        log.info(String.format("--- Migration: %s ---", this.getClass().getName()));
        final var extractedData = this.extract();
        final var transformedData = this.transform(extractedData);
        this.load(transformedData);

        try
        {
            this.target.commit();
        }
        catch (final SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected abstract List<T> extract();
    protected abstract List<E> transform(List<T> extractedData);
    protected abstract void load(List<E> transformedData);
}

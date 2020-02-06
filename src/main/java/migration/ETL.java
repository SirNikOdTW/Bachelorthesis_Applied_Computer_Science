package migration;

import data.SourceDataset;
import data.TargetDataset;
import data.source.PersonSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public abstract class ETL<T extends SourceDataset, E extends TargetDataset>
{
    protected static Logger log = Logger.getLogger(ETL.class.getName());

    protected Connection source;
    protected Connection target;

    public ETL(Connection source, Connection target)
    {
        this.source = source;
        this.target = target;
    }

    public void migrate()
    {
        log.info(String.format("--- Migration: %s ---", this.getClass().getName()));
        var extractedData = this.extract();
        var transformedData = this.transform(extractedData);
        this.load(transformedData);
    }

    protected abstract List<T> extract();
    protected abstract List<E> transform(List<T> extractedData);
    protected abstract void load(List<E> transformedData);
}

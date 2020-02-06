package etl;

import data.SourceDataset;
import data.TargetDataset;
import org.apache.log4j.Logger;
import utils.DataTransformer;

import java.util.ArrayList;
import java.util.List;

public class Transformer<T extends SourceDataset, E extends TargetDataset>
{
    private static final Logger log = Logger.getLogger(Transformer.class.getName());

    private final DataTransformer<T, E> transformer;
    private final List<T> extractedData;

    public Transformer(final DataTransformer<T, E> transformer, final List<T> extractedData)
    {
        this.transformer = transformer;
        this.extractedData = extractedData;
    }

    public List<E> doTransform()
    {
        final var transformed = new ArrayList<E>();
        for (final T dataset : this.extractedData)
        {
            log.info(String.format("Transform data: %s", dataset));
            transformed.add(this.transformer.transform(dataset));
        }
        log.info("--- Data transformed ---");
        return transformed;
    }
}

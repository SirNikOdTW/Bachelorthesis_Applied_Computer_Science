package etl;

import data.SourceDataset;
import data.TargetDataset;
import utils.DataTransformer;

import java.util.ArrayList;
import java.util.List;

public class Transformer<T extends SourceDataset, E extends TargetDataset>
{

    private DataTransformer<T, E> transformer;
    private List<T> extractedData;

    public Transformer(DataTransformer<T, E> transformer, List<T> extractedData)
    {
        this.transformer = transformer;
        this.extractedData = extractedData;
    }

    public List<E> doTransform()
    {
        var transformed = new ArrayList<E>();
        for (T dataset : this.extractedData)
        {
            transformed.add(this.transformer.transform(dataset));
        }
        return transformed;
    }
}

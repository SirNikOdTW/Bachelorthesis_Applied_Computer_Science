package etl;

import data.Dataset;
import utils.DataTransformer;

import java.util.ArrayList;
import java.util.List;

public class Transformer<T extends Dataset, E extends Dataset>
{

    private DataTransformer<T, E> transformer;
    private List<T> datasets;

    public Transformer(DataTransformer<T, E> transformer, List<T> datasets)
    {
        this.transformer = transformer;
        this.datasets = datasets;
    }

    public List<E> doTransform()
    {
        var transformed = new ArrayList<E>();
        for (T dataset : this.datasets)
        {
            transformed.add(this.transformer.transform(dataset));
        }
        return transformed;
    }
}

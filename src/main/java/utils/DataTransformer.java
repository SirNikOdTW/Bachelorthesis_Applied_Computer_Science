package utils;

import data.SourceDataset;
import data.TargetDataset;

public interface DataTransformer<T extends SourceDataset, E extends TargetDataset>
{
    E transform(T dataset);
}

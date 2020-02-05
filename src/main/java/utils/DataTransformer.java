package utils;

import data.Dataset;

import java.util.List;

public interface DataTransformer<T extends Dataset, E extends Dataset>
{
    E transform(T dataset);
}

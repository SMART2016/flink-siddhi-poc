package com.stackidentity.rae.app.transformer;

/**
 *
 * @param <I> The input Record type
 * @param <O> The output record type after applying transformation to I
 */
public interface EventTransformer<I,O> {
    public  O transform(I record);
}

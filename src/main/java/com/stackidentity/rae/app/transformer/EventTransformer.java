package com.stackidentity.rae.app.transformer;

import java.io.Serializable;

/**
 * @param <I> The input Record type
 * @param <O> The output record type after applying transformation to I
 */
public interface EventTransformer<I, O> extends Serializable {
    public O transform(I record);
}

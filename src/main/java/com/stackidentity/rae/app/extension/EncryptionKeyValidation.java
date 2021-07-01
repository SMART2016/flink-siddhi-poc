package com.stackidentity.rae.app.extension;

import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.exception.SiddhiAppCreationException;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.executor.function.FunctionExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;

public class EncryptionKeyValidation extends FunctionExecutor {
    private Attribute.Type returnType;

    /**
     * The initialization method for FunctionExecutor, this method will be called before the other methods
     */
    @Override
    protected StateFactory init(ExpressionExecutor[] expressionExecutors, ConfigReader configReader, SiddhiQueryContext siddhiQueryContext) {
        returnType = Attribute.Type.BOOL;
        for (ExpressionExecutor expressionExecutor : attributeExpressionExecutors) {
            Attribute.Type attributeType = expressionExecutor.getReturnType();
            if ((attributeType != Attribute.Type.STRING)) {
                throw new SiddhiAppCreationException("Parameter type has to be String");
            }
        }
        return null;
    }

    /**
     * The main execution method which will be called upon event arrival
     * when there are more then one function parameter
     *
     * @param data the runtime values of function parameters
     * @return the function result
     */
    @Override
    protected Object execute(Object[] data) {
        return true;
    }

    /**
     * The main execution method which will be called upon event arrival
     * when there are zero or one function parameter
     *
     * @param data null if the function parameter count is zero or
     *             runtime data value of the function parameter
     * @return the function result
     */
    @Override
    protected Object execute(Object data) {
        System.out.println("obj key is : "+data);
       return false;
    }

    @Override
    protected Object execute(Object[] objects, State state) {
        System.out.println("array key is : "+objects);
        return false;
    }

    @Override
    protected Object execute(Object o, State state) {
        System.out.println("state key is : "+o);
        return true;
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }

}
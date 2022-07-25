package org.winble.moray.statemachine;

import org.winble.moray.domain.*;
import org.winble.moray.result.BaseResult;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class SimpleStateMachine<C, S extends IState, E extends IEvent> extends AbstractStateMachine<C, S, E, BaseResult> {


    public SimpleStateMachine(C context, S state, IStateMachineFactory<C, S, E, BaseResult> factory) {
        super(context, state, factory);
    }

    public SimpleStateMachine(C context, S state, IStateMachineFactory<C, S, E, BaseResult> factory, Supplier<Function<Exception, BaseResult>> errorHandler) {
        super(context, state, factory, errorHandler);
    }

    @Override
    public BaseResult onSuccess(E event) {
        return BaseResult.success();
    }

    @Override
    public BaseResult onError(Exception e) {
        return BaseResult.failure(599, e.getMessage());
    }
}

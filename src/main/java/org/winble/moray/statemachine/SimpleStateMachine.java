package org.winble.moray.statemachine;

import org.winble.moray.result.BaseResult;
import org.winble.moray.domain.IEvent;
import org.winble.moray.domain.IState;
import org.winble.moray.domain.ITransition;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class SimpleStateMachine<C, S extends IState, E extends IEvent> extends AbstractStateMachine<C, S, E, BaseResult> {


    public SimpleStateMachine(C context, S state, BiFunction<S, E, ITransition<C, S, E>> transitionSupplier) {
        super(context, state, transitionSupplier);
    }

    public SimpleStateMachine(C context, S state, BiFunction<S, E, ITransition<C, S, E>> transitionSupplier, Supplier<Function<Exception, BaseResult>> errorHandler) {
        super(context, state, transitionSupplier, errorHandler);
    }

    @Override
    public BaseResult fire(E event) {
        try {
            transitionSupplier.apply(state, event).action(this, event);
            return BaseResult.success();
        } catch (Exception e) {
            return errorHandler.get().apply(e);
        }
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

package org.winble.moray.statemachine;

import org.winble.moray.domain.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author bowenzhang
 * Create on 2022/7/25
 */
public abstract class AbstractStateMachine<C, S extends IState, E extends IEvent, R extends IResult> implements IStateMachine<C, S, E, R> {

    protected S state;

    protected C context;

    protected final Supplier<Function<Exception, R>> errorHandler;

    protected final IStateMachineFactory<C, S, E, R> factory;

    public AbstractStateMachine(C context, S state, IStateMachineFactory<C, S, E, R> factory) {
        this.context = context;
        this.state = state;
        this.factory  = factory;
        this.errorHandler = () -> this::onError;
    }

    public AbstractStateMachine(C context, S state, IStateMachineFactory<C, S, E, R> factory, Supplier<Function<Exception, R>> errorHandler) {
        this.context = context;
        this.state = state;
        this.factory = factory;
        this.errorHandler = errorHandler;
    }

    @Override
    public R fire(E event) {
        try {
            factory.matchTransition(state, event).action(this, event);
            return this.onSuccess(event);
        } catch (Exception e) {
            return errorHandler.get().apply(e);
        }
    }

    public abstract R onSuccess(E event);

    public abstract R onError(Exception e);

    @Override
    public C getContext() {
        return this.context;
    }

    @Override
    public S getState() {
        return this.state;
    }

    @Override
    public void transit(S from, S to, C context) {
        this.state = to;
        this.context = context;
    }
}

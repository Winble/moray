package org.winble.moray.statemachine;

import org.winble.moray.domain.*;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author bowenzhang
 * Create on 2022/7/25
 */
public abstract class AbsStateMachine<C, S extends IState, R extends IResult> implements IStateMachine<C, S, R> {

    protected volatile S state;

    protected volatile C context;

    protected final Supplier<Function<Exception, R>> errorHandler;

    protected final IStateMachineFactory<C, S, R> factory;

    public AbsStateMachine(C context, S state, IStateMachineFactory<C, S, R> factory) {
        this.context = context;
        this.state = state;
        this.factory  = factory;
        this.errorHandler = () -> this::onError;
    }

    public AbsStateMachine(C context, S state, IStateMachineFactory<C, S, R> factory, Supplier<Function<Exception, R>> errorHandler) {
        this.context = context;
        this.state = state;
        this.factory = factory;
        this.errorHandler = errorHandler;
    }

    @Override
    public R fire(IEvent event) {
        try {
            factory.matchTransition(this.getState(), event).action(this, event);
            return this.onSuccess();
        } catch (Exception e) {
            return errorHandler.get().apply(e);
        }
    }

    public abstract R onSuccess();

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

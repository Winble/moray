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

    protected final BiFunction<S, E, ITransition<C, S, E>> transitionSupplier;

    protected final Supplier<Function<Exception, R>> errorHandler;

    public AbstractStateMachine(C context, S state, BiFunction<S, E, ITransition<C, S, E>> transitionSupplier) {
        this.context = context;
        this.state = state;
        this.transitionSupplier = transitionSupplier;
        this.errorHandler = () -> this::onError;
    }

    public AbstractStateMachine(C context, S state, BiFunction<S, E, ITransition<C, S, E>> transitionSupplier, Supplier<Function<Exception, R>> errorHandler) {
        this.context = context;
        this.state = state;
        this.transitionSupplier = transitionSupplier;
        this.errorHandler = errorHandler;
    }

    @Override
    public R fire(E event) {
        try {
            transitionSupplier.apply(state, event).action(this, event);
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
    public void setContext(C context) {
        this.context = context;
    }

    @Override
    public S getState() {
        return this.state;
    }

    @Override
    public void transit(S from, S to) {
        this.state = to;
    }
}

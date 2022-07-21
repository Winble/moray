package org.winble.moray;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class StateMachine<C, S extends IState, E extends IEvent> implements IStateMachine<C, S, E> {

    private S state;

    private C context;

    private final BiFunction<S, E, ITransition<C, S, E>> transitionSupplier;

    private final Supplier<Function<Exception, IResult>> errorHandler;

    public StateMachine(C context, S state, BiFunction<S, E, ITransition<C, S, E>> transitionSupplier) {
        this.context = context;
        this.state = state;
        this.transitionSupplier = transitionSupplier;
        this.errorHandler = () -> e -> new FailureResult(599, e.getMessage());
    }

    public StateMachine(C context, S state, BiFunction<S, E, ITransition<C, S, E>> transitionSupplier, Supplier<Function<Exception, IResult>> errorHandler) {
        this.context = context;
        this.state = state;
        this.transitionSupplier = transitionSupplier;
        this.errorHandler = errorHandler;
    }

    @Override
    public IResult fire(E event) {
        try {
            transitionSupplier.apply(state, event).action(this, event);
            return SimpleResult.success();
        } catch (Exception e) {
            return errorHandler.get().apply(e);
        }
    }

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
    public void setState(S state) {
        this.state = state;
    }
}

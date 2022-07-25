package org.winble.moray.statemachine;

import org.winble.moray.domain.IEvent;
import org.winble.moray.domain.IResult;
import org.winble.moray.domain.IState;
import org.winble.moray.domain.IStateMachineFactory;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author bowenzhang
 * Create on 2022/7/25
 */
public abstract class AbsSyncStateMachine<C, S extends IState, E extends IEvent, R extends IResult> extends AbsStateMachine<C, S, E, R> {
    public AbsSyncStateMachine(C context, S state, IStateMachineFactory<C, S, E, R> factory) {
        super(context, state, factory);
    }

    public AbsSyncStateMachine(C context, S state, IStateMachineFactory<C, S, E, R> factory, Supplier<Function<Exception, R>> errorHandler) {
        super(context, state, factory, errorHandler);
    }

    @Override
    public R fire(E event) {
        synchronized (this) {
            return super.fire(event);
        }
    }
}

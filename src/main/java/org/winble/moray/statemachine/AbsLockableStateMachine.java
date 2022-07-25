package org.winble.moray.statemachine;

import org.winble.moray.domain.IEvent;
import org.winble.moray.domain.IResult;
import org.winble.moray.domain.IState;
import org.winble.moray.domain.IStateMachineFactory;
import org.winble.moray.type.ReservedErrorResults;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Create on 2022/7/25
 *
 * @author bowenzhang
 */
public abstract class AbsLockableStateMachine<C, S extends IState, R extends IResult> extends AbsStateMachine<C, S, R> {

    private final Lock lock;

    public AbsLockableStateMachine(C context, S state, IStateMachineFactory<C, S, R> factory) {
        super(context, state, factory);
        this.lock = this.initLock();
    }

    public AbsLockableStateMachine(C context, S state, IStateMachineFactory<C, S, R> factory, Supplier<Function<Exception, R>> errorHandler) {
        super(context, state, factory, errorHandler);
        this.lock = this.initLock();
    }

    protected Lock initLock() {
        return new ReentrantLock();
    }

    @Override
    public R fire(IEvent event) {
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                return super.fire(event);
            } else {
                return this.onError(ReservedErrorResults.TRY_LOCK_FAIL.exception());
            }
        } catch (Exception e) {
            return this.onError(e);
        }
    }
}

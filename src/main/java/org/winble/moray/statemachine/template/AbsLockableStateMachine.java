package org.winble.moray.statemachine.template;

import org.winble.moray.domain.Event;
import org.winble.moray.domain.Result;
import org.winble.moray.type.ReservedErrorResults;

/**
 * @author bowenzhang
 * Create on 2023/10/8
 */
public abstract class AbsLockableStateMachine<S, C> extends AbsStateMachine<S, C> {

    public AbsLockableStateMachine(S state, C context) {
        super(state, context);
    }

    @Override
    public Result fire(Event event) {
        boolean locked = false;
        try {
            if (!(locked = this.lock())) {
                return ReservedErrorResults.TRY_LOCK_FAIL.result();
            }
            return super.fire(event);
        } finally {
            if (locked) {
                this.unlock();
            }
        }
    }

    abstract boolean lock();

    abstract void unlock();
}

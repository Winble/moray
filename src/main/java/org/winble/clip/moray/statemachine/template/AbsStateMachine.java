package org.winble.clip.moray.statemachine.template;

import org.winble.clip.moray.domain.Event;
import org.winble.clip.moray.domain.Result;
import org.winble.clip.moray.domain.StateMachine;
import org.winble.clip.moray.domain.Transition;
import org.winble.clip.moray.type.BaseResult;
import org.winble.clip.moray.type.ContextResult;
import org.winble.clip.moray.type.ReservedErrorResults;
import org.winble.clip.moray.type.StateTransitionException;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author bowenzhang
 * Create on 2022/7/25
 */
public abstract class AbsStateMachine<S, C> implements StateMachine<S, C> {

    private final AtomicReference<S> state;

    private volatile C context;

    public AbsStateMachine(S state, C context) {
        this.state = new AtomicReference<>(state);
        this.context = context;
    }

    @Override
    public S getState() {
        return state.get();
    }

    @Override
    public C getContext() {
        return context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result fire(Event event) {
        Transition<Event, S, C> transition = this.matchTransition(this.getState(), event);
        if (null == transition) {
            return ReservedErrorResults.UNMATCHED_TRANSITION_FROM.result();
        }
        try {
            if (state.compareAndSet(transition.from(), transition.to())) {
                Result result = transition.action(event, this.getContext());
                if (result instanceof ContextResult) {
                    if (context.getClass().isInstance(((ContextResult<?>) result).getContext())) {
                        this.context = ((ContextResult<C>) result).getContext();
                    } else {
                        return ReservedErrorResults.CONTEXT_TYPE_MISMATCH.result();
                    }
                }
                return result;
            } else {
                return ReservedErrorResults.UNMATCHED_STATE.result();
            }
        } catch (StateTransitionException e) {
            return new BaseResult(false, e.getCode(), e.getMessage());
        }
    }

    abstract public Transition<Event, S, C> matchTransition(S from, Event event);
}

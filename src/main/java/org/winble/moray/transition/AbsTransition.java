package org.winble.moray.transition;

import org.apache.commons.lang3.tuple.Pair;
import org.winble.moray.type.ReservedErrorResults;
import org.winble.moray.type.StateTransitionException;
import org.winble.moray.domain.IEvent;
import org.winble.moray.domain.IState;
import org.winble.moray.domain.IStateMachine;
import org.winble.moray.domain.ITransition;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public abstract class AbsTransition<C, S extends IState, E extends IEvent> implements ITransition<C, S, E> {

    private final S from;

    private final S to;

    private final Pair<String, Class<? extends IEvent>> on;

    protected AbsTransition(S from, S to, Class<E> on) {
        this.from = from;
        this.to = to;
        this.on = Pair.of(on.getSimpleName(), on);
    }

    protected AbsTransition(S from, S to, E on) {
        this.from = from;
        this.to = to;
        this.on = Pair.of(on.name(), on.getClass());
    }

    @Override
    public S from() {
        return from;
    }

    @Override
    public S to() {
        return to;
    }

    @Override
    public Pair<String, Class<? extends IEvent>> on() {
        return on;
    }

    @Override
    public void action(IStateMachine<C, S, ?> stateMachine, E event) {
        if (!this.preCondition(stateMachine, event)) {
            throw ReservedErrorResults.CHECK_PRE_CONDITION_FAIL.exception();
        }
        try (AutoCloseable ignored = tryAction(stateMachine, event)) {
            C context = this.doAction(stateMachine.getContext(), event);
            stateMachine.transit(this.from, this.to, context);
        } catch (StateTransitionException e) {
            throw e;
        } catch (Exception e) {
            throw ReservedErrorResults.UNKNOWN_ERROR.exception(e);
        }
    }

    protected boolean preCondition(IStateMachine<C, S, ?> stateMachine, E event) {
        if (!this.from.equals(stateMachine.getState())) {
            throw ReservedErrorResults.UNMATCHED_TRANSITION_FROM.exception();
        }
        if (!this.on.getLeft().equals(event.name())) {
            throw ReservedErrorResults.UNMATCHED_EVENT_TYPE.exception();
        }
        return true;
    }

    protected AutoCloseable tryAction(IStateMachine<C, S, ?> stateMachine, E event) throws Exception {
        return () -> {};
    }

    protected abstract C doAction(C context, E event);
}

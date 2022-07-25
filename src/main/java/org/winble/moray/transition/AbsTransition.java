package org.winble.moray.transition;

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

    private final E on;

    protected AbsTransition(S from, S to, E on) {
        this.from = from;
        this.to = to;
        this.on = on;
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
    public E on() {
        return on;
    }

    @Override
    public void action(IStateMachine<C, S, E, ?> stateMachine, E event) {
        if (!this.preCondition(stateMachine, event)) {
            return;
        }
        try (AutoCloseable ignored = tryAction(stateMachine, event)) {
            C context = this.doAction(stateMachine.getContext(), event);
            stateMachine.transit(this.from, this.to, context);
        } catch (StateTransitionException e) {
            throw e;
        } catch (Exception e) {
            throw new StateTransitionException(510, e);
        }
    }

    protected boolean preCondition(IStateMachine<C, S, E, ?> stateMachine, E event) {
        if (!this.from.equals(stateMachine.getState())) {
            throw new StateTransitionException(500, "illegal transition");
        }
        if (!this.on.name().equals(event.name())) {
            throw new StateTransitionException(501, "invalid event type");
        }
        return true;
    }

    protected AutoCloseable tryAction(IStateMachine<C, S, E, ?> stateMachine, E event) throws Exception {
        return () -> {};
    }

    protected abstract C doAction(C context, E event);
}

package org.winble.moray.statemachine;

import org.apache.commons.lang3.tuple.Triple;
import org.winble.moray.domain.*;
import org.winble.moray.statemachine.template.AbsStateMachine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bowenzhang
 * Create on 2023/10/8
 */
public class SimpleStateMachine<S, C> extends AbsStateMachine<S, C> implements StateMachineFactory<S, C, Object> {

    Map<Triple<S, String, Class<?>>, Transition<? extends Event, S, C>> transitions = new HashMap<>();

    public SimpleStateMachine(S state, C context) {
        super(state, context);
    }

    @Override
    public synchronized Result fire(Event event) {
        return super.fire(event);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Transition<Event, S, C> matchTransition(S from, Event event) {
        return (Transition<Event, S, C>) transitions.get(this.transKey(this.getState(), event));
    }

    @Override
    public StateMachine<S, C> get(Object id) {
        return this;
    }

    @Override
    public synchronized StateMachineFactory<S, C, Object> load(Transition<? extends Event, S, C> transition) {
        transitions.put(this.transKey(transition.from(), transition.when()), transition);
        return this;
    }

    private Triple<S, String, Class<?>> transKey(S from, Event event) {
        return Triple.of(from, event.name(), event.getClass());
    }
}

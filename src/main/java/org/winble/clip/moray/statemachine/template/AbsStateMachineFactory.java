package org.winble.clip.moray.statemachine.template;

import org.apache.commons.lang3.tuple.Triple;
import org.winble.clip.moray.domain.Event;
import org.winble.clip.moray.domain.StateMachineFactory;
import org.winble.clip.moray.domain.Transition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bowenzhang
 * Create on 2023/10/8
 */
public abstract class AbsStateMachineFactory<S, C, I> implements StateMachineFactory<S, C, I> {

    Map<Triple<S, String, Class<?>>, Transition<? extends Event, S, C>> transitions = new HashMap<>();

    @Override
    public synchronized StateMachineFactory<S, C, I> load(Transition<? extends Event, S, C> transition) {
        transitions.put(this.transKey(transition.from(), transition.when()), transition);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Transition<Event, S, C> matchTransition(S from, Event event) {
        return (Transition<Event, S, C>) transitions.get(this.transKey(from, event));
    }

    private Triple<S, String, Class<?>> transKey(S from, Event event) {
        return Triple.of(from, event.name(), event.getClass());
    }
}

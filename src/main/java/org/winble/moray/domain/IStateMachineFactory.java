package org.winble.moray.domain;

import java.util.Collection;
import java.util.List;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface IStateMachineFactory<C, S extends IState, R extends IResult> {

    void load(ITransition<C, S, ? extends IEvent> transition);

    default void load(Collection<ITransition<C, S, ? extends IEvent>> transitions) {
        transitions.forEach(this::load);
    }

    default void load(ITransition<C, S, ? extends IEvent>[] transitions) {
        for (ITransition<C, S, ? extends IEvent> transition : transitions) {
            this.load(transition);
        }
    }

    IStateMachine<C, S, R> get(String id);

    <E extends IEvent> ITransition<C, S, E> matchTransition(IState from, E event);
}

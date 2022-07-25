package org.winble.moray.domain;

import java.util.Collection;
import java.util.List;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface IStateMachineFactory<C, S extends IState, E extends IEvent, R extends IResult> {

    void load(ITransition<C, S, E> transition);

    default void load(Collection<ITransition<C, S, E>> transitions) {
        transitions.forEach(this::load);
    }

    default void load(ITransition<C, S, E>[] transitions) {
        for (ITransition<C, S, E> transition : transitions) {
            this.load(transition);
        }
    }

    IStateMachine<C, S, E, R> get(String id);

    ITransition<C, S, E> matchTransition(IState from, IEvent event);
}

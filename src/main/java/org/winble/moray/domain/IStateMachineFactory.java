package org.winble.moray.domain;

import java.util.List;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface IStateMachineFactory<C, S extends IState, E extends IEvent, M extends IStateMachine<C, S, E, ?>> {

    void load(ITransition<C, S, E> transition);

    default void load(List<ITransition<C, S, E>> transitions) {
        transitions.forEach(this::load);
    }

    default void load(ITransition<C, S, E>[] transitions) {
        for (ITransition<C, S, E> transition : transitions) {
            this.load(transition);
        }
    }

    M get(String id);
}

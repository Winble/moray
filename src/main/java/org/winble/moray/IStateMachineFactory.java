package org.winble.moray;

import java.util.List;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface IStateMachineFactory<C, S extends IState, E extends IEvent, M extends IStateMachine<C, S, E>> {

    void launch(ITransition<C, S, E> transition);

    default void launch(List<ITransition<C, S, E>> transitions) {
        transitions.forEach(this::launch);
    }

    default void launch(ITransition<C, S, E>[] transitions) {
        for (ITransition<C, S, E> transition : transitions) {
            this.launch(transition);
        }
    }

    M get(String id);
}

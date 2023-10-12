package org.winble.clip.moray.domain;

/**
 * @author bowenzhang
 * Create on 2023/10/8
 */
public interface StateMachineFactory<S, C, I> {

    StateMachine<S, C> get(I id);

    StateMachineFactory<S, C, I> load(Transition<? extends Event, S, C> transition);

    default StateMachineFactory<S, C, I> load(Transition<? extends Event, S, C>... transitions) {
        for (Transition<? extends Event, S, C> transition : transitions) {
            load(transition);
        }
        return this;
    }
}

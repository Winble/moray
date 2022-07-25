package org.winble.moray.domain;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface ITransition<C, S extends IState, E extends IEvent> {
    S from();
    S to();
    E on();
    void action(IStateMachine<C, S, ?> stateMachine, E event);
}

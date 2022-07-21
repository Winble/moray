package org.winble.moray;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface ITransition<C, S extends IState, E extends IEvent> {
    S from();
    S to();
    E on();
    void action(IStateMachine<C, S, E> stateMachine, E event);
}

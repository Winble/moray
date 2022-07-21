package org.winble.moray;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface IStateMachine<C, S extends IState, E extends IEvent> {

    IResult fire(E event);

    C getContext();

    void setContext(C context);

    S getState();

    void setState(S state);
}

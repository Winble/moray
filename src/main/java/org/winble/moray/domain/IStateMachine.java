package org.winble.moray.domain;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface IStateMachine<C, S extends IState, E extends IEvent, R extends IResult> {

    /**
     * Accept and handle state change event
     *
     * @param event received event
     * @return result
     */
    R fire(E event);

    C getContext();

    void setContext(C context);

    S getState();

    void transit(S from, S to);
}

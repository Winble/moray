package org.winble.moray.domain;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface StateMachine<S, C> {

    S getState();

    C getContext();

    /**
     * Accept and handle state change event
     *
     * @param event received event
     * @return result
     */
    Result fire(Event event);
}

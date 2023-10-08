package org.winble.moray.domain;

/**
 * @author bowenzhang
 * Create on 2023/10/8
 */
public interface Context<S> {

    S getState();

    boolean compareAndSetState(S expect, S update);
}

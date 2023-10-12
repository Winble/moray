package org.winble.clip.moray.domain;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface Transition<E extends Event, S, C> {
    E when();
    S from();
    S to();
    Result action(E event, C context);
}

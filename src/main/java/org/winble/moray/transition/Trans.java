package org.winble.moray.transition;

import org.winble.moray.domain.IState;

/**
 * Create on 2022/8/1
 *
 * @author bowenzhang
 */
public class Trans {

    public static <S extends IState> TransitionBuilder.From<S> from(S from) {
        return new TransitionBuilder.From<>(from);
    }

    public static <S extends IState> TransitionBuilder.Stay<S> stay(S stay) {
        return new TransitionBuilder.Stay<>(stay);
    }
}

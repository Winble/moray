package org.winble.moray.transition;

import org.winble.moray.domain.Event;

/**
 * Create on 2022/8/1
 *
 * @author bowenzhang
 */
public class Trans {
    public static <E extends Event> TransitionBuilder.When<E> when(E event) {
        return new TransitionBuilder.When<>(event);
    }
}

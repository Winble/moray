package org.winble.moray.transition.template;

import org.winble.moray.domain.Event;
import org.winble.moray.domain.Transition;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public abstract class AbsTransition<E extends Event, S, C> implements Transition<E, S, C> {

    private final E when;

    private final S from;

    private final S to;

    protected AbsTransition(E when, S from, S to) {
        this.when = when;
        this.from = from;
        this.to = to;
    }

    @Override
    public E when() {
        return this.when;
    }

    @Override
    public S from() {
        return this.from;
    }

    @Override
    public S to() {
        return this.to;
    }
}

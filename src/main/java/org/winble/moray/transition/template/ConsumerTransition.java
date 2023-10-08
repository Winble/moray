package org.winble.moray.transition.template;

import org.winble.moray.domain.Event;
import org.winble.moray.domain.Result;
import org.winble.moray.type.BaseResult;

import java.util.function.BiConsumer;

/**
 * @author bowenzhang
 * Create on 2023/10/8
 */
public class ConsumerTransition<E extends Event, S, C> extends AbsTransition<E, S, C> {

    private final BiConsumer<E, C> consumer;

    public ConsumerTransition(E when, S from, S to, BiConsumer<E, C> consumer) {
        super(when, from, to);
        this.consumer = consumer;
    }

    @Override
    public Result action(E event, C context) {
        this.consumer.accept(event, context);
        return BaseResult.success();
    }
}

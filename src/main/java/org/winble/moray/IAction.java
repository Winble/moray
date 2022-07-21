package org.winble.moray;

import java.util.function.BiFunction;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface IAction<C, E extends IEvent, R extends IResult> extends BiFunction<C, E, R> {
}

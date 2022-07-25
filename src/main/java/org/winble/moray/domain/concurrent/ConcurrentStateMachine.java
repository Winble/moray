package org.winble.moray.domain.concurrent;

import org.winble.moray.domain.IEvent;
import org.winble.moray.domain.IResult;
import org.winble.moray.domain.IState;
import org.winble.moray.domain.IStateMachine;

/**
 * @author bowenzhang
 * Create on 2022/7/25
 */
public interface ConcurrentStateMachine<C, S extends IState, E extends IEvent, R extends IResult> extends IStateMachine<C, S, E, R> {

}

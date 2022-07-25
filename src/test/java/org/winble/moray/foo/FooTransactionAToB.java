package org.winble.moray.foo;

import org.winble.moray.statemachine.SimpleStateMachine;
import org.winble.moray.transition.AbstractTransition;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class FooTransactionAToB extends AbstractTransition<Integer, FooState, FooEvent, SimpleStateMachine<Integer, FooState, FooEvent>> {

    protected FooTransactionAToB() {
        super(FooState.a, FooState.b, FooEvent.a_to_b);
    }

    @Override
    protected Integer doAction(Integer context, FooEvent event) {
        return context + 1;
    }
}

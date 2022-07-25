package org.winble.moray.foo;

import org.winble.moray.transition.AbsTransition;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class FooTransactionAToB extends AbsTransition<Integer, FooState, FooEvent> {

    protected FooTransactionAToB() {
        super(FooState.a, FooState.b, FooEvent.a_to_b);
    }

    @Override
    protected Integer doAction(Integer context, FooEvent event) {
        return context + 1;
    }
}

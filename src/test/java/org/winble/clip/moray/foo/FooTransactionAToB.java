package org.winble.clip.moray.foo;

import org.winble.clip.moray.domain.Result;
import org.winble.clip.moray.transition.template.AbsTransition;
import org.winble.clip.moray.type.ContextResult;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class FooTransactionAToB extends AbsTransition<FooEvent, FooState, Integer> {

    protected FooTransactionAToB() {
        super(FooEvent.a_to_b, FooState.a, FooState.b);
    }

    @Override
    public Result action(FooEvent event, Integer context) {
        return ContextResult.of(context + 1);
    }
}

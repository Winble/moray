package org.winble.moray.foo;

import statemachine.ITransition;
import statemachine.StateMachine;

import java.util.function.BiFunction;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class FooStateMachine extends StateMachine<Integer, FooState, FooEvent> {

    public FooStateMachine(Integer context, FooState state, BiFunction<FooState, FooEvent, ITransition<Integer, FooState, FooEvent>> transitionSupplier) {
        super(context, state, transitionSupplier);
    }
}

package org.winble.moray.foo;

import org.winble.moray.domain.IStateMachine;
import org.winble.moray.factory.AbsStateMachineFactory;
import org.winble.moray.result.BaseResult;
import org.winble.moray.transition.AbsTransition;
import org.winble.moray.statemachine.SimpleStateMachine;
import org.winble.moray.transition.Trans;

import java.text.MessageFormat;
import java.util.function.BiFunction;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class FooStateMachineFactory extends AbsStateMachineFactory<Integer, FooState, BaseResult> {

    public static void main(String[] args) {
        try {
            FooStateMachineFactory fooStateMachineFactory = new FooStateMachineFactory();
            fooStateMachineFactory.load(new FooTransactionAToB());
            fooStateMachineFactory.load(new AbsTransition<Integer, FooState, FooEvent>(FooState.b, FooState.c, FooEvent.b_to_c) {
                @Override
                protected Integer doAction(Integer context, FooEvent event) {
                    return context + 1;
                }
            });
            fooStateMachineFactory.load(Trans.stay(FooState.c).on(FooEvent.c_to_d)
                    .action(Integer.class, (c, e) -> c + 1).build());
            IStateMachine<Integer, FooState, BaseResult> stateMachine = fooStateMachineFactory.get("1");
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
            stateMachine.fire(FooEvent.a_to_b);
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
            stateMachine.fire(FooEvent.b_to_c);
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
            stateMachine.fire(FooEvent.c_to_d);
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected SimpleStateMachine<Integer, FooState> buildStateMachine(String id) {
        return new SimpleStateMachine<>(0, FooState.a, this);
    }
}

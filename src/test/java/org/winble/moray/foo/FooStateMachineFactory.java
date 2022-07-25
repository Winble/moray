package org.winble.moray.foo;

import org.winble.moray.factory.AbstractStateMachineFactory;
import org.winble.moray.transition.AbstractTransition;
import org.winble.moray.statemachine.SimpleStateMachine;

import java.text.MessageFormat;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class FooStateMachineFactory extends AbstractStateMachineFactory<Integer, FooState, FooEvent, SimpleStateMachine<Integer, FooState, FooEvent>> {

    public static void main(String[] args) {
        try {
            FooStateMachineFactory fooStateMachineFactory = new FooStateMachineFactory();
            fooStateMachineFactory.load(new FooTransactionAToB());
            fooStateMachineFactory.load(new AbstractTransition<Integer, FooState, FooEvent>(FooState.b, FooState.c, FooEvent.b_to_c) {
                @Override
                protected Integer doAction(Integer context, FooEvent event) {
                    return context + 1;
                }
            });
            SimpleStateMachine<Integer, FooState, FooEvent> stateMachine = fooStateMachineFactory.get("1");
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
            stateMachine.fire(FooEvent.a_to_b);
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
            stateMachine.fire(FooEvent.b_to_c);
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected SimpleStateMachine<Integer, FooState, FooEvent> buildStateMachine(String id) {
        return new SimpleStateMachine<>(0, FooState.a, super::matchTransition);
    }
}

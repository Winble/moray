package org.winble.clip.moray.foo;

import org.winble.clip.moray.domain.Event;
import org.winble.clip.moray.domain.Result;
import org.winble.clip.moray.domain.StateMachine;
import org.winble.clip.moray.domain.Transition;
import org.winble.clip.moray.statemachine.template.AbsStateMachine;
import org.winble.clip.moray.statemachine.template.AbsStateMachineFactory;
import org.winble.clip.moray.transition.Trans;

import java.text.MessageFormat;

/**
 * @author bowenzhang
 * Create on 2023/10/8
 */
public class FooStateMachineFactory extends AbsStateMachineFactory<FooState, Integer, Integer> {

    @Override
    public StateMachine<FooState, Integer> get(Integer id) {
        return new DelegateStateMachine(FooState.a, id);
    }

    public class DelegateStateMachine extends AbsStateMachine<FooState, Integer> {
        public DelegateStateMachine(FooState state, Integer context) {
            super(state, context);
        }

        @Override
        public Transition<Event, FooState, Integer> matchTransition(FooState from, Event event) {
            return FooStateMachineFactory.this.matchTransition(from, event);
        }
    }

    public static void main(String[] args) {
        FooStateMachineFactory factory = new FooStateMachineFactory();
        factory.load(Trans.when(FooEvent.a_to_b).from(FooState.a).to(FooState.b).action((e, c) -> c + 1));
        StateMachine<FooState, Integer> stateMachine = factory.get(1);
        Result result;
        result = stateMachine.fire(FooEvent.a_to_b);
        System.out.println(MessageFormat.format("result={0}, state={1}, context={2}", result.getMessage(), stateMachine.getState(), stateMachine.getContext()));
        result = stateMachine.fire(FooEvent.a_to_b);
        System.out.println(MessageFormat.format("result={0}, state={1}, context={2}", result.getMessage(), stateMachine.getState(), stateMachine.getContext()));
    }
}

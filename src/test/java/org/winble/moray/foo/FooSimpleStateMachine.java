package org.winble.moray.foo;

import org.winble.moray.domain.Result;
import org.winble.moray.domain.Transition;
import org.winble.moray.statemachine.SimpleStateMachine;
import org.winble.moray.transition.Trans;
import org.winble.moray.type.ContextResult;

import java.text.MessageFormat;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class FooSimpleStateMachine extends SimpleStateMachine<FooState, Integer> {

    public FooSimpleStateMachine() {
        super(FooState.a, 1);
    }

    public static void main(String[] args) {
        try {
            FooSimpleStateMachine stateMachine = new FooSimpleStateMachine();
            stateMachine.load(new FooTransactionAToB())
                    .load(new Transition<FooEvent, FooState, Integer>() {
                        @Override
                        public FooEvent when() {
                            return FooEvent.b_to_c;
                        }

                        @Override
                        public FooState from() {
                            return FooState.b;
                        }

                        @Override
                        public FooState to() {
                            return FooState.c;
                        }

                        @Override
                        public Result action(FooEvent event, Integer context) {
                            return ContextResult.of(context + 1);
                        }
                    })
                    .load(Trans.when(FooEvent.c_to_d).from(FooState.c).to(FooState.d).action((fooEvent, c) -> c + 1));
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
            stateMachine.fire(FooEvent.a_to_b);
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
            stateMachine.fire(FooEvent.b_to_c);
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
            stateMachine.fire(FooEvent.c_to_d);
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
            stateMachine.fire(FooEvent.a_to_b);
            System.out.println(MessageFormat.format("state={0}, context={1}", stateMachine.getState(), stateMachine.getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

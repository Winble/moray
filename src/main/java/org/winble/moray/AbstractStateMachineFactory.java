package org.winble.moray;

import org.unidal.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public abstract class AbstractStateMachineFactory<C, S extends IState, E extends IEvent, M extends IStateMachine<C, S, E>> implements IStateMachineFactory<C, S, E, M> {

    Map<String, M> registry = new HashMap<>();

    Map<Pair<S, String>, ITransition<C, S, E>> transactions = new HashMap<>();

    @Override
    public void launch(ITransition<C, S, E> transition) {
        transactions.put(Pair.from(transition.from(), transition.on().name()), transition);
    }

    @Override
    public M get(String id) {
        if (registry.containsKey(id)) {
            return registry.get(id);
        }
        M stateMachine = this.buildStateMachine(id);
        registry.put(id, stateMachine);
        return stateMachine;
    }

    public ITransition<C, S, E> matchTransition(IState state, IEvent event) {
        Pair<IState, String> key = Pair.from(state, event.name());
        if (transactions.containsKey(key)) {
            return transactions.get(key);
        }
        throw new StateTransitionException(500, "no match transaction");
    }

    protected abstract M buildStateMachine(String id);
}

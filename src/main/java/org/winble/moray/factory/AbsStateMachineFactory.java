package org.winble.moray.factory;

import org.apache.commons.lang3.tuple.Pair;
import org.winble.moray.type.StateTransitionException;
import org.winble.moray.domain.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public abstract class AbsStateMachineFactory<C, S extends IState, E extends IEvent, R extends IResult> implements IStateMachineFactory<C, S, E, R> {

    Map<String, IStateMachine<C, S, E, R>> registry = new HashMap<>();

    Map<Pair<IState, String>, ITransition<C, S, E>> transactions = new HashMap<>();

    @Override
    public void load(ITransition<C, S, E> transition) {
        transactions.put(Pair.of(transition.from(), transition.on().name()), transition);
    }

    @Override
    public IStateMachine<C, S, E, R> get(String id) {
        if (registry.containsKey(id)) {
            return registry.get(id);
        }
        IStateMachine<C, S, E, R> stateMachine = this.buildStateMachine(id);
        registry.put(id, stateMachine);
        return stateMachine;
    }

    @Override
    public ITransition<C, S, E> matchTransition(IState state, IEvent event) {
        Pair<IState, String> key = Pair.of(state, event.name());
        if (transactions.containsKey(key)) {
            return transactions.get(key);
        }
        throw new StateTransitionException(500, "no match transaction");
    }

    protected abstract IStateMachine<C, S, E, R> buildStateMachine(String id);
}

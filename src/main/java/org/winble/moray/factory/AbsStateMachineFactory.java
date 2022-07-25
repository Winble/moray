package org.winble.moray.factory;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.winble.moray.type.StateTransitionException;
import org.winble.moray.domain.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public abstract class AbsStateMachineFactory<C, S extends IState, R extends IResult> implements IStateMachineFactory<C, S, R> {

    Map<String, IStateMachine<C, S, R>> registry = new HashMap<>();

    Map<Triple<IState, String, Class<?>>, ITransition<C, S, ? extends IEvent>> transactions = new HashMap<>();

    @Override
    public void load(ITransition<C, S, ? extends IEvent> transition) {
        transactions.put(Triple.of(transition.from(), transition.on().name(), transition.on().getClass()), transition);
    }

    @Override
    public IStateMachine<C, S, R> get(String id) {
        if (registry.containsKey(id)) {
            return registry.get(id);
        }
        IStateMachine<C, S, R> stateMachine = this.buildStateMachine(id);
        registry.put(id, stateMachine);
        return stateMachine;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends IEvent> ITransition<C, S, E> matchTransition(IState state, E event) {
        Triple<IState, String, Class<?>> key = Triple.of(state, event.name(), event.getClass());
        if (transactions.containsKey(key)) {
            return (ITransition<C, S, E>) transactions.get(key);
        }
        throw new StateTransitionException(500, "no match transaction");
    }

    protected abstract IStateMachine<C, S, R> buildStateMachine(String id);
}

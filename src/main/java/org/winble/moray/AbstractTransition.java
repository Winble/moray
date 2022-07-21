package org.winble.moray;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public abstract class AbstractTransition<C, S extends IState, E extends IEvent> implements ITransition<C, S, E> {

    private final S from;

    private final S to;

    private final E on;

    protected AbstractTransition(S from, E on) {
        this.from = from;
        this.to = from;
        this.on = on;
    }

    protected AbstractTransition(S from, S to, E on) {
        this.from = from;
        this.to = to;
        this.on = on;
    }

    @Override
    public S from() {
        return from;
    }

    @Override
    public S to() {
        return to;
    }

    @Override
    public E on() {
        return on;
    }

    @Override
    public void action(IStateMachine<C, S, E> stateMachine, E event) {
        if (!this.from().equals(stateMachine.getState())) {
            throw new StateTransitionException(500, "illegal transition");
        }
        if (!this.on().name().equals(event.name())) {
            throw new StateTransitionException(501, "invalid event type");
        }
        C context = this.doAction(stateMachine.getContext(), event);
        stateMachine.setContext(context);
        if (!stateMachine.getState().equals(this.to())) {
            stateMachine.setState(this.to());
        }
    }

    protected abstract C doAction(C context, E event);
}

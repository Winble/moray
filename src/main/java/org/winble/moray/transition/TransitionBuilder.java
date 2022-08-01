package org.winble.moray.transition;

import org.apache.commons.lang3.tuple.Pair;
import org.winble.moray.domain.*;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Create on 2022/8/1
 *
 * @author bowenzhang
 */
public class TransitionBuilder {

    protected TransitionBuilder() {
    }

    public static class From<S extends IState> {
        protected final S from;

        protected From(S from) {
            this.from = from;
        }

        public To<S> to(S to) {
            return new To<>(from, to);
        }
    }

    public static class To<S extends IState> extends From<S> {
        protected final S to;

        protected To(S from, S to) {
            super(from);
            this.to = to;
        }

        @SuppressWarnings("unchecked")
        public <E extends IEvent> On<S, E> on(E on) {
            return new On<>(from, to, on.name(), (Class<E>) on.getClass());
        }

        public <E extends IEvent> On<S, E> on(Class<E> on) {
            return new On<>(from, to, on.getSimpleName(), on);
        }
    }

    public static class Stay<S extends IState> extends To<S> {

        protected Stay(S stay) {
            super(stay, stay);
        }
    }

    public static class On<S extends IState, E extends IEvent> extends To<S> {

        protected final Pair<String, Class<E>> on;

        protected On(S from, S to, String onName, Class<E> onClazz) {
            super(from, to);
            this.on = Pair.of(onName, onClazz);
        }

        public On(S from, S to, Pair<String, Class<E>> on) {
            super(from, to);
            this.on = on;
        }

        public <C> Action<E, S, C> action(Class<C> clazz, BiFunction<C, E, C> action) {
            return new Action<>(from, to, on, action);
        }

        public <C> Action<E, S, C> action(BiFunction<C, E, C> action) {
            return new Action<>(from, to, on, action);
        }
    }

    public static class Action<E extends IEvent, S extends IState, C> extends On<S, E> {

        protected final BiFunction<C, E, C> action;

        protected Action(S from, S to, Pair<String, Class<E>> on, BiFunction<C, E, C> action) {
            super(from, to, on);
            this.action = action;
        }

        public ITransition<C, S, E> build() {
            return new AbsTransition<C, S, E>(from, to, on) {
                @Override
                protected C doAction(C context, E event) {
                    return action.apply(context, event);
                }
            };
        }

        public void upload(IStateMachineFactory<C, S, ?> factory) {
            factory.load(build());
        }
    }
}

package org.winble.clip.moray.transition;

import org.winble.clip.moray.domain.Event;
import org.winble.clip.moray.domain.Result;
import org.winble.clip.moray.domain.Transition;
import org.winble.clip.moray.transition.template.AbsTransition;
import org.winble.clip.moray.transition.template.ConsumerTransition;
import org.winble.clip.moray.type.BaseResult;
import org.winble.clip.moray.type.ContextResult;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Create on 2022/8/1
 *
 * @author bowenzhang
 */
public class TransitionBuilder {

    public static class When<E extends Event> {

        protected final E when;

        protected When(E when) {
            this.when = when;
        }

        public <S> From<E, S> from(S... froms) {
            return new From<>(when, froms);
        }

        @SuppressWarnings("unchecked")
        public <S> To<E, S> stay(S stay) {
            return new To<>(when, (S[]) new Object[]{stay}, stay);
        }
    }

    public static class From<E extends Event, S> {

        protected final E when;
        protected final S[] froms;

        protected From(E when, S... froms) {
            this.when = when;
            this.froms = froms;
        }

        public To<E, S> to(S to) {
            return new To<>(when, froms, to);
        }
    }

    public static class To<E extends Event, S> {
        protected final E when;
        protected final S[] froms;
        protected final S to;

        protected To(E when, S[] froms, S to) {
            this.when = when;
            this.froms = froms;
            this.to = to;
        }

        @SuppressWarnings("unchecked")
        public <C> Transition<? extends Event, S, C>[] action(BiConsumer<E, C> consumer) {
            return Arrays.stream(froms).map(from -> new ConsumerTransition<>(when, from, to, consumer))
                    .collect(Collectors.toList()).toArray((Transition<? extends Event, S, C>[]) new Transition<?, ?, ?>[0]);
        }

        @SuppressWarnings("unchecked")
        public <C> Transition<? extends Event, S, C>[] action(BiFunction<E, C, ?> action) {
            return Arrays.stream(froms).map(from -> new AbsTransition<E, S, C>(when, from, to) {
                        @Override
                        public Result action(E event, C context) {
                            Object result = action.apply(event, context);
                            if (result instanceof Result) {
                                return (Result) result;
                            }
                            if (context.getClass().isInstance(result)) {
                                return ContextResult.of(result);
                            }
                            return BaseResult.success();
                        }
                    })
                    .collect(Collectors.toList()).toArray((Transition<? extends Event, S, C>[]) new Transition<?, ?, ?>[0]);
        }
    }
}

package org.winble.moray;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface IResult {
    boolean isSuccess();

    int getCode();

    String getMessage();

    default <T extends IResult, R> R getValue(Function<T, R> getter) {
        return this.getValue(getter, () -> null);
    }

    @SuppressWarnings("unchecked")
    default <T extends IResult, R> R getValue(Function<T, R> getter, Supplier<R> defaultValue) {
        try {
            return getter.apply((T) this);
        } catch (Exception e) {
            return defaultValue.get();
        }
    }
}

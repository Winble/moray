package org.winble.moray.domain;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface IEvent {
    default String name() {
        return this.getClass().getSimpleName();
    }
}

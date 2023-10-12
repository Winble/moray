package org.winble.clip.moray.domain;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface Event {
    default String name() {
        return this.getClass().getSimpleName();
    }
}

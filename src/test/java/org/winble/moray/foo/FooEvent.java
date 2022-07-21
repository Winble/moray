package org.winble.moray.foo;

import statemachine.*;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public enum FooEvent implements IEvent {
    a_to_b, b_to_c, c_to_d, d_to_a
}

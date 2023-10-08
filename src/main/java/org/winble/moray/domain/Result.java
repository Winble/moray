package org.winble.moray.domain;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public interface Result {
    boolean isSuccess();

    int getCode();

    String getMessage();
}

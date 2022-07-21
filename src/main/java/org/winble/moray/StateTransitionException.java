package org.winble.moray;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class StateTransitionException extends RuntimeException {

    private final int code;

    public StateTransitionException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

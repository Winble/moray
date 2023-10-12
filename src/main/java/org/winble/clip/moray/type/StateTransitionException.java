package org.winble.clip.moray.type;

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

    public StateTransitionException(int code, String message, Throwable e) {
        super(message, e);
        this.code = code;
    }

    public StateTransitionException(int code, Throwable e) {
        super(e);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

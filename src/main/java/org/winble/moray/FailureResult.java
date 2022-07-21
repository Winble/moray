package org.winble.moray;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class FailureResult implements IResult {

    private final int code;

    private final String message;

    public FailureResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public FailureResult(StateTransitionException exception) {
        this.code = exception.getCode();
        this.message = exception.getMessage();
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

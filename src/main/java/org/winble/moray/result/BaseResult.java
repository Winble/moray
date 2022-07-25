package org.winble.moray.result;

import org.winble.moray.type.StateTransitionException;
import org.winble.moray.domain.IResult;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class BaseResult implements IResult {

    private boolean success;

    private int code;

    private String message;

    public BaseResult(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static BaseResult success() {
        return new BaseResult(true, 0, "");
    }

    public static BaseResult failure(int code, String message) {
        return new BaseResult(false, code, message);
    }

    public static BaseResult failure(StateTransitionException exception) {
        return new BaseResult(false, exception.getCode(), exception.getMessage());
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

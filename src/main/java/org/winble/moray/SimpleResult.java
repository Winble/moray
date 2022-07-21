package org.winble.moray;

/**
 * @author bowenzhang
 * Create on 2022/7/20
 */
public class SimpleResult implements IResult {

    private boolean success;

    private int code;

    private String message;

    public SimpleResult(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static SimpleResult success() {
        return new SimpleResult(true, 0, "");
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

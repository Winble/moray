package org.winble.moray.type;

import org.winble.moray.domain.Result;

/**
 * @author bowenzhang
 * Create on 2023/10/8
 */
public class ContextResult<V> extends BaseResult {

    private final V context;

    protected ContextResult(boolean success, int code, String message, V value) {
        super(success, code, message);
        this.context = value;
    }

    protected ContextResult(BaseResult result, V value) {
        super(result.isSuccess(), result.getCode(), result.getMessage());
        this.context = value;
    }

    public static <V> Result of(V value) {
        if (null == value) {
            return ReservedErrorResults.CONTEXT_NOT_EXIST.result();
        }
        return new ContextResult<>(BaseResult.success(), value);
    }

    public V getContext() {
        return context;
    }
}

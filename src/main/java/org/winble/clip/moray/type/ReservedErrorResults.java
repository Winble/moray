package org.winble.clip.moray.type;

/**
 * Create on 2022/7/25
 *
 * @author bowenzhang
 */
public enum ReservedErrorResults {
    UNMATCHED_TRANSITION_FROM(500, "unmatched transition from"),
    UNMATCHED_STATE(501, "unmatched state"),
    UNMATCHED_EVENT_TYPE(502, "unmatched event type"),
    TRY_LOCK_FAIL(503, "try statemachine lock fail"),
    CHECK_PRE_CONDITION_FAIL(504, "check pre condition fail"),
    CONTEXT_NOT_EXIST(506, "context not exist"),
    CONTEXT_TYPE_MISMATCH(507, "context type mismatch"),
    UNKNOWN_ERROR(599, "unknown error"),
    ;

    private final int code;

    private final String message;

    ReservedErrorResults(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public StateTransitionException exception() {
        return new StateTransitionException(code, message);
    }

    public BaseResult result() {
        return BaseResult.failure(code, message);
    }

    public StateTransitionException exception(Exception e) {
        return e instanceof StateTransitionException ? (StateTransitionException) e : new StateTransitionException(code, e);
    }
}

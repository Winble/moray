package org.winble.moray.type;

/**
 * Create on 2022/7/25
 *
 * @author bowenzhang
 */
public enum ReservedErrorResults {
    UNMATCHED_TRANSITION_FROM(500, "unmatched transition from"),
    UNMATCHED_EVENT_TYPE(501, "unmatched event type"),
    TRY_LOCK_FAIL(502, "try statemachine lock fail"),
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

    public StateTransitionException exception(Exception e) {
        return e instanceof StateTransitionException ? (StateTransitionException) e : new StateTransitionException(code, e);
    }
}

package vn.back_end_best_practice.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    USER_EXISTS( 1002 ,"User already exists"),
    METHOD_NOT_ALLOWED( 405 ,"Method not allowed"),
    NOT_FOUND( 404 ,"Resource not found");

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

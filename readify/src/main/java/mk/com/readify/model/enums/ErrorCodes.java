package mk.com.readify.model.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCodes {
    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No error code"),
    INCORRECT_CURRENT_PASSWORD(300, HttpStatus.BAD_REQUEST, "The current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, HttpStatus.BAD_REQUEST, "The new password does not match the confirmed password"),
    ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "User account is currently locked"),
    ACCOUNT_DISABLED(303, HttpStatus.FORBIDDEN, "User account is currently disabled"),
    BAD_CREDENTIALS(304, HttpStatus.FORBIDDEN, "Incorrect login credentials provided"),
    ;
    @Getter
    private final int code;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final String description;

    ErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}

package org.hyperskill.hyperid.exception;

import org.springframework.http.HttpStatus;

public class UsernameTakenException extends ApiException {
    public UsernameTakenException() {
        super(HttpStatus.CONFLICT, "This username already used by someone else.");
    }
}

package org.hyperskill.hyperid.exception;

import org.springframework.http.HttpStatus;

public class InvalidAccessTokenException extends ApiException {
    public InvalidAccessTokenException() {
        super(HttpStatus.FORBIDDEN, "You don't have access rights. Please authorize.");
    }
}

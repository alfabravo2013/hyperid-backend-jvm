package org.hyperskill.hyperid.exception;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends ApiException {
    public BadCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Username or password don't match any known.");
    }
}

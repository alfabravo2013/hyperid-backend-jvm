package org.hyperskill.hyperid.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    protected final HttpStatus httpStatusCode;
    public ApiException(HttpStatus code, String reason) {
        super(reason);
        httpStatusCode = code;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}

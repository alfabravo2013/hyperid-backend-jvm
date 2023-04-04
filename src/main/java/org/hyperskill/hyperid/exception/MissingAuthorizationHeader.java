package org.hyperskill.hyperid.exception;

import org.springframework.http.HttpStatus;

public class MissingAuthorizationHeader extends ApiException {
    public MissingAuthorizationHeader() {
        super(HttpStatus.FORBIDDEN, "Header 'Authorization' is empty");
    }
}

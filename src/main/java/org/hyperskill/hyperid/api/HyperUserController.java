package org.hyperskill.hyperid.api;

import org.hyperskill.hyperid.exception.ApiException;
import org.hyperskill.hyperid.exception.MissingAuthorizationHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class HyperUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyperUserController.class);
    private final HyperUserService service;

    public HyperUserController(HyperUserService service) {
        this.service = service;
    }

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public void register(@Valid @RequestBody UserCredentialsDto request) {
        LOGGER.info("REGISTRATION REQUEST={}", request);
        service.registerUser(request);
    }

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserDto> login(@Valid @RequestBody UserCredentialsDto request) {
        LOGGER.info("LOGIN REQUEST={}", request);

        var wrapper = service.loginUser(request);
        var body = wrapper.userDto();
        var accessToken = wrapper.accessToken();

        var headers = new HttpHeaders();
        headers.add("accessToken", accessToken);
        headers.add("Access-Control-Expose-Headers", "accessToken");

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    @PostMapping(path = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request) {
        var token = getToken(request);
        service.logoutUser(token);
    }

    @GetMapping(
            path = "/account",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserInfo(HttpServletRequest request) {
        var token = getToken(request);
        return service.getAccountDetails(token);
    }

    @PutMapping(
            path = "/account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(
            HttpServletRequest request,
            @Valid @RequestBody UserUpdateRequest updateRequest
    ) {
        var token = getToken(request);
        return service.updateAndGetUser(updateRequest, token);
    }

    private static String getToken(HttpServletRequest request) {
        var token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            throw new MissingAuthorizationHeader();
        }
        return token;
    }

    @ControllerAdvice
    static class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(ApiException.class)
        ResponseEntity<ApiError> handleApiException(ApiException e) {
            return ResponseEntity
                    .status(e.getHttpStatusCode())
                    .body(ApiError.of(e.getMessage()));
        }

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                MethodArgumentNotValidException ex,
                HttpHeaders headers,
                HttpStatus status,
                WebRequest request) {
            var message = ex.getBindingResult().getAllErrors().stream()
                    .findFirst()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .orElse(ex.getMessage());
            var body = ApiError.of(message);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(body);
        }
    }
}

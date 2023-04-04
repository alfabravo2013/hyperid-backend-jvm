package org.hyperskill.hyperid.api;

import java.util.Map;

public record ApiError(Map<String, String> error) {
    public static ApiError of(String message) {
        return new ApiError(Map.of("message", message));
    }
}

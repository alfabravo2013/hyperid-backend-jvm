package org.hyperskill.hyperid.api;

import javax.validation.constraints.NotBlank;

public record UserCredentialsDto(
        @NotBlank(message = "Field 'username' is empty")
        String username,
        @NotBlank(message = "Field 'password' is empty")
        String password
) {
}

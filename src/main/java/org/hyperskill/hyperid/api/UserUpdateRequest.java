package org.hyperskill.hyperid.api;

import javax.validation.constraints.NotBlank;

public record UserUpdateRequest(
        @NotBlank(message = "Field 'name' is empty.")
        String name,
        @NotBlank(message = "Field 'surname' is empty.")
        String surname
) {
}

package org.hyperskill.hyperid.api;

public record AuthenticatedUserWrapper(UserDto userDto, String accessToken) {
    public static AuthenticatedUserWrapper from(HyperUser user) {
        return new AuthenticatedUserWrapper(UserDto.from(user), user.getAccessToken());
    }
}

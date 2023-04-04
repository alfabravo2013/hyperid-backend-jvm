package org.hyperskill.hyperid.api;

public record UserDto(String username, String name, String surname) {
    public static UserDto from(HyperUser user) {
        return new UserDto(user.getUsername(), user.getName(), user.getSurname());
    }
}

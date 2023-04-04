package org.hyperskill.hyperid.api;

import org.hyperskill.hyperid.exception.BadCredentialsException;
import org.hyperskill.hyperid.exception.InvalidAccessTokenException;
import org.hyperskill.hyperid.exception.UsernameTakenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class HyperUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyperUserService.class);
    private final HyperUserRepository repository;

    public HyperUserService(HyperUserRepository repository) {
        this.repository = repository;
    }

    public void registerUser(UserCredentialsDto credentials) {
        var user = new HyperUser()
                .withUsername(credentials.username())
                .withPassword(credentials.password());
        try {
            repository.save(user);
        } catch (DataIntegrityViolationException e) {
            LOGGER.info("Failed to save new user={}, reason={}", user, e.getMessage());
            throw new UsernameTakenException();
        }
    }

    public AuthenticatedUserWrapper loginUser(UserCredentialsDto request) {
        var user = repository
                .findByUsername(request.username())
                .orElseThrow(BadCredentialsException::new);

        verifyCredentials(request, user);

        var accessToken = UUID.randomUUID().toString();
        repository.save(user.withAccessToken(accessToken));

        return AuthenticatedUserWrapper.from(user);
    }

    @Transactional
    public void logoutUser(String token) {
        repository.invalidateToken(token);
    }

    public UserDto getAccountDetails(String token) {
        return repository
                .findByAccessToken(token)
                .map(UserDto::from)
                .orElseThrow(InvalidAccessTokenException::new);
    }

    public UserDto updateAndGetUser(UserUpdateRequest request, String token) {
        var user = repository.findByAccessToken(token)
                .map(u -> u.withName(request.name()).withSurname(request.surname()))
                .orElseThrow(InvalidAccessTokenException::new);

        repository.save(user);

        return UserDto.from(user);
    }

    private static void verifyCredentials(UserCredentialsDto request, HyperUser user) {
        if (!Objects.equals(user.getPassword(), request.password())) {
            throw new BadCredentialsException();
        }
    }
}

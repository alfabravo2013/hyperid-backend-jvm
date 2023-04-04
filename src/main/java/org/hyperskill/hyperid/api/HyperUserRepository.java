package org.hyperskill.hyperid.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HyperUserRepository extends JpaRepository<HyperUser, Long> {
    Optional<HyperUser> findByUsername(String username);

    @Modifying
    @Query(value = "UPDATE HyperUser u SET u.accessToken = NULL WHERE u.accessToken = :token")
    void invalidateToken(String token);

    Optional<HyperUser> findByAccessToken(String token);
}

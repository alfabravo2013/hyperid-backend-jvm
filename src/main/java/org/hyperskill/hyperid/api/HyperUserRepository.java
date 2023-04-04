package org.hyperskill.hyperid.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HyperUserRepository extends JpaRepository<HyperUser, Long> {
    Optional<HyperUser> findByUsername(String username);
}

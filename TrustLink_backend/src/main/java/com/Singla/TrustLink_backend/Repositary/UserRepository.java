package com.Singla.TrustLink_backend.Repositary;

import com.Singla.TrustLink_backend.modles.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}

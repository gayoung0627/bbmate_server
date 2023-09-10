package com.bb.bb_server.repository;

import com.bb.bb_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);


}

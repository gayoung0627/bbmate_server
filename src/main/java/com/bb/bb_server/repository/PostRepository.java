package com.bb.bb_server.repository;

import com.bb.bb_server.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);
    List<Post> findByUserId(Long userId);

}

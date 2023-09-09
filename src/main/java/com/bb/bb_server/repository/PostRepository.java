package com.bb.bb_server.repository;

import com.bb.bb_server.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

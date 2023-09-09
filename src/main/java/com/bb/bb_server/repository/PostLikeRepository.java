package com.bb.bb_server.repository;

import com.bb.bb_server.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}

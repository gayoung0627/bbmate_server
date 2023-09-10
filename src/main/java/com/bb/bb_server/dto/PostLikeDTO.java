package com.bb.bb_server.dto;

import com.bb.bb_server.domain.Post;
import com.bb.bb_server.domain.PostLike;
import com.bb.bb_server.domain.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeDTO {
    private Long id;
    private Long userId;
    private Long postId;

    public PostLike toEntity() {
        return PostLike.builder()
                .id(id)
                .user(User.builder().id(userId).build())
                .post(Post.builder().id(postId).build())
                .build();
    }

    public static PostLikeDTO toDTO(PostLike postLike) {
        return new PostLikeDTO(
                postLike.getId(),
                postLike.getUser().getId(),
                postLike.getPost().getId());
    }
}
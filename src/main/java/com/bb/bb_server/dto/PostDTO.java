package com.bb.bb_server.dto;

import com.bb.bb_server.domain.Post;
import com.bb.bb_server.domain.PostStatus;
import com.bb.bb_server.domain.Stadium;
import com.bb.bb_server.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String status;
    private int maxParticipants;
    private String prefer;
    private LocalDateTime gameDate;
    private String stadium;
    private Long userId;

    public Post toEntity() {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .status(PostStatus.valueOf(status))
                .maxParticipants(maxParticipants)
                .prefer(prefer)
                .gameDate(gameDate)
                .stadium(Stadium.valueOf(stadium))
                .user(User.builder().id(userId).build())
                .build();
    }

    public static PostDTO toDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getStatus().name(),
                post.getMaxParticipants(),
                post.getPrefer(),
                post.getGameDate(),
                post.getStadium().name(),
                post.getUser().getId());
    }

}

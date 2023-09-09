package com.bb.bb_server.dto;

import com.bb.bb_server.domain.Comment;
import com.bb.bb_server.domain.Post;
import com.bb.bb_server.domain.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private Long postId;
    private Long userId;


    public Comment toEntity() {

        return Comment.builder()
                .id(id)
                .content(content)
                .post(Post.builder().id(postId).build())
                .user(User.builder().id(userId).build())
                .build();
    }

    public static CommentDTO toDTO(Comment comment) {

        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .build();
    }
}
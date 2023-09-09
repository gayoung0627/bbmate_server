package com.bb.bb_server.service;

import com.bb.bb_server.domain.Comment;
import com.bb.bb_server.domain.Post;
import com.bb.bb_server.domain.User;
import com.bb.bb_server.dto.CommentDTO;
import com.bb.bb_server.repository.CommentRepository;
import com.bb.bb_server.repository.PostRepository;
import com.bb.bb_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CommentDTO> getAllComments(){
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(CommentDTO::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));
        return CommentDTO.toDTO(comment);
    }

    //단건 조회만 됨
    @Transactional(readOnly = true)
    public List<CommentDTO> getAllCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        List<Comment> comments = post.getComments();

        return comments.stream().map(CommentDTO::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        Long postId = commentDTO.getPostId();
        Long userId = commentDTO.getUserId();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Comment comment = Comment.builder()
                .content(commentDTO.getContent())
                .post(post)
                .user(user)
                .build();

        Comment createdComment = commentRepository.save(comment);
        return CommentDTO.toDTO(createdComment);
    }


    @Transactional
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));
        existingComment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(existingComment);
        return CommentDTO.toDTO(updatedComment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }



}

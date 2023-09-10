package com.bb.bb_server.service;

import com.bb.bb_server.domain.Post;
import com.bb.bb_server.domain.PostLike;
import com.bb.bb_server.domain.User;
import com.bb.bb_server.dto.PostLikeDTO;
import com.bb.bb_server.repository.PostLikeRepository;
import com.bb.bb_server.repository.PostRepository;
import com.bb.bb_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    public List<PostLikeDTO> getLikesByUserId(Long userId) {
        List<PostLike> postLikes = postLikeRepository.findByUserId(userId);
        return postLikes.stream()
                .map(PostLikeDTO::toDTO)
                .collect(Collectors.toList());
    }

    public List<PostLikeDTO> getLikesByPostId(Long postId) {
        List<PostLike> postLikes = postLikeRepository.findByPostId(postId);
        return postLikes.stream()
                .map(PostLikeDTO::toDTO)
                .collect(Collectors.toList());
    }

    public void likePost(Long userId, Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long loggedInUserId = getUserIdFromAuthentication(authentication);

        if (loggedInUserId.equals(userId)) {
            List<PostLike> existingLikes = postLikeRepository.findByUserIdAndPostId(userId, postId);

            if (existingLikes.isEmpty()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
                Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new IllegalArgumentException("Post not found"));

                PostLike postLike = PostLike.builder()
                        .user(user)
                        .post(post)
                        .build();
                postLikeRepository.save(postLike);
            }
        } else {
            throw new IllegalArgumentException("You are not authorized to like this post");
        }
    }

    public void cancelLikePost(Long userId, Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long loggedInUserId = getUserIdFromAuthentication(authentication);

        if (loggedInUserId.equals(userId)) {
            List<PostLike> existingLikes = postLikeRepository.findByUserIdAndPostId(userId, postId);
            if (!existingLikes.isEmpty()) {
                postLikeRepository.deleteAll(existingLikes);
            }
        } else {
            throw new IllegalArgumentException("You are not authorized to cancel the like for this post");
        }
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        if (user != null) {
            return user.getId();
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}
package com.bb.bb_server.service;

import com.bb.bb_server.domain.Post;
import com.bb.bb_server.domain.PostStatus;
import com.bb.bb_server.domain.Stadium;
import com.bb.bb_server.domain.User;
import com.bb.bb_server.dto.PostDTO;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 전체 POST 조회
    @Transactional(readOnly = true)
    public List<PostDTO> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostDTO::toDTO).collect(Collectors.toList());
    }

    // 개별 POST 조회
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return PostDTO.toDTO(post);
    }

    // POST 생성
    @Transactional
    public PostDTO createPost(PostDTO postDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = getUserIdFromAuthentication(authentication);
        postDTO.setUserId(userId);
        Post post = postDTO.toEntity();
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        post.setUser(user);
        post = postRepository.save(post);
        return PostDTO.toDTO(post);
    }

    // POST 수정
    @Transactional
    public PostDTO updatePost(Long id, PostDTO postDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = getUserIdFromAuthentication(authentication);

        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        if (!userId.equals(existingPost.getUser().getId())) {
            throw new IllegalArgumentException("You are not authorized to update this post");
        }

        existingPost.updatePost(
                postDto.getTitle(),
                PostStatus.valueOf(postDto.getStatus()),
                postDto.getMaxParticipants(),
                postDto.getPrefer(),
                postDto.getGameDate(),
                Stadium.valueOf(postDto.getStadium()),
                postDto.getContent()
        );

        Post updatedPost = postRepository.save(existingPost);
        return PostDTO.toDTO(updatedPost);
    }

    // POST 삭제
    @Transactional
    public void deletePost(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = getUserIdFromAuthentication(authentication);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        if (!userId.equals(post.getUser().getId())) {
            throw new IllegalArgumentException("You are not authorized to delete this post");
        }

        postRepository.deleteById(id);
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

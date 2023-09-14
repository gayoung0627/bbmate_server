package com.bb.bb_server.controller;

import com.bb.bb_server.domain.PostLike;
import com.bb.bb_server.dto.PostLikeDTO;
import com.bb.bb_server.service.PostLikeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @ApiOperation(value = "Like a Post", notes = "POST 방식으로 특정 게시물에 좋아요")
    @PostMapping("/likes/{postId}/{userId}")
    public ResponseEntity<String> likePost(@PathVariable Long userId, @PathVariable Long postId) {
        postLikeService.likePost(userId, postId);
        return ResponseEntity.ok("Liked post successfully.");
    }

    @ApiOperation(value = "Cancel a Post Like", notes = "POST 방식으로 특정 게시물의 좋아요를 취소")
    @PostMapping("/likes/cancel/{postId}/{userId}")
    public ResponseEntity<String> cancelLikePost(@PathVariable Long userId, @PathVariable Long postId) {
        postLikeService.cancelLikePost(userId, postId);
        return ResponseEntity.ok("Cancelled post like successfully.");
    }

    @ApiOperation(value = "Get Likes by User ID", notes = "GET 방식으로 특정 사용자의 좋아요 목록을 조회")
    @GetMapping("/likes/user/{userId}")
    public ResponseEntity<List<PostLikeDTO>> getLikesByUserId(@PathVariable Long userId) {
        List<PostLikeDTO> likes = postLikeService.getLikesByUserId(userId);
        return ResponseEntity.ok(likes);
    }

    @ApiOperation(value = "Get Likes by Post ID", notes = "GET 방식으로 특정 게시물의 좋아요 목록을 조회")
    @GetMapping("/likes/post/{postId}")
    public ResponseEntity<List<PostLikeDTO>> getLikesByPostId(@PathVariable Long postId) {
        List<PostLikeDTO> likes = postLikeService.getLikesByPostId(postId);
        return ResponseEntity.ok(likes);
    }
}
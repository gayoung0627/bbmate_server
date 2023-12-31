package com.bb.bb_server.controller;

import com.bb.bb_server.dto.CommentDTO;
import com.bb.bb_server.dto.PostDTO;
import com.bb.bb_server.service.CommentService;
import com.bb.bb_server.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;


    @ApiOperation(value = "All Posts GET", notes = "GET 방식으로 모든 Post 조회")
    @GetMapping()
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @ApiOperation(value = "One Post GET", notes = "GET 방식으로 하나의 Post 조회")
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){
        PostDTO post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @ApiOperation(value = "create Post POST", notes = "POST 방식으로 Post 생성")
    @PostMapping("/write")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        PostDTO createdPost = postService.createPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @ApiOperation(value = "update Post PUT", notes = "PUT 방식으로 Post 수정")
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(updatedPost);
    }

    @ApiOperation(value = "delete Post DELETE", notes = "DELETE 방식으로 Post 삭제")
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "All Comments for a Post GET", notes = "GET 방식으로 특정 Post에 대한 모든 Comment 조회")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getAllCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

}

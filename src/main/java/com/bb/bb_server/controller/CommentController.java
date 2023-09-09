package com.bb.bb_server.controller;

import com.bb.bb_server.dto.CommentDTO;
import com.bb.bb_server.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "All Comments GET", notes = "GET 방식으로 모든 Comment 조회")
    @GetMapping("/")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @ApiOperation(value = "One Comment GET", notes = "GET 방식으로 하나의 Comment 조회")
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id){
        CommentDTO comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    //API 주소 다시 생각해보기
    // 재작성해야됨 지금 조회 이상하게됨
    @ApiOperation(value = "All Comments for a Post GET", notes = "GET 방식으로 특정 Post에 대한 모든 Comment 조회")
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getAllCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @ApiOperation(value = "Create Comment POST", notes = "POST 방식으로 Comment 생성")
    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO){
        CommentDTO createdComment = commentService.createComment(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @ApiOperation(value = "Update Comment PUT", notes = "PUT 방식으로 Comment 수정")
    @PutMapping("/update/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    @ApiOperation(value = "Delete Comment DELETE", notes = "DELETE 방식으로 Comment 삭제")
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}

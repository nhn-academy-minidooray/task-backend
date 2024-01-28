package com.nhnacademy.minidooray.task.backend.controller;

import com.nhnacademy.minidooray.task.backend.domain.dto.comment.CommentDto;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.task.TaskIdOnlyRequest;
import com.nhnacademy.minidooray.task.backend.domain.requestbody.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.task.backend.service.interfaces.CommentService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CommentDto>> getCommentList(@RequestBody TaskIdOnlyRequest request) {
        return ResponseEntity.ok().body(commentService.findCommentListByTask(request.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createComment(@Valid @RequestBody CommentRequest commentRequest) {
        boolean isProcessed = commentService.createComment(commentRequest);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();

    }

    @PutMapping("/{commentId}/modify")
    public ResponseEntity<Void> modifyComment(@PathVariable("commentId") Long commentId,
                                              @Valid @RequestBody CommentModifyRequest commentModifyRequest) {
        boolean isProcessed = commentService.modifyComment(commentId, commentModifyRequest);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {
        boolean isProcessed = commentService.deleteComment(commentId);

        return isProcessed
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();

    }
}
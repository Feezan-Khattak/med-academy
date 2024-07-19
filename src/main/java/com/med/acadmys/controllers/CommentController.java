package com.med.acadmys.controllers;

import com.med.acadmys.dtos.Response;
import com.med.acadmys.models.Comment;
import com.med.acadmys.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "*")
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add")
    ResponseEntity<Response> addComment(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "questionId") Long questionId,
            @RequestBody Comment comment
    ) {
        Response response = commentService.addComment(userId, questionId, comment);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    ResponseEntity<Response> updateComment(@RequestBody Comment comment) {
        Response response = commentService.updateComment(comment);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    ResponseEntity<Response> getCommentById(@PathVariable Long id) {
        Response response = commentService.getCommentById(id);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-user/{userId}")
    ResponseEntity<Response> getCommentsByUserId(
            @PathVariable Long userId,
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Response response = commentService.getCommentsByUserId(userId, pageable);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-question/{questionId}")
    ResponseEntity<Response> getCommentsByQuestionId(
            @PathVariable Long questionId,
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Response response = commentService.getCommentsByQuestionId(questionId, pageable);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{commentId}/like")
    ResponseEntity<Response> commentLiked(@PathVariable Long commentId) {
        Response response = commentService.commentLiked(commentId);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{commentId}/disLike")
    ResponseEntity<Response> commentDisLiked(@PathVariable Long commentId) {
        Response response = commentService.commentDisliked(commentId);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}

package com.bamdoliro.stupetition.domain.petition.presentation;

import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.CommentRequest;
import com.bamdoliro.stupetition.domain.petition.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void writeComment(
            @RequestBody @Valid CommentRequest request
    ) {
        commentService.writeComment(request);
    }

    @DeleteMapping("/{comment-id}")
    public void deleteComment(@PathVariable("comment-id") Long commentId) {
        commentService.deleteComment(commentId);
    }
}

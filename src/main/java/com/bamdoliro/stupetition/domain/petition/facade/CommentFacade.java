package com.bamdoliro.stupetition.domain.petition.facade;

import com.bamdoliro.stupetition.domain.petition.domain.Comment;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.AnswerRepository;
import com.bamdoliro.stupetition.domain.petition.domain.repository.CommentRepository;
import com.bamdoliro.stupetition.domain.petition.exception.AlreadyApproveException;
import com.bamdoliro.stupetition.domain.petition.exception.CommentNotFoundException;
import com.bamdoliro.stupetition.domain.petition.exception.NotOwnCommentException;
import com.bamdoliro.stupetition.domain.petition.exception.PetitionNotFoundException;
import com.bamdoliro.stupetition.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentRepository commentRepository;

    public Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> CommentNotFoundException.EXCEPTION);
    }

    public void checkOwnComment(User user, Comment comment) {
        if (!comment.getUser().getId().equals(user.getId())) {
            throw NotOwnCommentException.EXCEPTION;
        }
    }
}

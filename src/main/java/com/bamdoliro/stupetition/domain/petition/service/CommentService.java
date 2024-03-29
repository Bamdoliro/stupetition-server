package com.bamdoliro.stupetition.domain.petition.service;

import com.bamdoliro.stupetition.domain.petition.domain.Comment;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.CommentRepository;
import com.bamdoliro.stupetition.domain.petition.facade.CommentFacade;
import com.bamdoliro.stupetition.domain.petition.facade.PetitionFacade;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.request.CommentRequest;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserFacade userFacade;
    private final PetitionFacade petitionFacade;
    private final CommentRepository commentRepository;
    private final CommentFacade commentFacade;

    @Transactional
    public void writeComment(CommentRequest request) {
        User user = userFacade.getCurrentUser();
        Petition petition = petitionFacade.findPetitionById(request.getPetitionId());

        commentRepository.save(request.toEntity(user, petition));
    }

    @Transactional
    public void deleteComment(Long commentId) {
        User user = userFacade.getCurrentUser();
        Comment comment = commentFacade.getComment(commentId);
        commentFacade.checkOwnComment(user, comment);

        comment.delete();
    }
}

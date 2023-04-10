package com.bamdoliro.stupetition.domain.petition.domain.repository;

import com.bamdoliro.stupetition.domain.petition.domain.Comment;
import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.presentation.dto.response.CommentResponse;
import com.bamdoliro.stupetition.domain.user.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    void deleteByUser(User user);

    void deleteByPetitionId(Long id);

    @Query("SELECT c FROM Comment c WHERE c.petition = :petition AND c.status <> 'DELETED'")
    List<CommentResponse> findByPetition(Petition petition);
}

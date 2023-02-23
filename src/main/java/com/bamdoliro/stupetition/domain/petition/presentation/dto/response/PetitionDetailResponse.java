package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PetitionDetailResponse {

    private Long id;
    private String title;
    private String content;
    private Status status;
    private Boolean approved;
    private int numberOfApprover;
    private LocalDateTime createdAt;
    private List<CommentResponse> comments;
    private String answer;
    private UserResponse writer;

    public static PetitionDetailResponse of(Petition petition, Boolean approved, int numberOfApprover) {
        return PetitionDetailResponse.builder()
                .id(petition.getId())
                .title(petition.getTitle())
                .content(petition.getContent())
                .status(petition.getStatus())
                .approved(approved)
                .numberOfApprover(numberOfApprover)
                .createdAt(petition.getCreatedAt())
                .comments(petition.getComment().stream()
                        .map(CommentResponse::of)
                        .collect(Collectors.toList()))
                .answer(petition.getStatus() == Status.ANSWERED ? petition.getAnswer().getComment() : null)
                .writer(UserResponse.of(petition.getUser()))
                .build();
    }
}

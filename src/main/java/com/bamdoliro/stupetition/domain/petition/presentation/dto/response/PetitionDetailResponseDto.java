package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PetitionDetailResponseDto {

    private Long id;
    private String userEmail;
    private String title;
    private String content;
    private Status status;
    private Boolean approved;
    private int numberOfApprover;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> comments;
    private String answer;

    public static PetitionDetailResponseDto of(Petition petition, Boolean approved, int numberOfApprover) {
        return PetitionDetailResponseDto.builder()
                .id(petition.getId())
                .userEmail(petition.getUser().getEmail())
                .title(petition.getTitle())
                .content(petition.getContent())
                .status(petition.getStatus())
                .approved(approved)
                .numberOfApprover(numberOfApprover)
                .createdAt(petition.getCreatedAt())
                .comments(petition.getComment().stream()
                        .map(CommentResponseDto::of)
                        .collect(Collectors.toList()))
                .answer(petition.getStatus() == Status.ANSWERED ? petition.getAnswer().getComment() : null)
                .build();
    }
}

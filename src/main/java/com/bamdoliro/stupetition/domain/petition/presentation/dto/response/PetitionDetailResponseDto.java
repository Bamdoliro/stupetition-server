package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PetitionDetailResponseDto {

    private Long id;
    private String title;
    private String content;
    private Status status;
    private Boolean approved;
    private int numberOfApprover;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> comments;
    private String answer;
    private UserResponseDto writer;

    public static PetitionDetailResponseDto of(Petition petition, Boolean approved, int numberOfApprover) {
        return PetitionDetailResponseDto.builder()
                .id(petition.getId())
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
                .writer(UserResponseDto.of(petition.getUser()))
                .build();
    }
}

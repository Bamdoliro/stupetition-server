package com.bamdoliro.stupetition.domain.petition.presentation.dto.response;

import com.bamdoliro.stupetition.domain.petition.domain.Petition;
import com.bamdoliro.stupetition.domain.petition.domain.repository.ApproverRepository;
import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.domain.user.domain.repository.UserRepository;
import com.bamdoliro.stupetition.domain.user.presentation.dto.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Builder
public class PetitionDetailResponse {

    private Long id;
    private String title;
    private String content;
    private Status status;
    private Boolean approved;
    private int percentageOfApprover;
    private int numberOfApprover;
    private LocalDateTime createdAt;
    private List<CommentResponse> comments;
    private List<AnswerResponse> answer;
    private UserResponse writer;
    private Boolean hasPermission;

    public static PetitionDetailResponse of(Petition petition, ApproverRepository approverRepository, UserRepository userRepository, User user) {
        return PetitionDetailResponse.builder()
                .id(petition.getId())
                .title(petition.getTitle())
                .content(petition.getContent())
                .status(petition.getStatus())
                .approved(petition.hasUserApproved(user, approverRepository))
                .percentageOfApprover(petition.getPercentageOfApprover(approverRepository, userRepository))
                .numberOfApprover(petition.getNumberOfApprover(approverRepository))
                .createdAt(petition.getCreatedAt())
                .comments(petition.getComment().stream()
                        .map(c -> CommentResponse.of(c, user))
                        .collect(Collectors.toList()))
                .answer(petition.getStatus() == Status.ANSWERED ?
                        petition.getAnswer().stream()
                                .map(a -> AnswerResponse.of(a, user))
                                .collect(Collectors.toList())
                        : null)
                .writer(UserResponse.of(petition.getUser()))
                .hasPermission(Objects.equals(user.getId(), petition.getUser().getId()))
                .build();
    }
}

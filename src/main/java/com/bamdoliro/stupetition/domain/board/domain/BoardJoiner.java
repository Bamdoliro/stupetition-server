package com.bamdoliro.stupetition.domain.board.domain;

import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;
import com.bamdoliro.stupetition.global.utils.BooleanToYNConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board_joiner")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BoardJoiner extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_joiner_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(length = 4000, nullable = false)
    private String comment;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(length = 1, nullable = false)
    private Boolean isStudentCouncil;

    @Builder
    public BoardJoiner(User user, Board board, String comment, Boolean isStudentCouncil) {
        this.user = user;
        this.board = board;
        this.comment = comment;
        this.isStudentCouncil = isStudentCouncil;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}

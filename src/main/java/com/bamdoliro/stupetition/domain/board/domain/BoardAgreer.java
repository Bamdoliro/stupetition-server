package com.bamdoliro.stupetition.domain.board.domain;

import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board_agreer_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BoardAgreer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(length = 500, nullable = false)
    private String comment;

    @Builder
    public BoardAgreer(User user, Board board, String comment) {
        this.user = user;
        this.board = board;
        this.comment = comment;
    }
}

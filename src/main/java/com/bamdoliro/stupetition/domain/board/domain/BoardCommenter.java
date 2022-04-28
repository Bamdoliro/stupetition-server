package com.bamdoliro.stupetition.domain.board.domain;

import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board_commenter_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BoardCommenter extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(length = 4000, nullable = false)
    private String comment;

    @Builder
    public BoardCommenter(User user, Board board, String comment) {
        this.user = user;
        this.board = board;
        this.comment = comment;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}

package com.bamdoliro.stupetition.domain.petition.domain;

import com.bamdoliro.stupetition.domain.petition.domain.type.CommentStatus;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petition_id", nullable = false)
    private Petition petition;

    @Column(length = 500, nullable = false)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private CommentStatus status;

    @Builder
    public Comment(User user, Petition petition, String comment) {
        this.user = user;
        this.petition = petition;
        this.comment = comment;
        this.status = CommentStatus.COMMENT;
    }

    public void delete() {
        this.status = CommentStatus.DELETED;
    }
}

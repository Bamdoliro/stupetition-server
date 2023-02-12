package com.bamdoliro.stupetition.domain.petition.domain;

import com.bamdoliro.stupetition.domain.petition.domain.type.Status;
import com.bamdoliro.stupetition.domain.school.domain.School;
import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_petition")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Petition extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petition_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", length = 4000, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 9, nullable = false)
    private Status status;

    @OneToMany(mappedBy = "petition")
    private List<Approver> approver = new ArrayList<>();

    @OneToMany(mappedBy = "petition")
    private List<Comment> comment = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peitition", nullable = true)
    private Answer answer;

    @Builder
    public Petition(School school, User user, String title, String content) {
        this.school = school;
        this.user = user;
        this.title = title;
        this.content = content;
        this.status = Status.PETITION;
    }

    public void updatePetition(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}

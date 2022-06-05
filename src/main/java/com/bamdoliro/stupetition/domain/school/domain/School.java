package com.bamdoliro.stupetition.domain.school.domain;

import com.bamdoliro.stupetition.domain.user.domain.User;
import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "school_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class School extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;

    @Column(length = 20, unique = true)
    private String name;

    @Column(length = 20)
    private String emailDomain;

    @Column
    private int numberOfMembers;

    @OneToMany(mappedBy = "school")
    private List<User> membersOfSchool = new ArrayList<>();

    @Builder
    public School(String name, String emailDomain) {
        this.name = name;
        this.emailDomain = emailDomain;
    }

    public void addMember() {
        numberOfMembers++;
    }

    public void subtractMember() {
        numberOfMembers--;
    }
}

package com.bamdoliro.stupetition.domain.school.domain;

import com.bamdoliro.stupetition.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "school_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class School extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;
}

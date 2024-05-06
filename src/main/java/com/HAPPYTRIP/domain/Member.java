package com.HAPPYTRIP.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userId;

    private String password;

    private String name;

    private String phone;

    private String birthday;

    //권한
    @Enumerated(EnumType.STRING)
    private UserRole role;
}

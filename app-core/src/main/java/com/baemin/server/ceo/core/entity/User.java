package com.baemin.server.ceo.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    private String password;

    private int rank;

    private int count;

    @ColumnDefault( "1" )
    private int active; // 0: inactive, 1: active

    @Builder
    public User( long id, String email, String password, int rank, int count) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.rank = rank;
        this.count = count;
    }
}